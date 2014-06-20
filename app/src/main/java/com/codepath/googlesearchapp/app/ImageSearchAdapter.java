package com.codepath.googlesearchapp.app;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by dharm on 6/19/14.
 */
public class ImageSearchAdapter extends ArrayAdapter<ImageSearch> {

    public ImageSearchAdapter(Context context, List<ImageSearch> image) {
        super(context,android.R.layout.simple_list_item_1 ,image);
    }
}
