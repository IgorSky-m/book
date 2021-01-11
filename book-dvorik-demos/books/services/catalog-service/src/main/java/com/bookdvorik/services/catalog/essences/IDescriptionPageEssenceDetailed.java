package com.bookdvorik.services.catalog.essences;

import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;

import java.util.List;

/**
 * TODO подумать над реализацией People, Publisher и series через одну сущность
 * Данный интерфейс является примером представления DETAILED через одну сущность
 */


public interface IDescriptionPageEssenceDetailed<BOOK extends IEssence> extends IDescriptionPageEssenceBrief {

    @ColumnDTO(
            viewOnBrief = false
    )
    String getBiography();
    void setBiography(String biography);


    /**
     * Для получения списка книг, которые связанны с данной сущностью
     * @return List BOOK, связанных с данной сущностью
     */
    List<BOOK> getBooks();
    void setBooks(List<BOOK> books);

}
