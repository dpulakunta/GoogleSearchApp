package com.codepath.googlesearchapp.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    String extraQuery = new String();
    String searchQuery;

    public static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupWidgets();
        imgSearchAdapter = new ImageSearchAdapter(this, imageResult);
        gvSearchItem.setAdapter(imgSearchAdapter);
        gvSearchItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View parent, int position, long l) {
                Intent imageDisplayIntent = new Intent(getApplicationContext(),ImageDisplayActivity.class);
                ImageSearch imageRes = imageResult.get(position);
                imageDisplayIntent.putExtra("ImageURL",imageRes);
                startActivity(imageDisplayIntent);
            }
        });
        gvSearchItem.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        offset = offset*8;
        addItemsToView(offset);


    }

    public void changeToFilter(View v){
        Intent filterIntent = new Intent(getApplicationContext(),SearchFilterActivity.class);
        startActivityForResult(filterIntent, 1);
    }

    public void setupWidgets(){
        searchString = (TextView) findViewById(R.id.txtSearch);
        searchButton = (Button) findViewById(R.id.btnSearch);
        gvSearchItem = (GridView) findViewById(R.id.gridView);
    }
    public void searchForTheString(View view){

        searchQuery = searchString.getText().toString();
        Toast.makeText(this,"Searching for "+searchQuery ,Toast.LENGTH_SHORT).show();
        imageResult.clear();
        addItemsToView(0);
    }
    public void addItemsToView(int offset){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = buildQueryString(offset);
        Log.i("Search",url);
        client.get(this,url,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResult.clear();
                    imgSearchAdapter.addAll(ImageSearch.fromJSONArray(imageJsonResults));
                    Log.d("DEBUG",imageResult.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private String buildQueryString(int offset) {
        StringBuilder res = new StringBuilder();
        res.append("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&");
        res.append("start=");
        res.append(offset);
        res.append("&v=1.0&q=");
        res.append(Uri.encode(searchQuery));
        if(extraQuery!=null)
            res.append(extraQuery);
        return res.toString();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                extraQuery=data.getStringExtra("ExtraQuery");
                Log.i("Search",extraQuery);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                extraQuery="";
                Log.i("Search",extraQuery);
            }
        }
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
