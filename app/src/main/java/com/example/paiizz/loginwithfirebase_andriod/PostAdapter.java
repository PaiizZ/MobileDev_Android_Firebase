package com.example.paiizz.loginwithfirebase_andriod;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView postTitle;
        public ImageView postImg;
        public ViewHolder(View itemView) {
            super(itemView);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postImg = (ImageView) itemView.findViewById(R.id.postImg);
        }

        public void loadImage(String url) {
            Glide.with(this.itemView.getContext()).load(url).transition(withCrossFade()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(this.postImg);
        }
    }

    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(context)
                .inflate(R.layout.view_post, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        holder.postTitle.setText(posts.get(position).getImageTitle());
        holder.loadImage(posts.get(position).getImageURL());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
