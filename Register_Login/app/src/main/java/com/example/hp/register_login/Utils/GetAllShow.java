package com.example.hp.register_login.Utils;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.register_login.MainActivity;
import com.example.hp.register_login.bean.Dyn;
import com.example.hp.register_login.ui.RegisterActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetAllShow  {
    // 实例化List对象
   public  List<Dyn> list = new ArrayList<Dyn>();
   public boolean flag = false;
   public List<Dyn> getall(){
     new Thread(new Runnable() {
           @Override
           public void run() {
               HttpURLConnection connection = null;
               BufferedReader reader = null;
               try {
                   URL url = new URL("http://101.37.79.26:8080/show/GetAllServlet");
                   connection = (HttpURLConnection)url.openConnection();
                   connection.setRequestMethod("POST");


                   //设置连接超时和读取超时的毫秒数
                   connection.setConnectTimeout(8000);
                   connection.setReadTimeout(8000);

                   InputStream in = connection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(in));
                   String line;
                   while ((line = reader.readLine())!=null) {
                       Dyn dyn = new Dyn();
                       dyn.setUser(line);
                       dyn.setTime(reader.readLine());
                       dyn.setLaosao(reader.readLine());
                       dyn.setType(reader.readLine());
                       int num = reader.readLine().charAt(0) - '0';
                       dyn.setImg_num(num);
                       for (int i = 0; i < num; i++) {
                           dyn.url.add(reader.readLine());
                       }
                       list.add(dyn);
                   }
                   flag = true;
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
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
       while (true){
             if(flag){
                 return list;
             }
       }
   }
}
