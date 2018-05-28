package com.example.hp.androidthreadtest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    public MyService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed ");

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
       //前台服务通知
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is title")
                .setContentText("内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand excuted");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("MyService", "onDestroy excuted ");
        super.onDestroy();
    }

    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder{
        public  void startDownload(){
            Log.d("MyService","开始了");
        }
        public int  getProgress(){
            Log.d(TAG, "getProgress: 加载进度");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
     //   throw new UnsupportedOperationException("Not yet implemented");
        return  mBinder;
    }
}
