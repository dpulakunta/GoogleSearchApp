package com.codepath.googlesearchapp.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dharm on 6/18/14.
 */
public class ImageSearch implements Serializable{
    private static final long serialVersionUID = 2636389073439281291L;
    private String url;
    private String tbUrl;
    ImageSearch(JSONObject json){
        try {
            this.url = json.getString("url");
            this.tbUrl = json.getString("tbUrl");
        } catch (JSONException e) {
            this.url = null;
            this.tbUrl = null;
        }

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }


    public static ArrayList<ImageSearch> fromJSONArray(JSONArray imageJsonResults) {
        ArrayList<ImageSearch> resultsFromJSON = new ArrayList<ImageSearch>();
        for(int i=0; i<imageJsonResults.length();i++){
            try {
                resultsFromJSON.add(new ImageSearch(imageJsonResults.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultsFromJSON;
    }

    @Override
    public String toString() {
        return this.tbUrl;
    }

}
