package com.example.mybookreader;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybookreader.adapter.BookAdapter;
import com.example.mybookreader.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private List<Book> listBook = new ArrayList<>();
    TextView txt_pathShow;
    private Button btn_addBook;

    private RecyclerView rcvBook;
    private BookAdapter mBookAdapter;
    private SearchView searchView;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Book book = (Book) intent.getExtras().get("new_book");
                        listBook.add(book);
                        mBookAdapter.setData(listBook);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        txt_pathShow = (TextView) findViewById(R.id.txt_pathShow);

        //list book view by RecyclerView
        rcvBook = findViewById(R.id.rcv_book);
        mBookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(listBook);
        rcvBook.setAdapter(mBookAdapter);

        loadBook();

        //add new book
        btn_addBook = (Button) findViewById(R.id.btn_addBook);
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, AddBook.class);
                mActivityResultLauncher.launch(intent);
            }
        });
    }

    private void loadBook() {
//        listBook.add(new Book("Ma thổi đèn 1", R.drawable.cover1, "âsđâsda"));
//        listBook.add(new Book("Ma thổi đèn 2", R.drawable.cover2, "áđâsđá"));
//        listBook.add(new Book("Ma thổi đèn 3", R.drawable.cover3));
//        listBook.add(new Book("Ma thổi đèn 4", R.drawable.cover4));
//        listBook.add(new Book("Ma thổi đèn 5", R.drawable.cover5));
//        listBook.add(new Book("Ma thổi đèn 6", R.drawable.cover6));
//        listBook.add(new Book("Ma thổi đèn 7", R.drawable.cover7));
//        listBook.add(new Book("Ma thổi đèn 8", R.drawable.cover8));
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconfiedByDefault()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBookAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mBookAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}

