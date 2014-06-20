package com.codepath.googlesearchapp.app;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    TextView searchString;
    Button searchButton;
    GridView gvSearchItem;
    ArrayList<ImageSearch> imageResult = new ArrayList<ImageSearch>();
    ImageSearchAdapter imgSearchAdapter;

    public static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupWidgets();
        imgSearchAdapter = new ImageSearchAdapter(this, imageResult);
        gvSearchItem.setAdapter(imgSearchAdapter);
    }

    public void setupWidgets(){
        searchString = (TextView) findViewById(R.id.txtSearch);
        searchButton = (Button) findViewById(R.id.btnSearch);
        gvSearchItem = (GridView) findViewById(R.id.gridView);
    }
    public void searchForTheString(View view){
        String searchQuery = searchString.getText().toString();
        Toast.makeText(this,"Searching for "+searchQuery ,Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this,"https://ajax.googleapis.com/ajax/services/search/images?szx=8&"+
                "start=" + 0 + "&v=1.0&q=" + Uri.encode(searchQuery),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    imageResult.clear();
                    imgSearchAdapter.addAll(ImageSearch.fromJSONArray(imageJsonResults));
                    Log.d("DEBUG",imageResult.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
