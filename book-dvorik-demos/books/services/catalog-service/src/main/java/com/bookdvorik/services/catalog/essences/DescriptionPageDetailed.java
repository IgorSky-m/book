package com.bookdvorik.services.catalog.essences;

import com.bookdvorik.services.catalog.essences.book.IBookEssenceBrief;

import java.util.List;

/**
 * TODO подумать над реализацией People, Publisher и series через одну сущность
 * Данный класс является примером представления DETAILED через одну сущность
 */


public class DescriptionPageDetailed extends DescriptionPageEssenceBrief implements IDescriptionPageEssenceDetailed<IBookEssenceBrief> {


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
