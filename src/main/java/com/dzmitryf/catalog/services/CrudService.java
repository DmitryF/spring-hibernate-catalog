package com.dzmitryf.catalog.services;

/**
 * Interface for generic CRUD operations on a service for a specific type.
 */
public interface CrudService<T> {

    /**
     * Create a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    T create(T entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T getById(Long id);

    /**
     * Update a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the updated entity
     */
    T update(T entity);

    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    void delete(T entity);
}
