package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybookreader.adapter.AddBookToBookShelfAdapter;
import com.example.mybookreader.R;

public class AddBookToBookShelf extends AppCompatActivity {
    private RecyclerView rcvBookShelf;
    private AddBookToBookShelfAdapter mAddBookToBookShelfAdapter;

    private Button btn_addBookToNewBookShelf;

    public static int positionOfBookNeedToAddToBookshelf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_book_shelf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Book To BookShelf");

        Bundle bundle = getIntent().getExtras();
        positionOfBookNeedToAddToBookshelf = bundle.getInt("position_of_book_need_to_add_to_bookshelf");

        btn_addBookToNewBookShelf = findViewById(R.id.btn_add_book_to_new_bookshelf);
        btn_addBookToNewBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddBookToNewBookShelf();
            }
        });

        //list book view by RecyclerView
        rcvBookShelf = findViewById(R.id.rcv_add_book_to_bookshelf);
        mAddBookToBookShelfAdapter = new AddBookToBookShelfAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvBookShelf.setLayoutManager(linearLayoutManager);
        mAddBookToBookShelfAdapter.setData();
        rcvBookShelf.setAdapter(mAddBookToBookShelfAdapter);
        rcvBookShelf.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void onClickAddBookToNewBookShelf() {
    }


    public AddBookToBookShelf() {
        // Required empty public constructor
    }
}