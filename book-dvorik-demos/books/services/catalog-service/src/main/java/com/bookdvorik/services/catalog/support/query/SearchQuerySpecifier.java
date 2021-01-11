package com.bookdvorik.services.catalog.support.query;

import com.bookdvorik.services.catalog.support.query.exceptions.SearchQuerySpecifyException;
import net.nvcm.sugar.search.core.IQueryRefinement;
import net.nvcm.sugar.search.core.SearchFactory;
import net.nvcm.sugar.search.core.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.*;

/**
 * Created by v.hovanski on 15.03.2017.
 */
public class SearchQuerySpecifier implements BeanPostProcessor {
    static final Logger logger = LoggerFactory.getLogger(SearchQuerySpecifier.class);

    private final Set<IQueryRefinement> queryRefinements = new HashSet<>();

    private final ISearchFactory searchFactory;

    public SearchQuerySpecifier(Set<IQueryRefinement> queryRefinements) {
        this.searchFactory = SearchFactory.getInstance();
        if(queryRefinements != null){
            this.queryRefinements.addAll(queryRefinements);
        }
    }

    public ISearchQuery specify(ISearchQuery searchQuery) {
        //1 Найдем CRUDRepository, которое обрабатывает этот квери
        IQueryRefinement queryRefinement = findQueryRefinement(searchQuery.getDtoClass());
        if (queryRefinement == null)
            throw new SearchQuerySpecifyException("search.query.specifier.convert.not.found");

        //2 ORDER
        Map<String, SortDirection> newSortFields = new HashMap<>();
        if(searchQuery.getSorting() != null && searchQuery.getSorting().size() > 0){
            for (Map.Entry<String, SortDirection> sorting : searchQuery.getSorting().entrySet()) {
                if (!queryRefinement.isFieldSupportedForOrder(sorting.getKey()))
                    throw new SearchQuerySpecifyException("search.query.specifier.convert.field.sort.notsupported", new Object[]{sorting.getKey()});

                newSortFields.put(queryRefinement.fieldNameForAlias(sorting.getKey()), sorting.getValue());
            }
        }
        //3 Поисковое выражение
        ISearchExpression searchExpression = searchQuery.getSearchExpression();
        ISearchExpression newSearchExpression = null;
        if (searchExpression != null) {
            newSearchExpression = convertSearchExpression(searchExpression, queryRefinement);
        }

        //4 Детали постраничного доступа
        long page = -1;
        long pageSize = -1;
        if (searchQuery.getPageSize() != -1) {//Значит что-то устанавливали
            if (searchQuery.getPageSize() > ISearchQuery.MAX_IN_PAGE)
                throw new SearchQuerySpecifyException("search.query.specifier.convert.page.size", new Object[]{String.valueOf(ISearchQuery.MAX_IN_PAGE)});
            if (searchQuery.getPageSize() < -1 || searchQuery.getPageSize() == -1)
                throw new SearchQuerySpecifyException("search.query.specifier.convert.page.zero", null);
            pageSize = searchQuery.getPageSize();

            if (searchQuery.getPage() < 0)
                throw new SearchQuerySpecifyException("search.query.specifier.convert.page.negative", null);
            page = searchQuery.getPage();
        }

        Class<?> dtoClass = searchQuery.getDtoClass();
        IResourcePath resourcePath = searchQuery.getResourcePath();

        ISearchQuery newSearchQuery = searchFactory.createQuery();
        newSearchQuery.setSearchExpression(newSearchExpression);
        newSearchQuery.setSorting(newSortFields);

        newSearchQuery.setDtoClass(dtoClass);
        newSearchQuery.setResourcePath(resourcePath);
        newSearchQuery.setPageSize(pageSize);
        newSearchQuery.setPage(page);
        newSearchQuery.setChainStart(searchQuery.getChainStart());

        return newSearchQuery;
    }


    public ISearchExpression convertSearchExpression(ISearchExpression searchExpression, Class clazz) {
        //Найдем CRUDRepository, которое обрабатывает эту группу
        IQueryRefinement queryRefinement = findQueryRefinement(clazz);
        if (queryRefinement == null)
            throw new SearchQuerySpecifyException("search.query.specifier.convert.search.expression", null);

        return convertSearchExpression(searchExpression, queryRefinement);
    }

    public ISearchUnit convertSearchUnit(ISearchUnit searchUnit, Class clazz) {
        //Найдем CRUDRepository, которое обрабатывает это выражение
        IQueryRefinement queryRefinement = findQueryRefinement(clazz);
        if (queryRefinement == null)
            throw new SearchQuerySpecifyException("search.query.specifier.convert.search.unit.not.found", null);

        return convertSearchUnit(searchUnit, queryRefinement);
    }


    private ISearchExpression convertSearchExpression(ISearchExpression searchExpression, IQueryRefinement queryRefinement) {
        if (searchExpression == null)
            return searchExpression;

        if (searchExpression.getLogicalOperator() == null)
            throw new SearchQuerySpecifyException("search.query.specifier.convert.search.expression.not.field", null);

//        if (!LogicalOperator.AND.equals(searchExpression.getLogicalOperator())) {
//            throw new ERGPIllegalArgumentException("Поддерживается только оператор группировки AND");
//        }


        //SearchUnit
        List<ISearchUnit> searchUnits = searchExpression.getLogicUnits();
        List<ISearchUnit> newSearchUnitList = new ArrayList<>();
        if (searchUnits != null) {
            for (ISearchUnit searchUnit : searchUnits) {
                ISearchUnit newSearchUnit = convertSearchUnit(searchUnit, queryRefinement);
                if (newSearchUnit != null)
                    newSearchUnitList.add(newSearchUnit);
            }
        }


        //SearchExpression
        List<ISearchExpression> searchExpressions = searchExpression.getLogicalExpressions();
        List<ISearchExpression> newSearchExpressions = new ArrayList<>();
        if (searchExpressions != null) {
            for (ISearchExpression searchExpressionIT : searchExpressions) {
                ISearchExpression newSearchExpression = convertSearchExpression(searchExpressionIT, queryRefinement);
                if (newSearchExpression != null)
                    newSearchExpressions.add(newSearchExpression);
            }
        }

        if (newSearchUnitList.isEmpty() && newSearchExpressions.isEmpty()) {
            throw new SearchQuerySpecifyException("search.query.specifier.convert.search.expression.empty.group", null);
        }

        //Держим только AND - создаем AND
        ISearchExpression newSearchExpression = LogicalOperator.AND.equals(searchExpression.getLogicalOperator()) ? searchFactory.and() : searchFactory.or();
        newSearchExpression.getLogicUnits().addAll(newSearchUnitList);
        newSearchExpression.getLogicalExpressions().addAll(newSearchExpressions);


        return newSearchExpression;
    }

    private ISearchUnit convertSearchUnit(ISearchUnit searchUnit, IQueryRefinement queryRefinement) {
        if (searchUnit == null)
            return searchUnit;

        //Расшифровываем псевдоним в правильное поле
        searchUnit.setField(queryRefinement.fieldNameForAlias(searchUnit.getField()));

        if(!queryRefinement.isFieldSupportedForSearch(searchUnit.getField()))
            throw new SearchQuerySpecifyException("search.query.specifier.convert.field.search.notsupported", new Object[]{searchUnit.getField()});

        if (searchUnit.getOperator() == null)
            throw new SearchQuerySpecifyException("search.query.specifier.convert.search.unit.operator.empty", null);

        return queryRefinement.prepareSearchUnit(searchUnit);
    }

    private IQueryRefinement findQueryRefinement(Class clazz) {

        for (IQueryRefinement queryRefinementIt : queryRefinements) {
            if (queryRefinementIt.isDtoSupported(clazz)) {
                return queryRefinementIt;
            }
        }

        return null;
    }
}
