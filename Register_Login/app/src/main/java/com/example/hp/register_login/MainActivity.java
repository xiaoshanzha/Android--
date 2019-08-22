package com.example.hp.register_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.register_login.bean.User;
import com.example.hp.register_login.ui.LoginActivity;
import com.example.hp.register_login.ui.RegisterActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_l = (Button)findViewById(R.id.button_l);
        Button button_r = (Button)findViewById(R.id.button_r);
        Button delete = (Button)findViewById(R.id.delete);


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
                User.islogin = false;
                User.isId = null;
                User.isPw = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
