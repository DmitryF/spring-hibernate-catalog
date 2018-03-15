package com.dzmitryf.catalog.repositories;

import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieve book by name
     * @param name book name
     * @return the book with given name or {@literal null} if none found
     */
    Book findBookByName(String name);

    /**
     * Retrieve books by genre
     * @param genre book genre
     * @return the books with given genre or {@literal null} if none found
     */
    List<Book> findBooksByGenre(Genre genre);

    /**
     * Retrivies books with max count pages
     * @return the books that sorted by descending
     */
    @Query(value = "SELECT * FROM hbschema.books ORDER BY count_pages DESC", nativeQuery = true)
    List<Book> findBooksByCountPagesDesc();
}
