package com.example.mybookreader.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBookActivity;
import com.example.mybookreader.activities.AddBookShelfActivity;
import com.example.mybookreader.activities.MainScreenActivity;
import com.example.mybookreader.adapter.BookShelfViewAdapter;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

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
public class BookshelfFragment extends Fragment {

    View mView;
    public static List<BookShelf> mListBookShelf = new ArrayList<>();
    private static boolean isCalled = false;

    private RecyclerView rcvBookShelf;
    private static BookShelfViewAdapter mBookShelfViewAdapter;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        BookShelf bookShelf = (BookShelf) intent.getExtras().get("new_bookshelf");
//                        mListBookShelf.add(bookShelf);
//                        mBookShelfViewAdapter.setData(mListBookShelf);

                        if (isBookshelfExisted(bookShelf)) {
                            Toast.makeText(getActivity(), "Đã tồn tại giá sách " + bookShelf.getName() + " trong thư viện!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        BookshelfDatabase.getInstance(getActivity()).bookshelfDAO().insertBookshelf(bookShelf);
                        Toast.makeText(getActivity(), "Đã thêm 1 giá sách vào thư viện", Toast.LENGTH_SHORT).show();

                        loadData();
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

        if (!isCalled) {
            loadData();
            isCalled = true;
        }

        Button btn_addNewBookShelf = (Button) mView.findViewById(R.id.btn_addNewBookShelf);
        btn_addNewBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddNewBookShelf();
            }
        });

        //list book view by RecyclerView
        rcvBookShelf = mView.findViewById(R.id.rcv_book_shelf);
        mBookShelfViewAdapter = new BookShelfViewAdapter(getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        rcvBookShelf.setLayoutManager(linearLayoutManager);
        mBookShelfViewAdapter.setData(mListBookShelf);
        rcvBookShelf.setAdapter(mBookShelfViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.custome_divider_item_decoration));
        rcvBookShelf.addItemDecoration(dividerItemDecoration);

        return mView;
    }

    private void onClickAddNewBookShelf() {
        ((MainScreenActivity) getActivity()).takePermission();

        if (((MainScreenActivity) getActivity()).isPermissionGranted()) {
            Intent intent = new Intent(getContext(), AddBookShelfActivity.class);
            mActivityResultLauncher.launch(intent);
        } else {
            Toast.makeText(getContext(), "Không có quyền truy cập bộ nhớ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        Log.v("info: ", "onStop() called");
        super.onStop();
    }

    @Override
    public void onResume() {
        if (mBookShelfViewAdapter != null) {
            mBookShelfViewAdapter.notifyDataSetChanged();
        }

        super.onResume();
    }

    public void loadData() {
        try {
            mListBookShelf = BookshelfDatabase.getInstance(getActivity()).bookshelfDAO().getListBookshelf();
            mBookShelfViewAdapter.setData(mListBookShelf);
        } catch (Exception e) {

        }
    }

    public boolean isBookshelfExisted(BookShelf bookShelf) {
        List<BookShelf> list = BookshelfDatabase.getInstance(getActivity()).bookshelfDAO().checkBookshelf(bookShelf.getName());
        return list != null && !list.isEmpty();
    }
}