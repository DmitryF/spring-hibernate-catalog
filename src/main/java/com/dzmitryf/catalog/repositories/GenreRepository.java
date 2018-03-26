package com.dzmitryf.catalog.repositories;

import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.model.book.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    /**
     * Retrieve genre by name
     * @param name genre name
     * @return the genre with given name or {@literal null} if none found
     */
    Genre findGenreByName(GenreEnum name);
}
