package com.iam.serviceacquisition.service;


import com.iam.serviceacquisition.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractGenericService<T, S extends Serializable> implements GenericService<T, S> {

    protected final GenericRepository<T, S> dao;

    @Override
    public Page<T> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable, Specification<T> specification) {
        return dao.findAll(specification, pageable);
    }

    @Override
    public T create(T entity) {
        return dao.save(entity);
    }

    @Override
    public T findOne(S id) {
        return dao.getOne(id);
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(S id) {
        dao.deleteById(id);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public Optional<T> findById(S id) {
        return dao.findById(id);
    }
}
