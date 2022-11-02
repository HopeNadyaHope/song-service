package com.epam.microservices.controller;

import com.epam.microservices.model.SongModel;
import com.epam.microservices.service.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class SongControllerTest {
    private static final String ID = "id";
    @MockBean
    private SongService service;
    @InjectMocks
    private SongController controller;

    @Test
    void testCreate() {
        SongModel songModel = new SongModel();
        songModel.setName("Perfect");
        songModel.setArtist("Ed Sheeran");
        songModel.setAlbum("÷");
        songModel.setLength("4:40");
        songModel.setResourceId(3);
        songModel.setYear(2017);
        int id = 3;
        Map<String, Integer> expectedResult = Map.of(ID, id);

        when(service.create(songModel)).thenReturn(id);

        assertEquals(expectedResult, controller.create(songModel));
        verify(service).create(songModel);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testRead() {
        SongModel songModel = new SongModel();
        songModel.setName("Perfect");
        songModel.setArtist("Ed Sheeran");
        songModel.setAlbum("÷");
        songModel.setLength("4:40");
        songModel.setResourceId(3);
        songModel.setYear(2017);
        int id = 3;

        when(service.read(id)).thenReturn(songModel);

        assertEquals(songModel, controller.read(id));
        verify(service).read(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testDelete() {
        List<Integer> ids = List.of(1, 2, 3);
        List<Integer> deletedIds = List.of(1, 3);
        Map<String, List<Integer>> expectedResult = Map.of(ID, deletedIds);

        when(service.delete(ids)).thenReturn(deletedIds);

        assertEquals(expectedResult, controller.delete(ids));
        verify(service).delete(ids);
        verifyNoMoreInteractions(service);
    }
}
