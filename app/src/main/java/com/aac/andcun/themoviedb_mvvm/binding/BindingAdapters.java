package com.aac.andcun.themoviedb_mvvm.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.bumptech.glide.Glide;

/**
 * Created by andani on 25.07.2017.
 */

public class BindingAdapters {

    @BindingAdapter("app:backdropPath")
    public static void loadImage(ImageView imageView, String backdropPath) {
        Glide.with(imageView.getContext()).load(ApiConstants.IMAGE_PREFIX + backdropPath).centerCrop().into(imageView);
    }

}