package com.mywork.framework.spring.helper;


import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mywork.framework.jpa.repository.BaseRepository;

@NoRepositoryBean
public class SimpleBaseRepository<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements
        BaseRepository<T, ID> {

    public SimpleBaseRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}
