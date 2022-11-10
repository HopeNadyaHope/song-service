package com.epam.microservices;

import com.epam.microservices.controller.SongController;
import com.epam.microservices.model.SongEntity;
import com.epam.microservices.model.SongModel;
import com.epam.microservices.repository.SongRepository;
import com.epam.microservices.service.SongService;
import com.epam.microservices.service.exception.DuplicateResourceIdException;
import com.epam.microservices.service.exception.SongNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class IntegrationTest {
    private static final String ID = "id";

    @SpyBean
    private SongService service;
    @MockBean
    private SongRepository repository;
    @Autowired
    private SongController controller;

    @Test
    void testCreate() {
        int id = 3;
        int resourceId = 3;
        SongModel songModel = new SongModel();
        songModel.setName("Perfect");
        songModel.setArtist("Ed Sheeran");
        songModel.setAlbum("÷");
        songModel.setLength("4:40");
        songModel.setResourceId(resourceId);
        songModel.setYear(2017);

        Map<String, Integer> expectedResult = Map.of(ID, id);

        when(repository.isSongWithResourceIdAlreadyPresent(resourceId)).thenReturn(false);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((SongEntity) args[0]).setId(id);
            return null;
        }).when(repository).create(any(SongEntity.class));

        assertEquals(expectedResult, controller.create(songModel));
        verify(service).create(songModel);
        verifyNoMoreInteractions(service);
        verify(repository).isSongWithResourceIdAlreadyPresent(resourceId);
        verify(repository).create(any(SongEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testCreateDuplicateResourceId() {
        int resourceId = 3;
        SongModel songModel = new SongModel();
        songModel.setName("Perfect");
        songModel.setArtist("Ed Sheeran");
        songModel.setAlbum("÷");
        songModel.setLength("4:40");
        songModel.setResourceId(resourceId);
        songModel.setYear(2017);

        when(repository.isSongWithResourceIdAlreadyPresent(resourceId)).thenReturn(true);

        assertThrows(DuplicateResourceIdException.class, () -> controller.create(songModel));
        verify(service).create(songModel);
        verifyNoMoreInteractions(service);
        verify(repository).isSongWithResourceIdAlreadyPresent(resourceId);
        verifyNoMoreInteractions(repository);
    }


    @Test
    void readTest() {
        int id = 1;
        SongModel songModel = new SongModel();
        songModel.setName("Umbrella");
        songModel.setArtist("Rihanna");
        songModel.setAlbum("Good Girl Gone Bad");
        songModel.setLength("4:20");
        songModel.setResourceId(1);
        songModel.setYear(2007);

        SongEntity songEntity = new SongEntity();
        songEntity.setId(id);
        songEntity.setName("Umbrella");
        songEntity.setArtist("Rihanna");
        songEntity.setAlbum("Good Girl Gone Bad");
        songEntity.setLength("4:20");
        songEntity.setResourceId(1);
        songEntity.setYear(2007);

        when(repository.read(id)).thenReturn(Optional.of(songEntity));

        assertEquals(songModel, controller.read(id));
        verify(service).read(id);
        verifyNoMoreInteractions(service);
        verify(repository).read(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void readTestNotFound() {
        int id = 100;

        when(repository.read(id)).thenReturn(Optional.empty());

        assertThrows(SongNotFoundException.class, () -> controller.read(id));
        verify(service).read(id);
        verifyNoMoreInteractions(service);
        verify(repository).read(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteTest() {
        List<Integer> ids = List.of(2, 300);
        List<Integer> deletedIds = List.of(2);
        Map<String, List<Integer>> expectedResult = Map.of(ID, deletedIds);

        SongEntity songEntity = new SongEntity();
        songEntity.setId(2);
        songEntity.setName("Umbrella");
        songEntity.setArtist("Rihanna");
        songEntity.setAlbum("Good Girl Gone Bad");
        songEntity.setLength("4:20");
        songEntity.setResourceId(1);
        songEntity.setYear(2007);
        when(repository.read(2)).thenReturn(Optional.of(songEntity));
        doNothing().when(repository).delete(songEntity);
        when(repository.read(300)).thenReturn(Optional.empty());

        assertEquals(expectedResult, controller.delete(ids));
        verify(service).delete(ids);
        verifyNoMoreInteractions(service);
        verify(repository).read(2);
        verify(repository).delete(songEntity);
        verify(repository).read(300);
        verifyNoMoreInteractions(repository);
    }
}
