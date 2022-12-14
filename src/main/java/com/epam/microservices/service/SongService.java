package com.epam.microservices.service;

import com.epam.microservices.model.SongEntity;
import com.epam.microservices.model.SongModel;
import com.epam.microservices.repository.SongRepository;
import com.epam.microservices.service.exception.DuplicateResourceIdException;
import com.epam.microservices.service.exception.SongNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Integer create(SongModel songModel) {
        Integer resourceId = songModel.getResourceId();
        if (repository.isSongWithResourceIdAlreadyPresent(resourceId)) {
            throw new DuplicateResourceIdException(resourceId);
        }
        SongEntity songEntity = modelMapper.map(songModel, SongEntity.class);
        repository.create(songEntity);
        return songEntity.getId();
    }

    public SongModel read(int id) {
        Optional<SongEntity> songEntity = repository.read(id);
        return songEntity.map(songEntity1 -> modelMapper.map(songEntity1, SongModel.class))
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    public List<Integer> delete(List<Integer> ids) {
        List<Integer> deletedIds = new ArrayList<>();
        ids.forEach(id -> {
            Optional<SongEntity> songEntity = repository.read(id);
            if (songEntity.isPresent()) {
                repository.delete(songEntity.get());
                deletedIds.add(id);
            }
        });
        return deletedIds;
    }
}
