package com.ehall.simplecrud;

import java.io.Serializable;

public class MusicAlbum implements Serializable {

    private String name;
    private String artist;
    private int releaseYear;

    public MusicAlbum() {
        this.name = "";
        this.artist = "";
        this.releaseYear = 0;
    }

    public MusicAlbum(String name, String artist, int releaseYear) {
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
    }

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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
