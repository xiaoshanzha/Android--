package com.example.hp.register_login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.register_login.Utils.GetAllShow;
import com.example.hp.register_login.Utils.UploadFileTask;
import com.example.hp.register_login.bean.Dyn;
import com.example.hp.register_login.bean.User;
import com.example.hp.register_login.ui.CSVActivity;
import com.example.hp.register_login.ui.LoginActivity;
import com.example.hp.register_login.ui.RegisterActivity;
import com.example.hp.register_login.ui.ShowActivity;
import com.example.hp.register_login.ui.tempActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "";
    List<String> list = new ArrayList<String>();
    String itemFile = "";
    public static final String requestURL = "http://192.168.137.1:8080/twoweb_war_exploded/ImageUploadServlet";
    List<String> final_list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button button_l = (Button)findViewById(R.id.button_l);
        Button button_r = (Button)findViewById(R.id.button_r);
        Button delete = (Button)findViewById(R.id.delete);
        Button upto = (Button)findViewById(R.id.up);
        Button path = (Button)findViewById(R.id.path);
        Button show = (Button)findViewById(R.id.show);
        Button get = (Button)findViewById(R.id.get);

        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        button_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.islogin){
                    String s = "您已登陆账号 ：" + User.isId + "  , 请勿重复登陆。";
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CSVActivity.class);
                startActivity(intent);
                finish();
            }
        });

        upto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add("/storage/emulated/0/Pictures/aa.jpg");
                list.add("/storage/emulated/0/Pictures/bb.jpg");
                list.add("/storage/emulated/0/Pictures/cc.jpg");
                list.add("/storage/emulated/0/Pictures/dd.jpg");
                for (int i = 0; i < list.size(); i++) {
                    itemFile = list.get(i);
                    if (itemFile != null && itemFile.length() > 0) {
                        UploadFileTask uploadFileTask = new UploadFileTask(MainActivity.this);
                        uploadFileTask.execute(itemFile);
                    }
                }
            }
        });

        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, tempActivity.class);
                startActivity(intent);
                finish();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
                finish();
            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllShow getAllShow = new GetAllShow();
                List<Dyn> list = getAllShow.getall();
                for(int i = 0;i<list.size();i++){
                    Log.e("dyn",list.get(i).user );
                    Log.e("dyn",list.get(i).time );
                    Log.e("dyn",list.get(i).laosao );
                    Log.e("dyn",list.get(i).type );
                    for(int j = 0;j < list.get(i).img_num;j++){
                        Log.e("dyn",list.get(i).url.get(j) );
                    }
                }
            }
        });
    }
}
