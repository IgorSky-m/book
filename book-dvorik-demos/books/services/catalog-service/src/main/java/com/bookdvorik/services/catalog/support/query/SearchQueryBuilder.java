package com.bookdvorik.services.catalog.support.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nvcm.sugar.search.commons.IChainable;
import net.nvcm.sugar.search.core.SearchFactory;
import net.nvcm.sugar.search.core.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by v.hovanski on 09.03.2017.
 */
public class SearchQueryBuilder implements Converter<ISearchQueryIntention, ISearchQuery> {
    static final Logger logger = LoggerFactory.getLogger(SearchQueryBuilder.class);

    private final SearchQuerySpecifier specifier;
    private final ObjectMapper objectMapper;
    private final ISearchFactory searchFactory;
    private final MessageSource messageSource;

    public SearchQueryBuilder(SearchQuerySpecifier specifier, ObjectMapper objectMapper, MessageSource messageSource) {
        this.specifier = specifier;
        this.objectMapper = objectMapper;
        this.searchFactory = SearchFactory.getInstance();
        this.messageSource = messageSource;
    }

    @Override
    public ISearchQuery convert(ISearchQueryIntention iSearchQueryIntention) {
        //1 Разпарсить queryString
        ISearchQuery searchQuery;
        if (StringUtils.hasText(iSearchQueryIntention.getQueryString())) {
            try {
                searchQuery = objectMapper.readValue(iSearchQueryIntention.getQueryString(), ISearchQuery.class);
            } catch (IOException e) {
                final String errorKey="search.query.builder.convert";
                logger.error(messageSource.getMessage(errorKey, null, Locale.getDefault()), e);
                throw new IllegalArgumentException(messageSource.getMessage(errorKey, null, LocaleContextHolder.getLocale()), e);
            }
        } else {
            searchQuery = searchFactory.createQuery();
        }


        //2 Детали постраничного доступа
        int page = -1;
        int pageSize = -1;

        do{
            //способ цепочкой
            if (iSearchQueryIntention.isChainable()) {
                searchQuery.setPage(0);
                searchQuery.setPageSize(iSearchQueryIntention.getPageSize() + 1);
                searchQuery.setChainStart(iSearchQueryIntention.getChainStart());

                //add order
                Map<String, SortDirection> sorting = new LinkedHashMap<>();
                sorting.put(IChainable.ORDINAL_FIELD, SortDirection.DESC);
                if (searchQuery.getSorting() != null)
                    sorting.putAll(searchQuery.getSorting());

                searchQuery.setSorting(sorting);

                break;
            }

            //классика страницей
            if (iSearchQueryIntention.isPageable()) {
                searchQuery.setPage(iSearchQueryIntention.getPage());
                searchQuery.setPageSize(iSearchQueryIntention.getPageSize());
            }  else{
                searchQuery.setPage(page);
                searchQuery.setPageSize(pageSize);
            }

        }while (false);





        //3 Класс ДТО и путь
        Class<?> dtoClass = iSearchQueryIntention.getDtoClass();
        IResourcePath resourcePath = iSearchQueryIntention.getResourcePath();

        searchQuery.setDtoClass(dtoClass);
        searchQuery.setResourcePath(resourcePath);

        return specifier.specify(searchQuery);
    }
}
