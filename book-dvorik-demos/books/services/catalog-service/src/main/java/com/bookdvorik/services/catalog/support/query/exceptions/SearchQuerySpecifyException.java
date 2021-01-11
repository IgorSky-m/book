package com.bookdvorik.services.catalog.support.query.exceptions;

public class SearchQuerySpecifyException extends IllegalArgumentException {
    private Object[] args;

    public SearchQuerySpecifyException() {
        super("serialize.exception");
    }

    public SearchQuerySpecifyException(String s) {
        super(s);
    }

    public SearchQuerySpecifyException(Object[] args) {
        super("serialize.exception");
        this.args = args;
    }

    public SearchQuerySpecifyException(String s, Object[] args) {
        super(s);
    }
}
