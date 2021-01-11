package com.bookdvorik.services.catalog.essences.book;

import net.nvcm.sugar.dao.tricks.api.PGColumnType;
import net.nvcm.sugar.dao.tricks.utils.annotation.ColumnDTO;

import java.math.BigDecimal;




public interface IBookEssenceDetailed extends IBookEssenceBrief {

    @ColumnDTO(
            viewOnBrief = false
    )
    String getOriginBookName();
    void setOriginBookName(String originBookName);


    @ColumnDTO(
            viewOnBrief = false
    )
    String getIsbn();
    void setIsbn(String isbn);


    @ColumnDTO(
            viewOnBrief = false
    )
    String getBookSize();
    void setBookSize(String bookSize);

    @ColumnDTO(
            viewOnBrief = false
    )
    String getGenreName();
    void setGenreName(String genreName);

    @ColumnDTO(
            viewOnBrief = false
    )
    String getPublisherName();
    void setPublisherName(String publisherName);


    @ColumnDTO(
            viewOnBrief = false
    )
    String getBookSeries();
    void setBookSeries(String bookSeries);

    @ColumnDTO(
            viewOnBrief = false
    )
    String getEdition();
    void setEdition(String edition);


    @ColumnDTO(
            viewOnBrief = false
    )
    Integer getPageQuantity();
    void setPageQuantity(Integer pageQuantity);


    /**
     * TODO подумать int или Integer
     */
    @ColumnDTO(
            viewOnBrief = false
    )
    Integer getWeight();
    void setWeight(Integer weight);


    @ColumnDTO(
            viewOnBrief = false
    )
    String getRating();
    void setRating(String rating);


    //getAnnotation используется?
    @ColumnDTO(
            viewOnBrief = false
    )
    String getBookAnnotation();
    void setBookAnnotation(String bookAnnotation);

    /**
     * ниже идут ПОЛЯ для Админ части
     * TODO подумать по целесообразности перенесения этих полей или вообще через другую сущность делать если мы будем использовать другой URL
     */

    @ColumnDTO(
            columnName = "buy_price",
            columnType = PGColumnType.DECIMAL
    )
    BigDecimal getBuyPrice();
    void setBuyPrice(BigDecimal buyPrice);

    @ColumnDTO
    boolean getAvailable();
    void setAvailable(boolean available);

    @ColumnDTO
    Integer getWarehouseCount();
    void setWarehouseCount(Integer warehouseCount);


    /**
     * TODO подумать, может enum?
     */
    @ColumnDTO
    String getBuyCurrency();
    void setBuyCurrency(String buyCurrency);


}
