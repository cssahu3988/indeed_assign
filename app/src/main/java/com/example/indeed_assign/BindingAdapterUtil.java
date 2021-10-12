package com.example.indeed_assign;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class BindingAdapterUtil {
    @BindingAdapter("url")
    public static void setUrl(View view, String url){
        ImageView imageView = (ImageView) view;
        Picasso.get().load(url).networkPolicy(NetworkPolicy.OFFLINE).fit().into(imageView);
    }
}
