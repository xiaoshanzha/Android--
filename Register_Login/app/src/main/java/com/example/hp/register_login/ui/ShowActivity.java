package com.example.hp.register_login.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.register_login.R;
import com.example.hp.register_login.Utils.UploadFileTask;
import com.example.hp.register_login.bean.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private static final String TAG = "aa";
    EditText user_et;
    EditText time_et;
    EditText type_et;
    EditText laosao_et;
    List<String> img_path = new ArrayList<>();
    String itemFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Button ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_et = (EditText) findViewById(R.id.user_et);
                time_et = (EditText) findViewById(R.id.time_et);
                type_et = (EditText) findViewById(R.id.type_et);
                laosao_et = (EditText) findViewById(R.id.laosao_et);
                Show.user = user_et.getText().toString().trim();
                Show.time = time_et.getText().toString().trim();
                Show.type = type_et.getText().toString().trim();
                Show.laosao = laosao_et.getText().toString().trim();

                //设置获取的图片数量  并添加路径
                Show.img_num = 4;
                img_path.add("/storage/emulated/0/Pictures/ee.jpg");
                img_path.add("/storage/emulated/0/Pictures/bb.jpg");
                img_path.add("/storage/emulated/0/Pictures/cc.jpg");
                img_path.add("/storage/emulated/0/Pictures/dd.jpg");

                for(int i = 0;i < Show.img_num;i++){
                    itemFile = img_path.get(i);
                    if (itemFile != null && itemFile.length() > 0) {
                        UploadFileTask uploadFileTask = new UploadFileTask(ShowActivity.this);
                        uploadFileTask.execute(itemFile);
                    }
                }
            }
        });
    }
}
