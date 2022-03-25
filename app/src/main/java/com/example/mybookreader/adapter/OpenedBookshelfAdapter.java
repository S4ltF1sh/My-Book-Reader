package com.example.mybookreader.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookreader.activities.OpenedBookshelfActivity;
import com.example.mybookreader.activities.PDFOpenerActivity;
import com.example.mybookreader.R;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.model.Book;

import java.io.File;
import java.util.List;

public class OpenedBookshelfAdapter extends RecyclerView.Adapter<OpenedBookshelfAdapter.OpenedBookshelfViewHolder> {
    private final Context mContext;
    private List<Book> mListBook;
    private int positionOfBookshelf;

    public OpenedBookshelfAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Book> mListBook) {
        this.mListBook = mListBook;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OpenedBookshelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_view, parent, false);
        return new OpenedBookshelfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenedBookshelfViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = mListBook.get(position);
        if (book == null)
            return;

        File imgFile = new File(book.getCoverPath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgCover.setImageBitmap(myBitmap);
        }
        holder.tvName.setText(book.getName());

        holder.layoutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOpenBook(book);
            }
        });

        holder.layoutItems.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_for_books_in_opened_bookshelf, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.read_for_books_in_opened_bookshelf:
                                onClickOpenBook(book);
                                break;
                            case R.id.remove_from_bookshelf_for_books_in_opened_bookshelf:
                                removeBookFromBookshelf(position);
                                break;
                            case R.id.remove_from_library_for_books_in_opened_bookshelf:
                                removeBookFromLibrary(position);
                        }
                        return false;
                    }
                });
                return false;
            }
        });
    }

    private void removeBookFromLibrary(int position) {
        new AlertDialog.Builder(mContext)
                .setTitle("Có phải bạn muốn xóa khỏi thư viện?")
                .setMessage("Cuốn sách này sẽ bị xóa khỏi toàn bộ những giá sách cũng như màn hình chính.")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = mListBook.get(position).getId();

                        for (int j = 0; j < HomeFragment.listBook.size(); j++) {
                            if (id == HomeFragment.listBook.get(j).getId()) {
                                BookDatabase.getInstance(mContext).bookDAO().deleteBook(HomeFragment.listBook.get(j));
                                HomeFragment.listBook = BookDatabase.getInstance(mContext).bookDAO().getListBook();
                                break;
                            }
                        }

                        for (int j = 0; j < BookshelfFragment.mListBookShelf.size(); j++) {
                            List<Book> tmpListBook = BookshelfFragment.mListBookShelf.get(j).getListBook();
                            for (int k = 0; k < tmpListBook.size(); k++) {
                                if (id == tmpListBook.get(k).getId()) {
                                    tmpListBook.remove(k);
                                    BookshelfFragment.mListBookShelf.get(j).setListBook(tmpListBook);
                                    BookshelfDatabase.getInstance(mContext).bookshelfDAO().updateBookshelf(BookshelfFragment.mListBookShelf.get(j));
                                    break;
                                }
                            }
                        }

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mListBook.size());

                        Toast.makeText(mContext, "Đã xóa 1 cuốn sách ra khỏi thư viện!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void removeBookFromBookshelf(int position) {
        mListBook.remove(position);

        int position_of_bookshelf = ((OpenedBookshelfActivity) mContext).getPosition();
        BookshelfFragment.mListBookShelf.get(position_of_bookshelf).setListBook(mListBook);
        BookshelfDatabase.getInstance(mContext).bookshelfDAO().updateBookshelf(BookshelfFragment.mListBookShelf.get(position_of_bookshelf));

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mListBook.size());

        Toast.makeText(mContext, "Đã xóa 1 cuốn sách ra khỏi " + BookshelfFragment.mListBookShelf.get(position_of_bookshelf).getName() + "!",
                Toast.LENGTH_SHORT).show();
    }

    private void onClickOpenBook(Book book) {
        Intent intent = new Intent(mContext, PDFOpenerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book_need_to_be_opened", book);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListBook != null)
            return mListBook.size();
        return 0;
    }

    public static class OpenedBookshelfViewHolder extends RecyclerView.ViewHolder {
        private CardView layoutItems;
        private ImageView imgCover;
        private TextView tvName;

        public OpenedBookshelfViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutItems = itemView.findViewById(R.id.layoutItem);
            imgCover = itemView.findViewById(R.id.im_cover);
            tvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
