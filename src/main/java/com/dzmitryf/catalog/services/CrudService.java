package com.dzmitryf.catalog.services;

import java.util.Locale;

/**
 * Interface for generic CRUD operations on a service for a specific type.
 */
public interface CrudService<T> {

    /**
     * Create a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     * @param entity must note be {@literal null}
     * @param locale
     * @return the saved entity
     * @throws Exception
     */
    T create(T entity, Locale locale) throws Exception;

    /**
     * Retrieves an entity by its id.
     * @param id must not be {@literal null}.
     * @param locale
     * @return the entity with the given id or {@literal null} if none found
     * @throws Exception
     */
    T getById(Long id, Locale locale) throws Exception;

    /**
     * Update a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     * @param entity must note be {@literal null}
     * @param locale
     * @return the updated entity
     * @throws Exception
     */
    T update(T entity, Locale locale) throws Exception;

    /**
     * Deletes a given entity.
     * @param entity must note be {@literal null}
     * @param locale
     * @throws Exception
     */
    void delete(T entity, Locale locale) throws Exception;
}
