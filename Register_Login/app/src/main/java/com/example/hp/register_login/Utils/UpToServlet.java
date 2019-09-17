package com.example.hp.register_login.Utils;

import android.os.Message;
import android.util.Log;

import com.example.hp.register_login.bean.Show;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.support.constraint.Constraints.TAG;

public class UpToServlet {
    public void  sendRequestWithHttpURLConnecyion(){
        for(int i=Show.img_num;i<9;i++){
            Show.url.add("");
        }
        final String[] text = new String[5];
        final String[] img_url = new String[10];
        text[0] = Show.user;
        text[1] = Show.time;
        text[2] = Show.laosao;
        text[3] = Show.type;
        try {
            text[0] = URLEncoder.encode(text[0],"utf-8");
            text[1] = URLEncoder.encode(text[1],"utf-8");
            text[2] = URLEncoder.encode(text[2],"utf-8");
            text[3] = URLEncoder.encode(text[3],"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i=0;i<9;i++){
            img_url[i] = Show.url.get(i);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://101.37.79.26:8080/show/addShowServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("contentType", "GBK");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                    out.writeBytes("user="+ text[0] +"&time="+text[1] +"&laosao="+text[2] +"&type="+text[3]
                            +"&img_0="+img_url[0]+"&img_1="+img_url[1]+"&img_2="+img_url[2]+"&img_3="+img_url[3]
                            +"&img_4="+img_url[4]+ "&img_5=" +img_url[5]+"&img_6="+img_url[6]+"&img_7="+img_url[7]
                            +"&img_8="+img_url[8]);

                    out.flush();
                    out.close();

                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);


                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in,"GBK"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    //将 StringBuilder转为String
                    String r = response.toString();
                    Log.e(TAG,r );
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
    };
}
