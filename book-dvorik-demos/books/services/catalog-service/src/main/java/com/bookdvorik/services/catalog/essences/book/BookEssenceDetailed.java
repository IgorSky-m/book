package com.bookdvorik.services.catalog.essences.book;

import java.math.BigDecimal;

public class BookEssenceDetailed extends BookEssenceBrief implements IBookEssenceDetailed {

    private String originBookName;
    private String isbn;
    private String bookSize;
    private String genreName;
    private String publisherName;
    private String bookSeries;
    private String edition;
    private Integer pageQuantity;
    private Integer weight;
    private String rating;
    private String bookAnnotation;
    private BigDecimal buyPrice;
    private boolean available;
    private Integer warehouseCount;
    private String buyCurrency;



    @Override
    public String getOriginBookName() {
        return this.originBookName;
    }

    @Override
    public void setOriginBookName(String originBookName) {
        this.originBookName = originBookName;
    }

    @Override
    public String getIsbn() {
        return this.isbn;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getBookSize() {
        return this.bookSize;
    }

    @Override
    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }

    @Override
    public String getGenreName() {
        return this.genreName;
    }

    @Override
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String getPublisherName() {
        return this.publisherName;
    }

    @Override
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String getBookSeries() {
        return this.bookSeries;
    }

    @Override
    public void setBookSeries(String bookSeries) {
        this.bookSeries = bookSeries;
    }

    @Override
    public String getEdition() {
        return this.edition;
    }

    @Override
    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public Integer getPageQuantity() {
        return this.pageQuantity;
    }

    @Override
    public void setPageQuantity(Integer pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    @Override
    public Integer getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String getRating() {
        return this.rating;
    }

    @Override
    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String getBookAnnotation() {
        return this.bookAnnotation;
    }

    @Override
    public void setBookAnnotation(String bookAnnotation) {
        this.bookAnnotation = bookAnnotation;
    }

    @Override
    public BigDecimal getBuyPrice() {
        return this.buyPrice;
    }

    @Override
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public boolean getAvailable() {
        return this.available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public Integer getWarehouseCount() {
        return this.warehouseCount;
    }

    @Override
    public void setWarehouseCount(Integer warehouseCount) {
        this.warehouseCount = warehouseCount;
    }

    @Override
    public String getBuyCurrency() {
        return this.buyCurrency;
    }

    @Override
    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

}
