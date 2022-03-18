package com.example.mybookreader.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookShelf implements Serializable {
    private List<Book> listBook;
    private String name;
    private int id = 0;
    public static int idNum = 0;

    public BookShelf() {
    }

    public BookShelf(String name) {
        this.name = name;
        this.listBook = new ArrayList<>();
        idNum++;
        id = idNum;
    }

    public BookShelf(List<Book> listBook, String name) {
        this.listBook = listBook;
        this.name = name;
        idNum++;
        id = idNum;
    }

    public void add(Book book) {
        listBook.add(book);
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
