package com.example.mybookreader.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String coverPath;
    private String path;
    private int savedPage;
    private int id = 0;
    public static int idnum = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String name, String coverPath, String path) {
        this.name = name;
        this.coverPath = coverPath;
        this.path = path;
        this.savedPage = 0;
        idnum++;
        id = idnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSavedPage() {
        return savedPage;
    }

    public void setSavedPage(int savedPage) {
        this.savedPage = savedPage;
    }
}
