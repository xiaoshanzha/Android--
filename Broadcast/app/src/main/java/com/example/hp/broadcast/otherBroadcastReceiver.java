package com.example.hp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by HP on 2018/5/24.
 */

public    class otherBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"成功接收",Toast.LENGTH_SHORT).show();

        abortBroadcast();//将这条广播截断，优先级低的广播接收器无法接收这条广播。
                         //androidmainfest中<intent-filter android:priority="100">设置了优先级，大的优先级高
    }
}
