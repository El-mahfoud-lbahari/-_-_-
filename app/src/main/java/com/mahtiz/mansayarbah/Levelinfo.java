package com.mahtiz.mansayarbah;

public class Levelinfo {
    private String Levelname;
    private int photoId;

    public String getLevelname() {
        return Levelname;
    }

    public void setLevelname(String name) {
        this.Levelname = name;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getPhotoId() {
        return photoId;
    }
}
