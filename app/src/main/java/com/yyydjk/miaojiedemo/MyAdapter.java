package com.yyydjk.miaojiedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by dongjunkun on 2015/12/1.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private boolean isFirst = true;
    private List<Integer> walls;

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float item_normal_font_size;
    private float item_max_font_size;

    public MyAdapter(Context context, List<Integer> walls) {
        this.walls = walls;
        item_max_height = (int) context.getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) context.getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = context.getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = context.getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = context.getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = context.getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wall, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isFirst && position == 0) {
            isFirst = false;
            holder.mark.setAlpha(item_max_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_max_height));
        } else {
            holder.mark.setAlpha(item_normal_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_normal_height));
        }
        holder.text.setText(String.format("in the place %d", position));
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(holder.imageView.getContext()).load(walls.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return walls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View mark;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            mark = itemView.findViewById(R.id.mark);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
