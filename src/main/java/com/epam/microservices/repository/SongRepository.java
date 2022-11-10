package com.epam.microservices.repository;

import com.epam.microservices.model.SongEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class SongRepository {

    private static final String COUNT_SONGS_WITH_RESOURCE_ID_SQL_QUERY = "SELECT count(s) FROM SongEntity s " +
            "WHERE s.resourceId = ?1";
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
        entityManager.remove(entityManager.contains(songEntity) ? songEntity : entityManager.merge(songEntity));
    }

    public boolean isSongWithResourceIdAlreadyPresent(Integer resourceId) {
        return entityManager.createQuery(COUNT_SONGS_WITH_RESOURCE_ID_SQL_QUERY, Long.class)
                .setParameter(1, resourceId).getSingleResult() > 0;
    }
}
