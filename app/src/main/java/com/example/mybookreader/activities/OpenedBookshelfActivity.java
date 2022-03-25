package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mybookreader.adapter.OpenedBookshelfAdapter;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.model.Book;

import java.util.ArrayList;
import java.util.List;

public class OpenedBookshelfActivity extends AppCompatActivity {

    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBook;
    private OpenedBookshelfAdapter mBookAdapter;

    private int position;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_book_shelf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position_of_bookshelf");

        title = BookshelfFragment.mListBookShelf.get(position).getName();

        //list book view by RecyclerView
        rcvBook = findViewById(R.id.rcv_book2);
        mBookAdapter = new OpenedBookshelfAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(BookshelfFragment.mListBookShelf.get(position).getListBook());
        rcvBook.setAdapter(mBookAdapter);

        getSupportActionBar().setTitle(title + " / " + String.valueOf(mBookAdapter.getItemCount()) + " cuốn");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public int getPosition(){
        return position;
    }
}