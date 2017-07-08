package com.fantasy1022.hackathon.entity;

/**
 * Created by fantasy_apple on 2017/7/2.
 */

public class PlaceDetailEntity {
    private String num;
    private String lat;
    private String lon;
    private String time;
    private String question;
    private String pictureUrl;
    private String category;
    private String resolve;
    private String filingTime;
    private String address;
    private String thumb;


    public PlaceDetailEntity() {
    }

    public String getNum() {
        return num;
    }

    public boolean getResolve() {
        return resolve.equals("true") ? true : false;
    }

    public Double getLat() {
        return Double.parseDouble(lat);
    }

    public Double getLon() {
        return Double.parseDouble(lon);
    }

    public int getTime() {
        return Integer.parseInt(time);
    }

    public String getQuestion() {
        return question;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getFilingTime() {
        return filingTime;
    }

    public String getAddress() {
        return address;
    }

    public String getThumb() {
        return thumb;
    }
}
