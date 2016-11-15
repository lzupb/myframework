package com.mywork.framework.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mywork.framework.jpa.entity.ConfDB;


@Repository
public interface IConfDAO extends BaseRepository<ConfDB, Long> {

    @Query("FROM ConfDB t WHERE t.key =:name")
    ConfDB find(@Param("name") String name);

}
