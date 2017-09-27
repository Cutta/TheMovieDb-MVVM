package com.aac.andcun.themoviedb_mvvm.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.bumptech.glide.Glide;

/**
 * Created by andani on 25.07.2017.
 */

public class BindingAdapters {

    @BindingAdapter("app:posterPath")
    public static void loadImage(ImageView imageView, String posterPath) {
        Glide.with(imageView.getContext()).load(ApiConstants.IMAGE_PREFIX + posterPath).centerCrop().into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}