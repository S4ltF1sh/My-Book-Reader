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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;
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
                        try {
                            writeDataIntoFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (!isCalled) {
            readDataFromFile();
            isCalled = true;
        }

        //list book view by RecyclerView
        rcvBook = findViewById(R.id.rcv_book);
        mBookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(listBook);
        rcvBook.setAdapter(mBookAdapter);

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

    @Override
    protected void onStop() {
        try {
            writeDataIntoFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onStop();
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

    public void readDataFromFile() {
        FileInputStream fis = null;
        ObjectInputStream objin = null;
        listBook.clear();

        try {
            fis = openFileInput("com\\example\\mybookreader\\data\\DATA.txt");
            objin = new ObjectInputStream(fis);
            listBook = (List<Book>) objin.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (objin != null) {
                    objin.close();
                }
            } catch (Exception e) {
            }
        }

        for (int i = 1; i <= listBook.size(); i++) {
            Book temp = listBook.get(i - 1);
            temp.setId(i);
            listBook.set(i - 1, temp);
        }
        Book.idnum = listBook.size();
    }

    public void writeDataIntoFile() throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream objout = null;

        try {
            fos = this.openFileOutput("com\\example\\mybookreader\\data\\DATA.txt", MODE_PRIVATE);
            objout = new ObjectOutputStream(fos);
            objout.writeObject(listBook);
            objout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (objout != null) {
                    objout.close();
                }
            } catch (Exception e) {

            }
        }
    }
}

