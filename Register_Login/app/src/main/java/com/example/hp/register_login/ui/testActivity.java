package com.example.hp.register_login.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.register_login.MainActivity;
import com.example.hp.register_login.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class testActivity extends AppCompatActivity {

    private static final int SHOW_RESPONSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button button = (Button)findViewById(R.id.Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = (EditText)findViewById(R.id.username);
                EditText pw = (EditText)findViewById(R.id.password);
                String id_text = id.getText().toString().trim();
                String pw_text = pw.getText().toString().trim();
                sendRequestWithHttpURLConnecyion(id_text,pw_text);
            }
        });
    }

    private void sendRequestWithHttpURLConnecyion(final String id, final String pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://192.168.137.1:8080/twoweb/SearchServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("ID="+id+"&PW="+pw);
                    out.flush();
                    out.close();

                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    Message message=new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj=response;
                    handler1.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public Handler handler1=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    if(response.equals("true")){
                        Intent intent = new Intent(testActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(testActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
