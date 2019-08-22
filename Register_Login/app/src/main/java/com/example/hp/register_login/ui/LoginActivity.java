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
import com.example.hp.register_login.bean.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    public static final int SHOW_RESPONSE = 1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    if(response.equals("true")){
                        User.islogin = true;
                        User.isId = id;
                        User.isPw = password;
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private EditText mTextId;
    private EditText mTextPassword;
    private String id;
    private String password;
    private Button mButtonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mTextId = findViewById(R.id.l_id);
        mTextPassword = findViewById(R.id.l_pw);
        mButtonLogin = findViewById(R.id.login);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mTextId.getText().toString().trim();
                password = mTextPassword.getText().toString().trim();
                sendRequestWithHttpURLConnecyion(id,password);
            }
        });
    }
    /*
    *使用HttpURLConnection访问网络：
    * 1.获取HttpURLConnection实例
    * 2.设置http请求的方法：GRT:获取数据, POST:提交数据
    * 3.DataOutStream输出流提交数据，InputStream输入流读取数据
    * 4.关闭HTTP连接
    * */
    private void sendRequestWithHttpURLConnecyion(final String id, final String pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://101.37.79.26:8080/twoweb/SearchServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("ID="+id+"&PW="+pw);
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

