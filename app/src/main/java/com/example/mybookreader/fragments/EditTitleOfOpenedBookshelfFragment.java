package com.example.mybookreader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mybookreader.R;
import com.example.mybookreader.activities.OpenedBookshelfActivity;
import com.example.mybookreader.utils.Util;

public class EditTitleOfOpenedBookshelfFragment extends Fragment {

    private View mView;
    private EditText edt_newTitle;
    private ImageView imv_closeButton, imv_checkedButton;

    public EditTitleOfOpenedBookshelfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_edit_title_in_opened_bookshelf_activity, container, false);

        setViewAndValue();
        setOnClick();

        return mView;
    }

    private void setViewAndValue() {
        edt_newTitle = mView.findViewById(R.id.edt_new_title);
        imv_closeButton = mView.findViewById(R.id.imv_close_button);
        imv_checkedButton = mView.findViewById(R.id.imv_checked_button);

        String oldTitle = (String) getArguments().getString("old_title");
        edt_newTitle.setText(oldTitle);
        edt_newTitle.requestFocus();
        Util.showKeyBoard(getActivity());
    }

    private void setOnClick() {
        imv_closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                Util.hideKeyBoard(getActivity());
            }
        });

        imv_checkedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBarInOpenedBookshelfActivityFragment();
            }
        });
    }

    private void openBarInOpenedBookshelfActivityFragment() {
        if (edt_newTitle.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Hãy nhập tên giá sách!", Toast.LENGTH_SHORT).show();
            return;
        }
        ((OpenedBookshelfActivity) getActivity()).setTitleBookshelf(edt_newTitle.getText().toString().trim());
        getActivity().getSupportFragmentManager().popBackStack();
        Util.hideKeyBoard(getActivity());
    }
}