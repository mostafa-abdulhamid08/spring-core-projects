package com.libraryProject.repository;

import com.libraryProject.beans.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> books= new ArrayList<>();

    public void save(Book book)
    {
        books.add(book);
        System.out.println("BookRepository: saved → " + book.getTitle());
    }

    public List<Book> findAll()
    {
        return books;
    }
    public int count()
    {
        return books.size();
    }
}
