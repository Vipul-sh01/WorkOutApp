package com.example.workouttimer.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workouttimer.R;
import com.example.workouttimer.data.local.models.ImageEntity;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final List<ImageEntity> images = new ArrayList<>();
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(ImageEntity imageEntity);
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageEntity> imageEntities) {
        images.clear();
        images.addAll(imageEntities);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageEntity entity = images.get(position);

        Glide.with(holder.imageView.getContext())
                .load(Uri.parse(entity.getUri()))
                .centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
        }
    }
}
