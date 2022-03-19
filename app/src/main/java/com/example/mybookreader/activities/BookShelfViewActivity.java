package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;

import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.adapter.BookAdapter;
import com.example.mybookreader.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookShelfViewActivity extends AppCompatActivity {

    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBook;
    private BookAdapter mBookAdapter;

    private int position;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");

        title = BookshelfFragment.mListBookShelf.get(position).getName();
        getSupportActionBar().setTitle(title);

        //list book view by RecyclerView
        rcvBook = findViewById(R.id.rcv_book2);
        mBookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(BookshelfFragment.mListBookShelf.get(position).getListBook());
        rcvBook.setAdapter(mBookAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}