package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mybookreader.adapter.OpenedBookshelfAdapter;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BarInOpenedBookshelfFragment;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.fragments.EditTitleOfOpenedBookshelfFragment;
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
        getSupportActionBar().hide();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frl_fragment_content, new BarInOpenedBookshelfFragment());
        fragmentTransaction.commit();

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position_of_bookshelf");
        title = BookshelfFragment.mListBookShelf.get(position).getName();

        setViewId();
        setOnClick();

        //list book view by RecyclerView
        mBookAdapter = new OpenedBookshelfAdapter(this);
        mBookAdapter.setData(BookshelfFragment.mListBookShelf.get(position).getListBook());
        rcvBook.setAdapter(mBookAdapter);
    }

    private void setViewId() {
        rcvBook = findViewById(R.id.rcv_book2);
    }

    private void setOnClick() {
    }

    public int getPosition() {
        return position;
    }

    public String getTitleBookshelf() {
        return title;
    }

    public void setTitleBookshelf(String newTitle) {
        title = newTitle;
        BookshelfFragment.mListBookShelf.get(position).setName(newTitle);
        BookshelfDatabase.getInstance(this).bookshelfDAO().updateBookshelf(BookshelfFragment.mListBookShelf.get(position));
    }
}