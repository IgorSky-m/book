package com.bookdvorik.services.catalog.essences.people;

import java.util.Date;
import java.util.UUID;

public class PeopleEssenceBrief implements IPeopleEssenceBrief {


    private String name ;
    private String imageUrl;
    private UUID uuid;
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
    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
