package com.bookdvorik.services.catalog.essences.book;

import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.dao.tricks.api.PGColumnType;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;
import net.nvcm.sugar.dao.tricks.utils.annotation.DescriptionSystemFields;

import java.math.BigDecimal;

@DescriptionSystemFields
public interface IBookEssenceBrief extends IEssence {


    @ColumnDTO
    String getName();
    void setName(String name);


    @ColumnDTO(
            columnName = "sell_price",
            columnType = PGColumnType.DECIMAL
    )
    BigDecimal getSellPrice();
    void setSellPrice(BigDecimal sellPrice);


    @ColumnDTO(
            columnName = "cover_image_url"
    )
    String getCoverImageUrl();
    void setCoverImageUrl(String coverImageUrl);


}
