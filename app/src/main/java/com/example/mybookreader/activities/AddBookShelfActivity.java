package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.model.BookShelf;
import com.example.mybookreader.utils.Util;

public class AddBookShelfActivity extends AppCompatActivity {

    EditText mBookShelfName;
    Button mBtnAddBookShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_book_shelf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Khởi tạo giá sách mới");

        setIdAndValue();
        setListener();

        Util.showKeyBoard(this);
    }

    private void setIdAndValue() {
        mBookShelfName = findViewById(R.id.edt_bookshelf_name);
        mBookShelfName.requestFocus();
        mBtnAddBookShelf = findViewById(R.id.btn_addBookShelf);
    }

    private void setListener() {
        mBtnAddBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddBookShelf(String.valueOf(mBookShelfName.getText()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Util.hideKeyBoard(this);
        finish();
        return true;
    }

    private void onClickAddBookShelf(String bookshelfName) {
        if (bookshelfName.equals("")) {
            Toast.makeText(this, "Hãy nhập tên giá sách!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AddBookShelfActivity.this, BookshelfFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("new_bookshelf", new BookShelf(bookshelfName));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        Util.hideKeyBoard(this);
        finish();
    }
}