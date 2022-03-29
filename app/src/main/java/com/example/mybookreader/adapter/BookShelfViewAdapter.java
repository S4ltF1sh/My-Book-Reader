package com.example.mybookreader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookreader.activities.OpenedBookshelfActivity;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.model.BookShelf;

import java.util.List;

public class BookShelfViewAdapter extends RecyclerView.Adapter<BookShelfViewAdapter.BookShelfViewHolder> {

    private final Context mContext;
    //private List<BookShelf> mListBookShelf;

    public BookShelfViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BookShelf> mListBookShelf) {
        //this.mListBookShelf = mListBookShelf;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_shelf_view, parent, false);
        return new BookShelfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookShelfViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookShelf bookShelf = BookshelfFragment.mListBookShelf.get(position);
        if (bookShelf == null)
            return;

        holder.tvName.setText(bookShelf.getName());
        String numberOfBooks = "Trống";
        if (bookShelf.getListBook().size() > 0) {
            numberOfBooks = String.valueOf(bookShelf.getListBook().size()) + " cuốn";
        }
        holder.tvNumberOfBooks.setText(numberOfBooks);

        holder.layoutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOpenBookShelf(position);
            }
        });

        holder.layoutItems.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_for_bookshelfs_in_bookshelf_view, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.remove_for_bookshelfs_in_bookshelf_view:
                                removeItem(position);
                                break;
                        }
                        return false;
                    }
                });
                return false;
            }
        });
    }

    private void removeItem(int position) {
        BookshelfDatabase.getInstance(mContext).bookshelfDAO().deleteBookshelf(BookshelfFragment.mListBookShelf.get(position));
        BookshelfFragment.mListBookShelf = BookshelfDatabase.getInstance(mContext).bookshelfDAO().getListBookshelf();
        notifyDataSetChanged();

        Toast.makeText(mContext, "Đã xóa 1 giá sách khỏi thư viện!", Toast.LENGTH_SHORT).show();
    }

    private void onClickOpenBookShelf(int position) {
        Intent intent = new Intent(mContext, OpenedBookshelfActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("position_of_bookshelf", position);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (BookshelfFragment.mListBookShelf != null)
            return BookshelfFragment.mListBookShelf.size();
        return 0;
    }

    public static class BookShelfViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvNumberOfBooks;
        private ConstraintLayout layoutItems;

        public BookShelfViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_of_bookshelf);
            tvNumberOfBooks = itemView.findViewById(R.id.tv_number_of_books);
            layoutItems = itemView.findViewById(R.id.layoutBookShelfView);
        }
    }
}
