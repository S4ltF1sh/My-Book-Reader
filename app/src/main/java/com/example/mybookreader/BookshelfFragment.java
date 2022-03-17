package com.example.mybookreader;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybookreader.adapter.BookAdapter;
import com.example.mybookreader.adapter.BookShelfAdapter;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookshelfFragment extends Fragment {

    View mView;
    private static List<BookShelf> mListBookShelf = new ArrayList<>();

    private RecyclerView rcvBookShelf;
    private BookShelfAdapter mBookShelfAdapter;
    private List<Book> mlistBook = new ArrayList<>();

    public BookshelfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        setHasOptionsMenu(true);

        loadBookShelf();

        //list book view by RecyclerView
        rcvBookShelf = mView.findViewById(R.id.rcv_book_shelf);
        mBookShelfAdapter = new BookShelfAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvBookShelf.setLayoutManager(linearLayoutManager);
        mBookShelfAdapter.setData(mListBookShelf);
        rcvBookShelf.setAdapter(mBookShelfAdapter);
        rcvBookShelf.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));

        return mView;
    }


    public void loadBookShelf() {
        mlistBook.add(new Book("Ma thổi đèn 1", "", ""));
        mListBookShelf.add(new BookShelf(mlistBook, "Ma thổi đèn"));
        mListBookShelf.add(new BookShelf(mlistBook, "Phàm nhân tu tiên"));
        mListBookShelf.add(new BookShelf(mlistBook, "Vũ Hạ Đích Hảo Đại"));
        mListBookShelf.add(new BookShelf(mlistBook, "Ma thổi đèn"));
    }
}