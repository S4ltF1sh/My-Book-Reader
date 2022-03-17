package com.example.mybookreader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookreader.R;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.util.List;

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.BookShelfViewHolder> {

    private final Context mContext;
    private List<BookShelf> mListBookShelf;

    public BookShelfAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BookShelf> mListBookShelf) {
        this.mListBookShelf = mListBookShelf;
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
        BookShelf bookShelf = mListBookShelf.get(position);
        if (bookShelf == null)
            return;

        holder.tvName.setText(bookShelf.getName());

//        holder.tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mListBookShelf != null)
            return mListBookShelf.size();
        return 0;
    }

    public static class BookShelfViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public BookShelfViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_of_bookshelf);
        }
    }
}