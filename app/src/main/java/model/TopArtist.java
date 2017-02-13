package model;

import java.io.Serializable;

/**
 * Created by alanwhitten on 21/10/2016.
 */

public class TopArtist implements Serializable {

    private static final long id = 1L;
    private String title;
    private String artist;
    private String listenerNum;
    private String country;
    private String url;
    private String mbid;
    private String bandImage;

    public static long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getListenerNum() {
        return listenerNum;
    }

    public void setListenerNum(String listenerNum) {
        this.listenerNum = listenerNum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getBandImage() {
        return bandImage;
    }

    public void setBandImage(String bandImage) {
        this.bandImage = bandImage;
    }
}
