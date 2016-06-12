package com.mllweb.model;

import java.io.Serializable;

public class ThingFile implements Serializable {

    private int id;
    private int thingId;
    private String filePath;
    private int fileType;

    public ThingFile() {
    }

    public ThingFile(int fileType, String filePath) {
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThingId() {
        return thingId;
    }

    public void setThingId(int thingId) {
        this.thingId = thingId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

}
