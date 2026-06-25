package com.libraryProject.service;

import com.libraryProject.beans.Book;
import com.libraryProject.beans.Magazine;
import com.libraryProject.repository.BookRepository;
import com.libraryProject.repository.MagazineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final MagazineRepository magazineRepository;

    public LibraryService(BookRepository bookRepository, MagazineRepository magazineRepository) {
        this.bookRepository = bookRepository;
        this.magazineRepository = magazineRepository;
        System.out.println("LibraryService: created with Constructor Injection");
    }
    public void addBook (Book book)
    {
        if(book.getPrice() < 0)
        {
            throw new RuntimeException("Price cannot be negative !");
        }
        bookRepository.save(book);
    }

    public void addMagazine(Magazine magazine) {
        if (magazine.getIssueNumber() <= 0) throw new RuntimeException("Issue number must be positive!");
        magazineRepository.save(magazine);
    }

    public List<Book> getAllBooks()
    {
        return  bookRepository.findAll();
    }
    public List<Magazine> getAllMagazines()
    {
        return  magazineRepository.findAll();
    }

    public void printSummary() {
        System.out.println("=== Library Summary ===");
        System.out.println("Total books:   " + bookRepository.count());
        List<Book> books = getAllBooks();
        for(var book : books)
        {
            System.out.println(book);
        }
        System.out.println("\n " + "Total magazines: " + magazineRepository.count());
         getAllMagazines().forEach(System.out::println);

    }






}
