package com.epam.microservices.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SongModel {
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotBlank(message = "Artist can't be empty")
    private String artist;
    @NotBlank(message = "Album can't be empty")
    private String album;
    @NotBlank(message = "Length can't be empty")
    private String length;
    @NotNull
    @Min(1)
    private int resourceId;
    @NotNull
    @Min(1500)
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongModel songModel = (SongModel) o;
        return resourceId == songModel.resourceId && year == songModel.year && name.equals(songModel.name) && artist.equals(songModel.artist) && album.equals(songModel.album) && length.equals(songModel.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, album, length, resourceId, year);
    }
}
