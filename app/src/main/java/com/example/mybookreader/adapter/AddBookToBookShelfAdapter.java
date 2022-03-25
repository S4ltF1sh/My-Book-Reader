package com.example.mybookreader.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookreader.activities.AddBookToBookShelfActivity;
import com.example.mybookreader.database.BookshelfDatabase;
import com.example.mybookreader.fragments.BookshelfFragment;
import com.example.mybookreader.R;
import com.example.mybookreader.fragments.HomeFragment;
import com.example.mybookreader.model.Book;
import com.example.mybookreader.model.BookShelf;

import java.util.List;

public class AddBookToBookShelfAdapter extends RecyclerView.Adapter<AddBookToBookShelfAdapter.AddBookToBookShelfViewHolder> {

    private final Context mContext;

    public AddBookToBookShelfAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddBookToBookShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_shelf_view2, parent, false);
        return new AddBookToBookShelfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddBookToBookShelfViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookShelf bookShelf = BookshelfFragment.mListBookShelf.get(position);
        if (bookShelf == null)
            return;

        holder.tvName.setText(bookShelf.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddBookToBookShelf(position);
            }
        });
    }

    private void onClickAddBookToBookShelf(int position) {
        List<Book> tmpBookshelf = BookshelfFragment.mListBookShelf.get(position).getListBook();
        tmpBookshelf.add(HomeFragment.listBook.get(AddBookToBookShelfActivity.positionOfBookNeedToAddToBookshelf));
        BookshelfFragment.mListBookShelf.get(position).setListBook(tmpBookshelf);

        BookshelfDatabase.getInstance(mContext).bookshelfDAO().updateBookshelf(BookshelfFragment.mListBookShelf.get(position));
        ((Activity) mContext).finish();
    }

    @Override
    public int getItemCount() {
        if (BookshelfFragment.mListBookShelf != null)
            return BookshelfFragment.mListBookShelf.size();
        return 0;
    }

    public static class AddBookToBookShelfViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RelativeLayout layout;

        public AddBookToBookShelfViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_of_bookshelf2);
            layout = itemView.findViewById(R.id.layoutBookShelfView2);
        }
    }
}
