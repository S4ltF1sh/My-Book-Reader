package com.example.mybookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;

import com.example.mybookreader.model.Book;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFOpener extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pdfreader);

        pdfView = findViewById(R.id.pdfView);

        //Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));
        pdfView.fromFile(new File(getIntent().getStringExtra("FileUri")))
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();

        setProgressBarIndeterminate(true);
    }
}