package com.example.mybookreader.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mybookreader.database.Converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "bookshelf")
@TypeConverters({Converters.class})
public class BookShelf implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<Book> listBook;
    private String name;

    //public static int idNum = 0;

    public BookShelf() {
    }

    public BookShelf(String name) {
        this.name = name;
        this.listBook = new ArrayList<>();
//        idNum++;
//        id = idNum;
    }

    public BookShelf(List<Book> listBook, String name) {
        this.listBook = listBook;
        this.name = name;
//        idNum++;
//        id = idNum;
    }

    public List<Book> getListBook() {
        return listBook;
    }

    public void setListBook(List<Book> listBook) {
        this.listBook = listBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
