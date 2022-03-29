package com.example.mybookreader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;

import com.example.mybookreader.R;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.model.Book;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;

import java.io.File;
import java.util.List;

public class PDFOpenerActivity extends AppCompatActivity {

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
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .autoSpacing(true)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                //.spacing(5)
                .load();

        setProgressBarIndeterminate(true);
    }

    @Override
    public void onBackPressed() {
        int id = bookNeedToBeOpened.getId(), currentPage = pdfView.getCurrentPage();
        for (int i = 0; i < HomeFragment.listBook.size(); i++) {
            if (HomeFragment.listBook.get(i).getId() == id) {
                HomeFragment.listBook.get(i).setSavedPage(currentPage);
                BookDatabase.getInstance(this).bookDAO().updateBook(HomeFragment.listBook.get(i));
                break;
            }
        }
        for (int i = 0; i < BookshelfFragment.mListBookShelf.size(); i++) {
            List<Book> tmpListBook = BookshelfFragment.mListBookShelf.get(i).getListBook();
            for (int j = 0; j < tmpListBook.size(); j++) {
                if (id == tmpListBook.get(j).getId()) {
                    tmpListBook.get(j).setSavedPage(currentPage);
                    BookshelfFragment.mListBookShelf.get(i).setListBook(tmpListBook);
                    BookshelfDatabase.getInstance(this).bookshelfDAO().updateBookshelf(BookshelfFragment.mListBookShelf.get(i));
                    break;
                }
            }
        }
        super.onBackPressed();
    }
}