package com.epam.microservices.repository;

import com.epam.microservices.model.SongEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class SongRepository {
    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void create(SongEntity songEntity) {
        entityManager.persist(songEntity);
    }

    public Optional<SongEntity> read(Integer id) {
        return Optional.ofNullable(entityManager.find(SongEntity.class, id));
    }

    @Transactional
    public void delete(SongEntity songEntity) {
        entityManager.remove(songEntity);
    }
}
