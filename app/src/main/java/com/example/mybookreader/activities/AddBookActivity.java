package com.example.mybookreader.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mybookreader.R;
import com.example.mybookreader.utils.UriUtils;
import com.example.mybookreader.model.Book;


public class AddBookActivity extends AppCompatActivity {

    TextView mUriCover, mUriFile;
    EditText mTitle, mAuthor;
    Button mAdd;
    ConstraintLayout mLinkCover, mLinkFile;

    String title, author, uriFile, uriCover;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Uri selectedPDF = intent.getData();

                        uriFile = UriUtils.getPathFromUri(AddBookActivity.this, selectedPDF);
                        mUriFile.setText(uriFile);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> mActivityResultLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Uri selectedImage = intent.getData();

                        uriCover = UriUtils.getPathFromUri(AddBookActivity.this, selectedImage);
                        mUriCover.setText(uriCover);
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Book");

        mUriCover = findViewById(R.id.tv_uriCover);
        mUriFile = findViewById(R.id.tv_uriPDF);

        mTitle = findViewById(R.id.edt_title);
        mAuthor = findViewById(R.id.edt_author);

        mLinkCover = findViewById(R.id.ctl_addCover);
        mLinkFile = findViewById(R.id.ctl_addFile);
        mAdd = findViewById(R.id.f_btn_addBook);

        mLinkCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                mActivityResultLauncher2.launch(intent);
            }
        });

        mLinkFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                mActivityResultLauncher.launch(intent);
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitle.getText().toString().equals("") || mTitle.getText().equals("") || uriFile == null || uriCover == null)
                    return;
                title = String.valueOf(mTitle.getText());
                author = String.valueOf(mAuthor.getText());
                Intent intent = new Intent(AddBookActivity.this, MainScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("new_book", new Book(title, author, uriCover, uriFile));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}