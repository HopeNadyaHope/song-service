package com.epam.microservices.service;

import com.epam.microservices.model.SongEntity;
import com.epam.microservices.model.SongModel;
import com.epam.microservices.repository.SongRepository;
import com.epam.microservices.service.exception.DuplicateResourceIdException;
import com.epam.microservices.service.exception.SongNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class SongServiceTest {
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private SongRepository repository;
    @InjectMocks
    private SongService service;

    @Test
    void testCreate() {
        SongModel songModel = new SongModel();
        int resourceId = 2;
        songModel.setResourceId(resourceId);
        SongEntity songEntity = mock(SongEntity.class);
        int songId = 3;

        when(repository.isSongWithResourceIdAlreadyPresent(resourceId)).thenReturn(false);
        when(modelMapper.map(songModel, SongEntity.class)).thenReturn(songEntity);
        doNothing().when(repository).create(songEntity);
        when(songEntity.getId()).thenReturn(songId);

        assertEquals(songId, service.create(songModel));
        verify(repository).isSongWithResourceIdAlreadyPresent(resourceId);
        verify(repository).create(songEntity);
        verifyNoMoreInteractions(repository);
        verify(modelMapper).map(songModel, SongEntity.class);
        verifyNoMoreInteractions(modelMapper);
        verify(songEntity).getId();
        verifyNoMoreInteractions(songEntity);
    }

    @Test
    void testCreateDuplicateResourceId() {
        SongModel songModel = new SongModel();
        int resourceId = 2;
        songModel.setResourceId(resourceId);

        when(repository.isSongWithResourceIdAlreadyPresent(resourceId)).thenReturn(true);

        DuplicateResourceIdException thrown = assertThrows(DuplicateResourceIdException.class,
                () -> service.create(songModel));
        assertEquals("Duplicate resource id 2", thrown.getMessage());
        verify(repository).isSongWithResourceIdAlreadyPresent(resourceId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testRead() {
        int id = 3;
        SongEntity songEntity = new SongEntity();
        songEntity.setId(id);
        songEntity.setName("Perfect");
        songEntity.setArtist("Ed Sheeran");
        songEntity.setAlbum("÷");
        songEntity.setLength("4:40");
        songEntity.setResourceId(3);
        songEntity.setYear(2017);

        SongModel songModel = new SongModel();
        songModel.setName("Perfect");
        songModel.setArtist("Ed Sheeran");
        songModel.setAlbum("÷");
        songModel.setLength("4:40");
        songModel.setResourceId(3);
        songModel.setYear(2017);

        when(repository.read(id)).thenReturn(Optional.of(songEntity));
        when(modelMapper.map(songEntity, SongModel.class)).thenReturn(songModel);

        assertEquals(songModel, service.read(id));
        verify(repository).read(id);
        verifyNoMoreInteractions(repository);
        verify(modelMapper).map(songEntity, SongModel.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testReadNotFound() {
        int id = 3;

        when(repository.read(id)).thenReturn(Optional.empty());

        assertThrows(SongNotFoundException.class, () -> service.read(id));
        verify(repository).read(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void testDelete() {
        SongEntity songEntity = mock(SongEntity.class);
        List<Integer> ids = List.of(1);

        when(repository.read(1)).thenReturn(Optional.of(songEntity));
        doNothing().when(repository).delete(songEntity);

        assertEquals(ids, service.delete(ids));
        verify(repository).read(1);
        verify(repository).delete(songEntity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testDeleteNotExistedSong() {
        SongEntity songEntity = mock(SongEntity.class);
        List<Integer> ids = List.of(1);

        when(repository.read(1)).thenReturn(Optional.empty());
        doNothing().when(repository).delete(songEntity);

        assertEquals(service.delete(ids), List.of());
        verify(repository).read(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testDeleteSongNoIds() {
        List<Integer> ids = List.of();

        assertEquals(service.delete(ids), ids);
        verifyNoMoreInteractions(repository);
    }
}
