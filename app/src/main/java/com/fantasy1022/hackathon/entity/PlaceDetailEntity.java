package com.fantasy1022.hackathon.entity;

/**
 * Created by fantasy_apple on 2017/7/2.
 */

public class PlaceDetailEntity {
    private Double lat;
    private Double lon;
    private int time;
    private String question;
    private String content;
    private String pictureUrl;


    public PlaceDetailEntity() {
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public int getTime() {
        return time;
    }

    public String getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
