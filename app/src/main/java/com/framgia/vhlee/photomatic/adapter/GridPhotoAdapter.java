package com.framgia.vhlee.photomatic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.vhlee.photomatic.R;

import java.util.ArrayList;
import java.util.List;

public class GridPhotoAdapter extends RecyclerView.Adapter<GridPhotoAdapter.ViewHolder> {
    private List<String> mPhotoUrls;
    private LayoutInflater mInflater;
    private PhotoClickListener mPhotoClickListener;

    public GridPhotoAdapter(Context context) {
        mPhotoUrls = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_photo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mPhotoUrls.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mPhotoUrls != null ? mPhotoUrls.size() : 0;
    }

    public void addPhoto(String url) {
        mPhotoUrls.add(url);
        notifyItemInserted(mPhotoUrls.size() - 1);
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        mPhotoClickListener = photoClickListener;
    }

    public String getPhotoSource(int position) {
        return mPhotoUrls.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.image_photo);
            mPhoto.setOnClickListener(this);
        }

        public void bindData(String url, int position) {
            Glide.with(itemView.getContext()).load(url).into(mPhoto);
        }

        @Override
        public void onClick(View view) {
            mPhotoClickListener.onPhotoClick(getAdapterPosition());
        }
    }

    public interface PhotoClickListener {
        void onPhotoClick(int position);
    }
}
