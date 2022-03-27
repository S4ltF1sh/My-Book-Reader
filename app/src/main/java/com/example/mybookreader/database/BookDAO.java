package com.example.mybookreader.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybookreader.model.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insertBook(Book book);

    @Query("SELECT * FROM book")
    List<Book> getListBook();

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);

    //search LIKE:
    @Query("SELECT * FROM book WHERE name LIKE '%' || :name || '%'")
    List<Book> searchBook(String name);
}
