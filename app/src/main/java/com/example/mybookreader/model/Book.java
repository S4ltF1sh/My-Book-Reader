package com.example.mybookreader.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "book")
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String author;
    private String coverPath;
    private String path;
    private int savedPage;
    //public static int idNum = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String name, String author, String coverPath, String path) {
        this.name = name;
        this.author = author;
        this.coverPath = coverPath;
        this.path = path;
        this.savedPage = 0;
        //idNum++;
        //id = idNum;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

//    public static int getIdNum() {
//        return idNum;
//    }
}
