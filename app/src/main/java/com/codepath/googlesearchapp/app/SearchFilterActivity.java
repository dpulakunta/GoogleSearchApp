package com.codepath.googlesearchapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class SearchFilterActivity extends ActionBarActivity {
    String[] size = {"none","small","medium","large","extra-large"};
    String[] colorFilter = {"none","black","blue","brown","gray","green"};
    String[] type = {"none","faces","photo","clip-art","line-art"};
    Spinner sizeSpinner;
    Spinner colorSpinner;
    Spinner typeSpinner;
    EditText siteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,size);
        sizeSpinner = (Spinner)findViewById(R.id.spinnerSize);
        sizeSpinner.setAdapter(sizeAdapter);

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,colorFilter);
        colorSpinner = (Spinner)findViewById(R.id.spinnerColor);
        colorSpinner.setAdapter(colorAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,type);
        typeSpinner = (Spinner)findViewById(R.id.spinnerType);
        typeSpinner.setAdapter(typeAdapter);

        siteFilter = (EditText) findViewById(R.id.etSite);

    }

    public void saveContents(View v){
        String size = (String) sizeSpinner.getSelectedItem();
        String color = (String) colorSpinner.getSelectedItem();
        String type = (String) typeSpinner.getSelectedItem();
        String site = siteFilter.getText().toString();

        StringBuilder extraParams = new StringBuilder();
        //as_sitesearch=photobucket.com
        //imgcolor=black
        //imgsz=small|medium|large|xlarge
        //imgtype=face
        if(!size.equals("none"))
            extraParams.append("&imgsz="+size);
        if(!color.equals("none"))
            extraParams.append("&imgcolor="+color);
        if(!type.equals("none"))
            extraParams.append("&imgtype="+type);
        if(!site.equals(""))
            extraParams.append("&as_sitesearch="+site);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("ExtraQuery",extraParams.toString());
        setResult(RESULT_OK,returnIntent);
        Log.i("Search","need to play it"+extraParams.toString());
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_filter, menu);
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
