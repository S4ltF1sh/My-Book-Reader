package com.example.mybookreader.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBook;
import com.example.mybookreader.activities.MainScreen;
import com.example.mybookreader.adapter.BookAdapter;
import com.example.mybookreader.model.Book;
import com.google.android.material.tabs.TabLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View mView;
    private TabLayout mTabLayout;

    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBook;
    private BookAdapter mBookAdapter;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        ((MainScreen) getActivity()).Hello();

        if (!isCalled) {
            readDataFromFile();
            isCalled = true;
        }

        //list book view by RecyclerView
        rcvBook = mView.findViewById(R.id.rcv_book);
        mBookAdapter = new BookAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvBook.setLayoutManager(gridLayoutManager);
        mBookAdapter.setData(listBook);
        rcvBook.setAdapter(mBookAdapter);

        //add new book
        Button btn_addBook = (Button) mView.findViewById(R.id.btn_addBook);
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBook.class);
                mActivityResultLauncher.launch(intent);
            }
        });
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        try {
            writeDataIntoFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            //searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            queryTextListener = new SearchView.OnQueryTextListener() {
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
            };

            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    public void readDataFromFile() {
        FileInputStream fis = null;
        ObjectInputStream objin = null;
        listBook.clear();

        try {
            fis = getActivity().openFileInput("com\\example\\mybookreader\\data\\DATA.txt");
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
            fos = this.getActivity().openFileOutput("com\\example\\mybookreader\\data\\DATA.txt", getActivity().MODE_PRIVATE);
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