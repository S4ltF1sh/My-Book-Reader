package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;

import com.example.mybookreader.R;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.model.Book;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFOpener extends AppCompatActivity {

    PDFView pdfView;
    Book bookNeedToBeOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pdfreader);

        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        bookNeedToBeOpened = (Book) bundle.get("book_need_to_be_opened");

        //Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));
        pdfView.fromFile(new File(bookNeedToBeOpened.getPath()))
                .defaultPage(bookNeedToBeOpened.getSavedPage())
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();

        setProgressBarIndeterminate(true);
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < HomeFragment.listBook.size(); i++) {
            if (HomeFragment.listBook.get(i).getId() == bookNeedToBeOpened.getId()) {
                HomeFragment.listBook.get(i).setSavedPage(pdfView.getCurrentPage());
                break;
            }
        }
        super.onBackPressed();
    }
}