package com.iam.serviceacquisition.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Optional;

public interface GenericService<T, S extends Serializable> {
    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Pageable pageable, Specification<T> specification);

    T create(T entity);

    T findOne(S id);

    void delete(T entity);

    void deleteById(S id);

    long count();

    Optional<T> findById(S id);
}
