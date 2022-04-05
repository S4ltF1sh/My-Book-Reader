package com.example.mybookreader.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybookreader.adapter.AddBookToBookShelfAdapter;
import com.example.mybookreader.R;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.util.ArrayList;
import java.util.List;

public class AddBookToBookShelfActivity extends AppCompatActivity {
    private RecyclerView rcvBookShelf;
    private AddBookToBookShelfAdapter mAddBookToBookShelfAdapter;

    private Button btn_addBookToNewBookShelf;

    public static int positionOfBookNeedToAddToBookshelf;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        BookShelf bookShelf = (BookShelf) intent.getExtras().get("new_bookshelf");

                        List<Book> tmpBookshelf = new ArrayList<>();
                        tmpBookshelf.add(HomeFragment.listBook.get(AddBookToBookShelfActivity.positionOfBookNeedToAddToBookshelf));
                        bookShelf.setListBook(tmpBookshelf);
//                        BookshelfFragment.mListBookShelf.add(bookShelf);
                        BookshelfDatabase.getInstance(AddBookToBookShelfActivity.this).bookshelfDAO().insertBookshelf(bookShelf);
                        Toast.makeText(AddBookToBookShelfActivity.this, "Đã thêm 1 giá sách vào thư viện", Toast.LENGTH_SHORT).show();

                        BookshelfFragment.mListBookShelf = BookshelfDatabase.getInstance(AddBookToBookShelfActivity.this).bookshelfDAO().getListBookshelf();
                        finish();
                    }
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_book_shelf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm sách vào giá sách");

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
        Intent intent = new Intent(AddBookToBookShelfActivity.this, AddBookShelfActivity.class);
        mActivityResultLauncher.launch(intent);
    }


    public AddBookToBookShelfActivity() {
        // Required empty public constructor
    }
}