package com.dzmitryf.catalog.dao;

import com.dzmitryf.catalog.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    Book save(Book book);

    Book findBookByName(String name);

    @Query(value = "SELECT * FROM hbschema.books ORDER BY count_pages DESC", nativeQuery = true)
    List<Book> getBooksByCountPagesDesc();
}
