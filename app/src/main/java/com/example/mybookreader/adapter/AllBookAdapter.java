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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookreader.activities.AddBookToBookShelfActivity;
import com.example.mybookreader.database.BookDatabase;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.activities.PDFOpenerActivity;
import com.example.mybookreader.R;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllBookAdapter extends RecyclerView.Adapter<AllBookAdapter.AllBookViewHolder> implements Filterable {
    private final Context mContext;
    private List<Book> mListBook;

    public AllBookAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Book> mListBook) {
        this.mListBook = mListBook;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_view, parent, false);
        return new AllBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBookViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = mListBook.get(position);
        if (book == null)
            return;

        File imgFile = new File(book.getCoverPath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgCover.setImageBitmap(myBitmap);
        }
        holder.tvName.setText(book.getName());
        //holder.tvAuthor.setText(book.getAuthor());
        holder.tvAuthor.setText(String.valueOf(book.getId()));

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
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_for_books_in_home_fragment, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.read_for_book_in_home_fragment:
                                onClickOpenBook(book);
                                break;
                            case R.id.add_to_bookshelf_for_book_in_home_fragment:
                                onClickAddBookToBookShelf(position);
                                break;
                            case R.id.remove_for_book_in_home_fragment:
                                removeBookFromLibrary(position, view);
                                break;
                        }
                        return false;
                    }
                });
                return false;
            }
        });
    }

    private void onClickAddBookToBookShelf(int position) {
        Intent intent = new Intent(mContext, AddBookToBookShelfActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("position_of_book_need_to_add_to_bookshelf", position);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void removeBookFromLibrary(int position, View view) {
        new AlertDialog.Builder(mContext)
                .setTitle("Có phải bạn muốn xóa khỏi thư viện?")
                .setMessage("Cuốn sách này sẽ bị xóa khỏi toàn bộ những giá sách cũng như màn hình chính.")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = mListBook.get(position).getId();
                        mListBook.remove(position);

                        for (int j = 0; j < HomeFragment.listBook.size(); j++) {
                            if (id == HomeFragment.listBook.get(j).getId()) {
                                BookDatabase.getInstance(mContext).bookDAO().deleteBook(HomeFragment.listBook.get(j));
                                HomeFragment.listBook = BookDatabase.getInstance(mContext).bookDAO().getListBook();
                                notifyItemRemoved(j);
                                notifyItemRangeChanged(j, mListBook.size());
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

//                        setData(HomeFragment.listBook);

                        Toast.makeText(mContext, "Đã xóa 1 cuốn sách ra khỏi thư viện!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
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

    public static class AllBookViewHolder extends RecyclerView.ViewHolder {
        private CardView layoutItems;
        private ImageView imgCover;
        private TextView tvName, tvAuthor;

        public AllBookViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutItems = itemView.findViewById(R.id.layoutItem);
            imgCover = itemView.findViewById(R.id.im_cover);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListBook = HomeFragment.listBook;
                } else {
                    List<Book> list = new ArrayList<>();
                    for (Book it : HomeFragment.listBook) {
                        if (it.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(it);
                        }
                    }

                    mListBook = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListBook;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListBook = (List<Book>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
