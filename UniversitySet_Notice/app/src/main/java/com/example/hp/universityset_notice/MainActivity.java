package com.example.hp.universityset_notice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<users> usersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // initusers();
        sendRequestWithOkhttp();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        userAdapter adapter = new userAdapter(usersList);
        recyclerView.setAdapter(adapter);
    }


        private void sendRequestWithOkhttp(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url("http://10.0.2.2:8008/get_data3.json").build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //  showResponse(responseData);
                        parseJSONWithJSONObject(responseData);
                        //  parseJSONWithGSON(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private users shanzha;
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String question = jsonObject.getString("question");

                Log.d("MainActivity", "name is "+ name);
                Log.d("MainActivity", "question is "+ question);
                users shanzha=  new users(R.drawable.two,name,question);
                usersList.add(shanzha);
                shanzha = null;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

