package com.epam.microservices.controller;

import com.epam.microservices.model.SongModel;
import com.epam.microservices.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/songs")
public class SongController {

    private static final String ID = "id";
    @Autowired
    private SongService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, Integer> create(@Valid @RequestBody SongModel songModel) {
        return Map.of(ID, service.create(songModel));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody SongModel read(@PathVariable(name = "id") Integer id) {
        return service.read(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, List<Integer>> delete(@PathVariable(name = "id") List<Integer> ids) {
        return Map.of(ID, service.delete(ids));
    }
}
