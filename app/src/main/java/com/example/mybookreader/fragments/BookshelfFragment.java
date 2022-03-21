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
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.AddBookShelfActivity;
import com.example.mybookreader.adapter.BookShelfViewAdapter;
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
                        mListBookShelf.add(bookShelf);
                        mBookShelfViewAdapter.setData(mListBookShelf);
                        try {
                            writeDataIntoFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            readDataFromFile();
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
        rcvBookShelf.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return mView;
    }

    private void onClickAddNewBookShelf() {
        Intent intent = new Intent(getContext(), AddBookShelfActivity.class);
        mActivityResultLauncher.launch(intent);
    }

    @Override
    public void onStop() {
        try {
            writeDataIntoFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("info: ", "onStop() called");
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBookShelfViewAdapter != null) {
            mBookShelfViewAdapter.notifyDataSetChanged();
        }
    }

    public void readDataFromFile() {
        FileInputStream fis = null;
        ObjectInputStream objin = null;
        mListBookShelf.clear();

        try {
            fis = getActivity().openFileInput("com\\example\\mybookreader\\data\\BOOK_SHELF_DATA.txt");
            objin = new ObjectInputStream(fis);
            mListBookShelf = (List<BookShelf>) objin.readObject();

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

        for (int i = 1; i <= mListBookShelf.size(); i++) {
            BookShelf temp = mListBookShelf.get(i - 1);
            temp.setId(i);
            mListBookShelf.set(i - 1, temp);
        }
        Book.idnum = mListBookShelf.size();
    }

    public void writeDataIntoFile() throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream objout = null;

        try {
            fos = this.getActivity().openFileOutput("com\\example\\mybookreader\\data\\BOOK_SHELF_DATA.txt", getActivity().MODE_PRIVATE);
            objout = new ObjectOutputStream(fos);
            objout.writeObject(mListBookShelf);
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

    public static void removeBookFromAllBookshelf(int id) {
        for (int i = 0; i < mListBookShelf.size(); i++) {
            List<Book> tmpListBook = mListBookShelf.get(i).getListBook();
            for (int j = 0; j < tmpListBook.size(); j++) {
                if (id == tmpListBook.get(j).getId()) {
                    tmpListBook.remove(j);
                    mListBookShelf.get(i).setListBook(tmpListBook);
                    mBookShelfViewAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        //mBookShelfViewAdapter.setData(mListBookShelf);
    }

}