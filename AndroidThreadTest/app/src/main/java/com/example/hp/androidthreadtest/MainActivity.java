package com.example.hp.androidthreadtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private MyService.DownloadBinder downloadBinder;


    //活动与服务进行通信时创建的匿名类
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //异步消息处理
    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){
            switch (msg.what){
                case 1 :
                    textView.setText("你好");
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button change = (Button)findViewById(R.id.change_text);
        Button startService = (Button)findViewById(R.id.start_service);
        Button stopService = (Button)findViewById(R.id.stop_service);
        Button bind = (Button)findViewById(R.id.bind);
        Button unbind = (Button)findViewById(R.id.unbind);
        Button intentservice = (Button)findViewById(R.id.intentservice);
        textView = (TextView)findViewById(R.id.text);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        change.setOnClickListener(this);
        intentservice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(connection);
                break;
            case R.id.intentservice:
                Intent intent = new Intent(this,MyIntentService.class);
                startService(intent);
                Log.d("MainActivity", "主Thread id is "+Thread.currentThread().getId());
            default:
                break;
        }
    }
}
