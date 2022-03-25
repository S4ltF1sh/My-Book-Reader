package com.example.mybookreader.database;

import androidx.room.TypeConverter;

import com.example.mybookreader.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Book> fromString(String value) {
        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Book> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
