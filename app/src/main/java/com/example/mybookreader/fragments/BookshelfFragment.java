package com.example.mybookreader.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBookShelf;
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
    public static List<BookShelf> mListBookShelf = new ArrayList<>();

    private RecyclerView rcvBookShelf;
    private BookShelfAdapter mBookShelfAdapter;
    private List<Book> mlistBook = new ArrayList<>();

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        BookShelf bookShelf = (BookShelf) intent.getExtras().get("new_bookshelf");
                        mListBookShelf.add(bookShelf);
                        mBookShelfAdapter.setData(mListBookShelf);
//                        try {
//                            writeDataIntoFile();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
    );

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

        Button btn_addNewBookShelf = (Button) mView.findViewById(R.id.btn_addNewBookShelf);
        btn_addNewBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddNewBookShelf();
            }
        });

        //loadBookShelf();

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

    private void onClickAddNewBookShelf() {
        Intent intent = new Intent(getContext(), AddBookShelf.class);
        mActivityResultLauncher.launch(intent);
    }


    public void loadBookShelf() {
        mlistBook.add(new Book("Ma thổi đèn 1", "", ""));
        mListBookShelf.add(new BookShelf(mlistBook, "Ma thổi đèn"));
        mListBookShelf.add(new BookShelf(mlistBook, "Phàm nhân tu tiên"));
        mListBookShelf.add(new BookShelf(mlistBook, "Vũ Hạ Đích Hảo Đại"));
        mListBookShelf.add(new BookShelf(mlistBook, "Ma thổi đèn"));
    }
}