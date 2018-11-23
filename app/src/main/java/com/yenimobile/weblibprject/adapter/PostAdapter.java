package com.yenimobile.weblibprject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yenimobile.weblibprject.R;
import com.yenimobile.weblibprject.model.TumPost;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mContext;
    private ArrayList<TumPost> mPostLists;
    RequestOptions options ;


    public PostAdapter(Context context, ArrayList<TumPost> postsList){
        mContext = context;
        mPostLists = postsList;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.post_list_item_layout, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        TumPost currentPost = mPostLists.get(position);
        holder.postNameTV.setText(currentPost.getTumPostUserName());
        Glide.with(mContext)
                .load(currentPost.getTumPostImageUrl())
                .into(holder.postIV);

    }

    @Override
    public int getItemCount() {
        return mPostLists.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView postIV;
        private TextView postNameTV;

        public PostViewHolder(View itemView) {
            super(itemView);
            postIV = itemView.findViewById(R.id.postIV);
            postNameTV = itemView.findViewById(R.id.postNameTV);
        }
    }
}