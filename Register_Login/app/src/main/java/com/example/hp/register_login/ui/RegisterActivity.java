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

public class RegisterActivity extends AppCompatActivity {

    public static final int SHOW_RESPONSE = 1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    if(response.equals("注册成功！ ")){
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    EditText r_id_text ;
    EditText r_pw_text ;
    Button register ;
    String r_id_string;
    String r_pw_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_id_text = (EditText)findViewById(R.id.r_id);
                r_pw_text = (EditText)findViewById(R.id.r_pw);
                r_id_string = r_id_text.getText().toString().trim();
                r_pw_string = r_pw_text.getText().toString().trim();
                sendRequestWithHttpURLConnecyion(r_id_string,r_pw_string);
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
                    URL url = new URL("http://101.37.79.26:8080/web/adduserServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("id="+id+"&password="+pw);
                    out.flush();
                    out.close();

                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    //将 StringBuilder转为String
                    String r = response.toString();
                    Message message=new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = r;
                    handler.sendMessage(message);
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
}
