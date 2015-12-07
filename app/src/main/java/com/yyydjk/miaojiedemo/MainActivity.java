package com.yyydjk.miaojiedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float alpha_d;
    private float item_normal_font_size;
    private float item_max_font_size;
    private float font_size_d;

    private List<Integer> walls = Arrays.asList(R.mipmap.wall01,
            R.mipmap.wall02, R.mipmap.wall03, R.mipmap.wall04, R.mipmap.wall05,
            R.mipmap.wall06, R.mipmap.wall07, R.mipmap.wall08, R.mipmap.wall09,
            R.mipmap.wall10, R.mipmap.wall01, R.mipmap.wall02, R.mipmap.wall03,
            R.mipmap.wall04, R.mipmap.wall05, R.mipmap.wall06, R.mipmap.wall07,
            R.mipmap.wall08, R.mipmap.wall09, R.mipmap.wall10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item_max_height = (int) getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);

        font_size_d = item_max_font_size - item_normal_font_size;
        alpha_d = item_max_alpha - item_normal_alpha;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        MyAdapter myAdapter = new MyAdapter(this, walls);
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                MyAdapter.ViewHolder firstViewHolder = (MyAdapter.ViewHolder) recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
                MyAdapter.ViewHolder secondViewHolder = (MyAdapter.ViewHolder) recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
                MyAdapter.ViewHolder threeViewHolder = (MyAdapter.ViewHolder) recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
                if (firstViewHolder != null) {
                    if (firstViewHolder.itemView.getLayoutParams().height - dy > item_normal_height) {
                        firstViewHolder.itemView.getLayoutParams().height = firstViewHolder.itemView.getLayoutParams().height - dy;
                        firstViewHolder.mark.setAlpha(firstViewHolder.mark.getAlpha() - dy * alpha_d / item_normal_height);
                        firstViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                firstViewHolder.text.getTextSize() - dy * font_size_d / item_normal_height);
                    } else {
                        if (dy > 0) {
                            firstViewHolder.itemView.getLayoutParams().height = item_normal_height;
                            firstViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                            firstViewHolder.mark.setAlpha(item_normal_alpha);
                        } else {
                            firstViewHolder.mark.setAlpha(item_max_alpha);
                            firstViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
                            firstViewHolder.itemView.getLayoutParams().height = item_max_height;
                        }
                    }
                    firstViewHolder.itemView.setLayoutParams(firstViewHolder.itemView.getLayoutParams());
                }
                if (secondViewHolder != null) {
                    if (secondViewHolder.itemView.getLayoutParams().height + dy < item_max_height) {
                        secondViewHolder.itemView.getLayoutParams().height = secondViewHolder.itemView.getLayoutParams().height + dy;
                        secondViewHolder.mark.setAlpha(secondViewHolder.mark.getAlpha() + dy * alpha_d / item_normal_height);
                        secondViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                secondViewHolder.text.getTextSize() + dy * font_size_d / item_normal_height);
                    } else {
                        if (dy > 0) {
                            secondViewHolder.mark.setAlpha(item_normal_alpha);
                            secondViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                            secondViewHolder.itemView.getLayoutParams().height = item_normal_height;
                        } else {
                            secondViewHolder.itemView.getLayoutParams().height = item_max_height;
                            secondViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
                            secondViewHolder.mark.setAlpha(item_max_alpha);
                        }
                    }
                    secondViewHolder.itemView.setLayoutParams(secondViewHolder.itemView.getLayoutParams());
                }

                if (threeViewHolder != null) {
                    threeViewHolder.mark.setAlpha(item_normal_alpha);
                    threeViewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                    threeViewHolder.itemView.getLayoutParams().height = item_normal_height;
                    threeViewHolder.itemView.setLayoutParams(threeViewHolder.itemView.getLayoutParams());
                }
            }
        });

    }
}
