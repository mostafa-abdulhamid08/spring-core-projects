package com.libraryProject;

import com.libraryProject.beans.Book;
import com.libraryProject.beans.Magazine;
import com.libraryProject.config.LibraryConfig;
import com.libraryProject.service.LibraryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LibraryApp {
    public static void main(String[] args) {

        System.out.println("========== Starting Spring Context ==========");
        var context = new AnnotationConfigApplicationContext(LibraryConfig.class);
        System.out.println("========== Context Ready ==========\n");

        LibraryService libraryService = context.getBean(LibraryService.class);
        Book cleanCode= context.getBean("cleanCodeBook", Book.class);
        Book effectiveJava = context.getBean("effectiveJavaBook", Book.class);
        Book springInAction= context.getBean("springInActionBook", Book.class);

        System.out.println("\n--- Fetching Primary Book ---");
        Book primaryBook = context.getBean(Book.class);
        System.out.println("Primary book is: " + primaryBook.getTitle());

        System.out.println("\n--- Adding Books ---");
        libraryService.addBook(cleanCode);
        libraryService.addBook(effectiveJava);
        libraryService.addBook(springInAction);

        System.out.println("\n--- Adding Magazines ---");
        Magazine natGeo = context.getBean("nationalGeographic", Magazine.class);
        Magazine times  = context.getBean("timesMagazine", Magazine.class);
        libraryService.addMagazine(natGeo);
        libraryService.addMagazine(times);

        System.out.println();
        libraryService.printSummary();

        // Singleton proof
        System.out.println("\n--- Singleton Proof ---");
        LibraryService s1 = context.getBean(LibraryService.class);
        LibraryService s2 = context.getBean(LibraryService.class);
        System.out.println("s1 == s2 ? " + (s1 == s2)); // true

        context.close();
        System.out.println("\n========== Context Closed ==========");



    }
}
