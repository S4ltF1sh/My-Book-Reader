package com.example.mybookreader.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.OpenedBookshelfActivity;

public class BarInOpenedBookshelfFragment extends Fragment {
    public final static int FRAGMENT_CODE = 182002;

    private View mView;
    private LinearLayout lnl_openedBookshelfNameAndNumberOfBooks;

    private String title, numOfBooks = "Trống";
    private ImageView imv_back_arrow_button, imv_more_button;
    private TextView tv_Title, tv_numberOfBooks;

    private int position;

    public BarInOpenedBookshelfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        title = ((OpenedBookshelfActivity) getActivity()).getTitleBookshelf();
        tv_Title.setText(title);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bar_in_opened_bookshelf_activity, container, false);

        position = ((OpenedBookshelfActivity) getActivity()).getPosition();
        title = ((OpenedBookshelfActivity) getActivity()).getTitleBookshelf();

        if (BookshelfFragment.mListBookShelf.get(position).getListBook().size() > 0) {
            numOfBooks = String.valueOf(BookshelfFragment.mListBookShelf.get(position).getListBook().size()) + " cuốn";
        }

        setViewIdAndValue();
        setOnClick();

        return mView;
    }

    private void setViewIdAndValue() {
        lnl_openedBookshelfNameAndNumberOfBooks = mView.findViewById(R.id.lnl_opened_bookshelf_name_and_number_of_books);
        imv_back_arrow_button = mView.findViewById(R.id.imv_back_arrow_button);
        imv_more_button = mView.findViewById(R.id.imv_more_button);
        tv_Title = mView.findViewById(R.id.tv_opened_bookshelf_name);
        tv_numberOfBooks = mView.findViewById(R.id.tv_number_of_books_in_opened_bookshelf);

        tv_Title.setText(title);
        tv_numberOfBooks.setText(numOfBooks);
    }

    private void setOnClick() {
        imv_back_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        imv_more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lnl_openedBookshelfNameAndNumberOfBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditTitleOfOpenedBookshelfFragment();
            }
        });
    }

    private void openEditTitleOfOpenedBookshelfFragment() {
        EditTitleOfOpenedBookshelfFragment nextFrag = new EditTitleOfOpenedBookshelfFragment();
        Bundle bundle = new Bundle();
        bundle.putString("old_title", title);
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frl_fragment_content, nextFrag)
                .addToBackStack(null)
                .commit();
    }
}