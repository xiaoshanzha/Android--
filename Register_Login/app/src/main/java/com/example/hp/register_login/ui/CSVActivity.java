package com.example.hp.register_login.ui;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hp.register_login.Adapter.ItemArrayAdapter;
import com.example.hp.register_login.R;
import com.example.hp.register_login.Utils.CSVFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CSVActivity extends AppCompatActivity {

    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.wheat);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> scoreList =  csvFile.read();

        int i = 0;
        for(String[] scoreData:scoreList ) {
            Log.e( "CSV1: ", scoreData[0]);
            Log.e( "CSV1: ", String.valueOf(i++));
            itemArrayAdapter.add(scoreData);
        }
    }
}
