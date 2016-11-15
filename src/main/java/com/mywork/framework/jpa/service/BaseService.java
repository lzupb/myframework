package com.mywork.framework.jpa.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mywork.framework.jpa.repository.BaseRepository;

/**
 * 基础service
 *
 * @param <T>  bean
 * @param <ID> bean id
 */
@Transactional
public abstract class BaseService<T, ID extends Serializable> {        
    
    protected abstract BaseRepository<T, ID> getRepository();    

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public List<T> findAll(Sort sort) {
        return getRepository().findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public Iterable<T> findAll(Predicate predicate) {
        return getRepository().findAll(predicate);
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        return getRepository().findAll(ids);
    }

    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return getRepository().findAll(predicate, orders);
    }

    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return getRepository().findAll(predicate, pageable);
    }

    public <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    public <S extends T> List<S> save(Iterable<S> entities) {
        return getRepository().save(entities);
    }

    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    public void flush() {
        getRepository().flush();
    }

    public T findOne(ID id) {
        return getRepository().findOne(id);
    }

    public T findOne(Predicate predicate) {
        return getRepository().findOne(predicate);
    }

    public boolean exists(ID id) {
        return getRepository().exists(id);
    }

    public long count() {
        return getRepository().count();
    }

    public long count(Predicate predicate) {
        return getRepository().count(predicate);
    }

    public void delete(ID id) {
        getRepository().delete(id);
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    public void delete(Iterable<? extends T> entities) {
        getRepository().delete(entities);
    }

    public void deleteInBatch(Iterable<T> entities) {
        getRepository().deleteInBatch(entities);
    }

    public void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    public void deleteAll() {
        getRepository().deleteAll();
    }

}
