package com.example.hp.metworktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    private String responseData;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {


            // sendRequestWithOkhttp();


            //以下发送HTTP请求的方法是  将其提取到一个公共的类中，用时调用即可。不用在每个活动中去编写发送请求
            HttpUtil.sendOkHttpRequest("http://10.0.2.2:8008/get_data.json" ,new okhttp3.Callback() {

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                }

                @Override
                public void onFailure(Call call, IOException e) {
                }
            });



        }
    }

    //发送HTTP请求
    private void sendRequestWithOkhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8008/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                  //  showResponse(responseData);
                   // parseJSONWithJSONObject(responseData);
                    parseJSONWithGSON(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //使用JSONObject解析JSON格式数据
    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id =jsonObject.getString("id");
                String name =jsonObject.getString("name");
                String version =jsonObject.getString("version");
                Log.d("MainActivity","id is" + id);
                Log.d("MainActivity","name is" + name);
                Log.d("MainActivity","version is" + version);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //使用GSON解析JSON格式数据
    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for(App app : appList){
            Log.d("MainActivity","id is "+app.getId());
            Log.d("MainActivity","name is "+app.getName());
            Log.d("MainActivity","version is "+app.getVersion());
        }
    }

    private  void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
