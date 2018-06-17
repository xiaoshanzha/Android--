package com.example.hp.universityset_notice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
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
                        parseJSONWithJSONObject(responseData);
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


                if(i==0||i ==7)
                {
                    users shanzha=  new users(R.drawable.one,name,question);
                    usersList.add(shanzha);
                    shanzha = null;
                }
                else if(i==1||i==8)
                {
                    users shanzha=  new users(R.drawable.two,name,question);
                    usersList.add(shanzha);
                    shanzha = null;
                }
                else if(i==2||i==9)
                {users shanzha=  new users(R.drawable.a,name,question);
                    usersList.add(shanzha);
                    shanzha = null;}
                else if(i==3||i==10)
                {
                    users shanzha=  new users(R.drawable.b,name,question);
                    usersList.add(shanzha);
                    shanzha = null;}
                else if(i==4||i==11)
                {
                    users shanzha=  new users(R.drawable.c,name,question);
                    usersList.add(shanzha);
                    shanzha = null;}
                else if(i==5||i==12)
                {
                    users shanzha=  new users(R.drawable.d,name,question);
                    usersList.add(shanzha);
                    shanzha = null;}
                else if(i==6||i==13)
                {
                    users shanzha=  new users(R.drawable.e,name,question);
                    usersList.add(shanzha);
                    shanzha = null;}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

