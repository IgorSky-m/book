package com.bookdvorik.services.catalog.essences.publisher;

import com.bookdvorik.services.catalog.essences.book.IBookEssenceBrief;

import java.util.List;

public class PublisherEssenceDetailed extends PublisherEssenceBrief implements IPublisherEssenceDetailed<IBookEssenceBrief> {


    private String biography;
    private List<IBookEssenceBrief> books;


    @Override
    public String getBiography() {
        return this.biography;
    }

    @Override
    public void setBiography(String biography) {
        this.biography = biography;

    }




    @Override
    public List<IBookEssenceBrief> getBooks() {
        return this.books;
    }

    @Override
    public void setBooks(List<IBookEssenceBrief> iBookEssenceBriefs) {
        this.books = iBookEssenceBriefs;
    }

}
