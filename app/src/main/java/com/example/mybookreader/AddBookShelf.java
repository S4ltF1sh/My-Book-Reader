package com.example.mybookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.mybookreader.model.BookShelf;

public class AddBookShelf extends AppCompatActivity {

    EditText mBookShelfName;
    Button mBtnAddBookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_book_shelf);

        mBookShelfName = (EditText) findViewById(R.id.edt_bookshelf_name);
        mBtnAddBookShelf = (Button) findViewById(R.id.btn_addBookShelf);

        mBtnAddBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddBookShelf(String.valueOf(mBookShelfName.getText()));
            }
        });
    }

    private void onClickAddBookShelf(String bookshelfName) {
        Intent intent = new Intent(AddBookShelf.this, BookshelfFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("new_bookshelf", new BookShelf(bookshelfName));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}