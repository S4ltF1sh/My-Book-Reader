package com.example.mybookreader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mybookreader.model.BookShelf;

@Database(entities = {BookShelf.class}, version = 1)
public abstract class BookshelfDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "bookshelf.database";
    private static BookshelfDatabase instance;

    public static synchronized BookshelfDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BookshelfDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract BookshelfDAO bookshelfDAO();
}
