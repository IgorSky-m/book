package com.bookdvorik.services.catalog.essences.publisher;

import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;
import net.nvcm.sugar.dao.tricks.utils.annotation.DescriptionSystemFields;


@DescriptionSystemFields
public interface IPublisherEssenceBrief extends IEssence {

    @ColumnDTO
    String getName();
    void setName(String name);


    @ColumnDTO
    String getImageUrl();
    void setImageUrl(String imageUrl);

}
