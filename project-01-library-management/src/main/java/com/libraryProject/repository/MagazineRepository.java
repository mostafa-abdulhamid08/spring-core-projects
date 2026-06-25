package com.libraryProject.repository;

import com.libraryProject.beans.Book;
import com.libraryProject.beans.Magazine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MagazineRepository {
    private final List<Magazine> magazines= new ArrayList<>();

    public void save(Magazine magazine)
    {
        magazines.add(magazine);
        System.out.println("MagazineRepository: saved → " + magazine.getTitle());
    }

    public List<Magazine> findAll()
    {
        return magazines;
    }
    public int count()
    {
        return magazines.size();
    }

}
