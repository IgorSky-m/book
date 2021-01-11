package com.bookdvorik.services.catalog.essences;

import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;
import net.nvcm.sugar.dao.tricks.utils.annotation.DescriptionSystemFields;


/**
 * TODO подумать над реализацией People, Publisher и series через одну сущность
 * Данный интерфейс является примером представления BRIEF через одну сущность
 */

@DescriptionSystemFields
public interface IDescriptionPageEssenceBrief extends IEssence {

    @ColumnDTO
    String getName();
    void setName(String name);


    @ColumnDTO
    String getImageUrl();
    void setImageUrl(String imageUrl);
}
