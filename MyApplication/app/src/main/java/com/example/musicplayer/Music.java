package com.example.musicplayer;

import android.net.Uri;

public class Music {

    private Uri uri;
    private String name;
    private String artist_name;
    private String duration;


    public Music(Uri uri, String name, String artist_name, String duration) {
        this.uri = uri;
        this.name = name;
        this.artist_name = artist_name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Uri getUri() {
        return uri;
    }


}
