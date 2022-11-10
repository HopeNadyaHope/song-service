package com.epam.microservices.repository;

import com.epam.microservices.model.SongEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongRepositoryTest {
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private SongRepository repository;

    @Test
    void testCreate() {
        int id = 3;
        String name = "Perfect";
        String artist = "Ed Sheeran";
        String album = "÷";
        String length = "4:40";
        int resourceId = 3;
        int year = 2017;

        SongEntity songEntity = new SongEntity();
        songEntity.setName(name);
        songEntity.setArtist(artist);
        songEntity.setAlbum(album);
        songEntity.setLength(length);
        songEntity.setResourceId(resourceId);
        songEntity.setYear(year);

        repository.create(songEntity);

        assertEquals(id, songEntity.getId());
        assertEquals(name, songEntity.getName());
        assertEquals(artist, songEntity.getArtist());
        assertEquals(album, songEntity.getAlbum());
        assertEquals(length, songEntity.getLength());
        assertEquals(resourceId, songEntity.getResourceId());
        assertEquals(year, songEntity.getYear());
    }

    @Test
    void testIsSongWithResourceIdAlreadyPresent() {
        int resourceId = 2;
        assertTrue(repository.isSongWithResourceIdAlreadyPresent(resourceId));
    }

    @Test
    void testIsSongWithResourceIdNotAlreadyPresent() {
        int resourceId = 100;
        assertFalse(repository.isSongWithResourceIdAlreadyPresent(resourceId));
    }

    @Test
    void testRead() {
        int id = 1;
        String name = "Umbrella";
        String artist = "Rihanna";
        String album = "Good Girl Gone Bad";
        String length = "4:20";
        int resourceId = 1;
        int year = 2007;

        Optional<SongEntity> songEntityOptional = repository.read(id);

        assertTrue(songEntityOptional.isPresent());
        SongEntity songEntity = songEntityOptional.get();
        assertEquals(id, songEntity.getId());
        assertEquals(name, songEntity.getName());
        assertEquals(artist, songEntity.getArtist());
        assertEquals(album, songEntity.getAlbum());
        assertEquals(length, songEntity.getLength());
        assertEquals(resourceId, songEntity.getResourceId());
        assertEquals(year, songEntity.getYear());
    }

    @Test
    void testReadNotFound() {
        int id = 100;
        assertEquals(Optional.empty(), repository.read(id));
    }

}
