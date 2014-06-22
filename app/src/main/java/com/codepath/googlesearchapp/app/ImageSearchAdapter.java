package com.codepath.googlesearchapp.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by dharm on 6/19/14.
 */
public class ImageSearchAdapter extends ArrayAdapter<ImageSearch> {

    public ImageSearchAdapter(Context context, List<ImageSearch> image) {
        super(context,R.layout.image_search_layout,image);
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        ImageSearch imageInfo = this.getItem(position);
        SmartImageView ivImage;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ivImage = (SmartImageView) inflater.inflate(R.layout.image_search_layout,parent,false);
        } else{
            ivImage = (SmartImageView) convertView;
            ivImage.setImageResource(android.R.color.transparent);
        }
        ivImage.setImageUrl(imageInfo.getTbUrl());

        return ivImage;
    }
}
