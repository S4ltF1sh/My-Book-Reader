package com.example.mybookreader;

import android.content.Context;

import com.example.mybookreader.model.Book;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class IOData {
    public static void readDataFromFile(Context context, List<Book> listBook) {
        FileInputStream fis = null;
        ObjectInputStream objin = null;

        try {
            fis = context.openFileInput("com\\example\\mybookreader\\data\\DATA.txt");
            objin = new ObjectInputStream(fis);
            listBook = (List<Book>) objin.readObject();

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
                e.printStackTrace();
            }
        }

        Book.idnum = listBook.size();
    }

    public static void writeDataIntoFile(Context context, List<Book> listBook) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream objout = null;

        try {

            fos = context.openFileOutput("com\\example\\mybookreader\\data\\DATA.txt", context.MODE_PRIVATE);
            objout = new ObjectOutputStream(fos);
            objout.writeObject(listBook);
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
                e.printStackTrace();
            }
        }
    }
}
