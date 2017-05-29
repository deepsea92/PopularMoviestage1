package com.sharma.deepak.popularmoviestage1.bean;

/**
 * Created by deepak on 27-05-2017.
 */

public class Trailer {
    private String key, type, name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trailer(String key, String type, String name) {

        this.key = key;
        this.type = type;
        this.name = name;
    }
}
