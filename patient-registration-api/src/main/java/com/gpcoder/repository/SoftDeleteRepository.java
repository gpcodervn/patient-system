package com.gpcoder.repository;

import org.springframework.data.jpa.repository.Modifying;

import java.io.Serializable;

public interface SoftDeleteRepository<T, ID extends Serializable> {

    /**
     * Execute an update statement to set the deleted time at now() for the given entity id
     * @param id The id of entity will be soft deleted, must not be {@literal null}.
     */
    @Modifying
    int softDelete(ID id);

    /**
     * Execute an update statement to set the deleted time at now() for the given entity id
     * @param entity The entity will be soft deleted, must not be {@literal null}.
     */
    int softDelete(T entity);

}