package com.example.hp.notice_column;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.Send);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Send:
                Intent intent = new Intent(this,oneActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("这是标题")
                        .setContentText("没错，这就是通知的内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.a)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.drawable.a))

                        .setAutoCancel(true)
                        .setLights(Color.BLUE,1000,1000)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(pi)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1000,notification);
                break;
            default:


                break;
        }

    }
}
