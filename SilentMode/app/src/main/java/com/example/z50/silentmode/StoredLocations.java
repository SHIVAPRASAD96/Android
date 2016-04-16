package com.example.z50.silentmode;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Z50 on 15-08-2015.
 */
public class StoredLocations extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.storedlocations);
        populateListView();

    }
    private void populateListView() {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(){super(StoredLocations.this,R.layout.labels,StorageHelper.getAllKeys(StoredLocations.this));}

        public View getView(int position,View convertView, ViewGroup parent){

            View lables = convertView;
            if (lables == null) {
                lables = getLayoutInflater().inflate(R.layout.labels, parent, false);
            }


            TextView name = (TextView) lables.findViewById(R.id.lbl_places);
            name.setText(StorageHelper.getAllKeys(StoredLocations.this).get(position));
            return lables;
        }
    }
}
