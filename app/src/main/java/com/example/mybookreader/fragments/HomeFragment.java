package com.example.mybookreader.fragments;

import static android.app.Activity.RESULT_OK;

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
//import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBookActivity;
import com.example.mybookreader.activities.MainScreenActivity;
import com.example.mybookreader.adapter.AllBookAdapter;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View mView;
    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBook;
    private static AllBookAdapter mAllBookAdapter;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Book book;
                        if (intent != null) {
                            book = (Book) intent.getExtras().get("new_book");
                        } else {
                            return;
                        }

                        BookDatabase.getInstance(getActivity()).bookDAO().insertBook(book);
                        Toast.makeText(getActivity(), "Đã thêm 1 cuốn sách vào thư viện", Toast.LENGTH_SHORT).show();

                        loadData();
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

        //((MainScreenActivity) getActivity()).Hello();

        if (!isCalled) {
            loadData();
            isCalled = true;
        }

        //list book view by RecyclerView
        rcvBook = mView.findViewById(R.id.rcv_book);
        mAllBookAdapter = new AllBookAdapter(getContext());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        rcvBook.setLayoutManager(gridLayoutManager);
        mAllBookAdapter.setData(listBook);
        rcvBook.setAdapter(mAllBookAdapter);

        //add new book
        Button btn_addBook = mView.findViewById(R.id.btn_addBook);
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBookActivity.class);
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
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainScreenActivity) getActivity()).getSupportActionBar()
                .setTitle(String.valueOf(listBook.size()) + " cuốn");

        if (mAllBookAdapter != null) {
            mAllBookAdapter.setData(listBook);
        }
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
                    mAllBookAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAllBookAdapter.getFilter().filter(newText);
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

    public void loadData() {
        try {
            listBook = BookDatabase.getInstance(getActivity()).bookDAO().getListBook();
            mAllBookAdapter.setData(listBook);
        } catch (Exception e) {

        }
    }
}