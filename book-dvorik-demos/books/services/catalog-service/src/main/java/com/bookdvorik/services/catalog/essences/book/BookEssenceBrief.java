package com.bookdvorik.services.catalog.essences.book;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class BookEssenceBrief implements IBookEssenceBrief {

    private UUID uuid;
    private String name;
    private BigDecimal sellPrice;
    private String coverImageUrl;
    private Date dtCreate;
    private Date dtUpdate;
    private String summary;



    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getSellPrice() {
        return this.sellPrice;
    }

    @Override
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String getCoverImageUrl() {
        return this.coverImageUrl;
    }

    @Override
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Date getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Override
    public Date getDtUpdate() {
        return this.dtUpdate;
    }

    @Override
    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public String getSummary() {
        return this.summary;
    }

    @Override
    public void setSummary(String summary) {
        this.summary = summary;
    }


}
