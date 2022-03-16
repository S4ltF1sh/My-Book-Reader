package com.example.mybookreader;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mybookreader.model.Book;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AddBook extends AppCompatActivity {

    TextView mUriCover, mUriFile;
    EditText mTitle, Author;
    Button mLinkCover, mLinkFile, mAdd, mCancel;

    String title, uriFile, uriCover;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Uri selectedPDF = intent.getData();

                        uriFile = UriUtils.getPathFromUri(AddBook.this, selectedPDF);
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

                        uriCover = UriUtils.getPathFromUri(AddBook.this, selectedImage);
                        mUriCover.setText(uriCover);
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_book);

        mUriCover = findViewById(R.id.tv_uriCover);
        mUriFile = findViewById(R.id.tv_uriPDF);

        mTitle = findViewById(R.id.edt_title);
        Author = findViewById(R.id.edt_author);

        mLinkCover = findViewById(R.id.btn_addCover);
        mLinkFile = findViewById(R.id.btn_addFile);
        mAdd = findViewById(R.id.f_btn_addBook);
        mCancel = findViewById(R.id.f_btn_cancel);

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
                if (mTitle == null || mTitle.getText().equals("") || uriFile == null || uriCover == null)
                    return;
                title = String.valueOf(mTitle.getText());
                Intent intent = new Intent(AddBook.this, MainScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("new_book", new Book(title, uriCover, uriFile));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}