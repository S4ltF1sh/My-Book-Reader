package com.example.mybookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;

import com.example.mybookreader.adapter.BookAdapter;
import com.example.mybookreader.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookShelfActivity extends AppCompatActivity {

    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBook;
    private BookAdapter mBookAdapter;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book_shelf);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");

        //list book view by RecyclerView
        rcvBook = findViewById(R.id.rcv_book2);
        mBookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(BookshelfFragment.mListBookShelf.get(position).getListBook());
        rcvBook.setAdapter(mBookAdapter);
    }
}