package com.fantasy1022.hackathon.entity;

import java.util.ArrayList;

/**
 * Created by fantasy_apple on 2017/7/2.
 */

public class PlaceEntity {
    private ArrayList<PlaceDetailEntity> road;
    private ArrayList<PlaceDetailEntity> environment;
    private ArrayList<PlaceDetailEntity> tree;
    private ArrayList<PlaceDetailEntity> park;
    private ArrayList<PlaceDetailEntity> other;

    public PlaceEntity() {
    }

    public ArrayList<PlaceDetailEntity> getRoad() {
        return road;
    }

    public ArrayList<PlaceDetailEntity> getEnvironment() {
        return environment;
    }

    public ArrayList<PlaceDetailEntity> getTree() {
        return tree;
    }

    public ArrayList<PlaceDetailEntity> getPark() {
        return park;
    }

    public ArrayList<PlaceDetailEntity> getOther() {
        return other;
    }
}
