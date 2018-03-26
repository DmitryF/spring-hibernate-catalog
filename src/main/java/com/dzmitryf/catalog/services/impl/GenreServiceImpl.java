package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.model.book.GenreEnum;
import com.dzmitryf.catalog.repositories.GenreRepository;
import com.dzmitryf.catalog.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Create a given genre. Use the returned instance for further operations as the save operation might have changed the
     * book instance completely.
     * @param genre must note be {@literal null}
     * @param locale
     * @return the saved genre
     * @throws Exception
     * @throws ApiServiceException if genre not entity or already exists
     */
    @Transactional
    @Override
    public Genre create(Genre genre, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("genre.service.create.genre", new Object[]{genre}, locale));
        Genre createdBook = new Genre();
        try {
            createdBook.update(genre);
            entityManager.persist(createdBook);
            entityManager.flush();
            LOGGER.info(messageSource.getMessage("genre.service.created.genre", new Object[]{createdBook}, locale));
            return createdBook;
        } catch (EntityExistsException e) {
            LOGGER.info(messageSource.getMessage("genre.service.genre.already.exist",
                    new Object[]{genre.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("genre.service.genre.already.exist",
                    new Object[]{genre.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("genre.service.genre.not.entity", new Object[]{genre.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("genre.service.genre.not.entity", new Object[]{genre.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("genre.service.error.create.genre", null, locale), e);
            throw e;
        }
    }

    @Override
    public Genre getById(Long id, Locale locale) throws Exception {
        return null;
    }

    @Override
    public Genre update(Genre entity, Locale locale) throws Exception {
        return null;
    }

    @Override
    public void delete(Genre entity, Locale locale) throws Exception {

    }

    /**
     * Retrieve genre by name
     * @param name genre name
     * @param locale
     * @return the genre with given name or {@literal null} if none found
     * @throws Exception
     */
    @Transactional
    @Override
    public Genre getGenreByName(String name, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("genre.service.get.genre.by.name", new Object[]{name}, locale));
        Genre genre = genreRepository.findGenreByName(GenreEnum.fromString(name));
        if (genre == null) {
            LOGGER.info(messageSource.getMessage("genre.service.genre.name.not.found", new Object[]{name}, locale));
            throw new ApiServiceException(messageSource.getMessage("genre.service.genre.name.not.found", new Object[]{name}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("genre.service.found.genre", new Object[]{genre}, locale));
        return genre;
    }

    /**
     * Retrieves all genres of books
     * @param locale
     * @return genres list
     * @throws Exception
     */
    @Override
    public List<Genre> getAllGenres(Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("genre.service.get.genre.all", null, locale));
        List<Genre> genres = genreRepository.findAll()
                .stream().filter(genre -> !genre.getName().equals(GenreEnum.undefined))
                .collect(Collectors.toList());
        genres.forEach( genre -> {
            genre.setTranslate(messageSource.getMessage("genre." + genre.getName().name(), null, locale));
        });
        LOGGER.info(messageSource.getMessage("genre.service.found.genres", new Object[]{genres.size()}, locale));
        return genres;
    }
}
