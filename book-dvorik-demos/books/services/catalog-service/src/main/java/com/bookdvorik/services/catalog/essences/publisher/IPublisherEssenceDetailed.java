package com.bookdvorik.services.catalog.essences.publisher;


import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;

import java.util.List;

public interface IPublisherEssenceDetailed<BOOK extends IEssence> extends IPublisherEssenceBrief {

    @ColumnDTO(
            viewOnBrief = false
    )
    String getBiography();
    void setBiography(String biography);


    /**
     * Для получения списка книг, которые связанны с данной сущностью
     * @return List BOOKS, связанных с данной сущностью
     */
    List<BOOK> getBooks();
    void setBooks(List<BOOK> books);



}
