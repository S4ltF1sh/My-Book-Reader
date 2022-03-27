package com.example.mybookreader.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBookActivity;
import com.example.mybookreader.adapter.AllBookAdapter;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View mView;
    public static List<Book> listBook = new ArrayList<>();
    private static boolean isCalled = false;

    private EditText edt_searchBar;
    private Handler mHandler = new Handler();
    Runnable mFilterTask = new Runnable() {

        @Override
        public void run() {
            searchBook();
        }
    };

    private RecyclerView rcvBook;
    private static AllBookAdapter mAllBookAdapter;

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

        edt_searchBar = mView.findViewById(R.id.edt_search_bar);
//        edt_searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
////                if (i == EditorInfo.IME_ACTION_SEARCH) {
////                    handleSearchBook();
////                }
//                handleSearchBook();
//                return false;
//            }
//        });
        edt_searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                searchBook();
                mHandler.removeCallbacks(mFilterTask);
                mHandler.postDelayed(mFilterTask, 300);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        if (!isCalled) {
            loadData();
            isCalled = true;
        }

        //list book view by RecyclerView
        rcvBook = mView.findViewById(R.id.rcv_book);
        mAllBookAdapter = new AllBookAdapter(getContext());
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

    private void searchBook() {
        String keyWord = edt_searchBar.getText().toString().trim();
        List<Book> tmpListBook = new ArrayList<>();
        tmpListBook = BookDatabase.getInstance(getActivity()).bookDAO().searchBook(keyWord);
        mAllBookAdapter.setData(tmpListBook);
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

        if (mAllBookAdapter != null) {
            mAllBookAdapter.setData(listBook);
        }
    }

    public void loadData() {
        try {
            listBook = BookDatabase.getInstance(getActivity()).bookDAO().getListBook();
            mAllBookAdapter.setData(listBook);
        } catch (Exception e) {

        }
    }
}