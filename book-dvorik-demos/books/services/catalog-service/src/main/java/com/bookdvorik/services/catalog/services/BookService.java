package com.bookdvorik.services.catalog.services;

import com.bookdvorik.services.catalog.essences.book.IBookEssenceBrief;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    public List<IBookEssenceBrief> getAll() {
        //Заглушка
        List<IBookEssenceBrief> books = new ArrayList<>();
        return books;

    }
}
