package com.dzmitryf.catalog.services;

import com.sun.istack.internal.Nullable;

import java.util.Locale;

/**
 * Interface for generic CRUD operations on a service for a specific type.
 */
public interface CrudService<T> {

    /**
     * Create a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must note be {@literal null}
     * @param locale
     * @return the saved entity
     */
    T create(T entity, Locale locale);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @param locale
     * @return the entity with the given id or {@literal null} if none found
     */
    T getById(Long id, Locale locale);

    /**
     * Update a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must note be {@literal null}
     * @param locale
     * @return the updated entity
     */
    T update(T entity, Locale locale);

    /**
     * Deletes a given entity.
     *
     * @param entity must note be {@literal null}
     * @param locale
     */
    void delete(T entity, Locale locale);
}
