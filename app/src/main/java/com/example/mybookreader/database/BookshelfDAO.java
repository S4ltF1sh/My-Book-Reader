package com.example.mybookreader.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.util.List;

@Dao
public interface BookshelfDAO {
    @Insert
    void insertBookshelf(BookShelf bookshelf);

    @Query("SELECT * FROM bookshelf")
    List<BookShelf> getListBookshelf();

    @Query("SELECT * FROM bookshelf WHERE name= :name")
    List<BookShelf> checkBookshelf(String name);

    @Update
    void updateBookshelf(BookShelf bookshelf);

    @Delete
    void deleteBookshelf(BookShelf bookshelf);
}
