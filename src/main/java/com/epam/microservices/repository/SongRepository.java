package com.epam.microservices.repository;

import com.epam.microservices.model.SongEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class SongRepository {
    private final Logger logger = LoggerFactory.getLogger(SongRepository.class);
    private static final String COUNT_SONGS_WITH_RESOURCE_ID_SQL_QUERY = "SELECT count(s) FROM SongEntity s " +
            "WHERE s.resourceId = ?1";
    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void create(SongEntity songEntity) {
        entityManager.persist(songEntity);
        logger.info("SongEntity with id={} created in database", songEntity.getId());
    }

    public Optional<SongEntity> read(Integer id) {
        logger.info("Getting songEntity with id={} from database", id);
        return Optional.ofNullable(entityManager.find(SongEntity.class, id));
    }

    @Transactional
    public void delete(SongEntity songEntity) {
        logger.info("Deleting songEntity with id={} from database", songEntity.getId());
        entityManager.remove(entityManager.contains(songEntity) ? songEntity : entityManager.merge(songEntity));
    }

    public boolean isSongWithResourceIdAlreadyPresent(Integer resourceId) {
        return entityManager.createQuery(COUNT_SONGS_WITH_RESOURCE_ID_SQL_QUERY, Long.class)
                .setParameter(1, resourceId).getSingleResult() > 0;
    }
}
