
package com.example.hp.universityset_notice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HP on 2018/5/28.
 */

public   class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private List<users>muserList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userimage;
        TextView userthings;
        View userView;

        public ViewHolder(View view){
            super(view);
            userimage = (ImageView)view.findViewById(R.id.user_image);
            userthings = (TextView)view.findViewById(R.id.user_things);
            userView = view;
        }
    }

    public userAdapter(List<users> usersList){
        muserList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        final  ViewHolder holder = new ViewHolder(view);
        holder.userthings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                users user = muserList.get(position);
              //  Toast.makeText(v.getContext(),"you clicked this ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.zhihu.com/question/28515516/answer/108661208\n"));
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        users user = muserList.get(position);
        holder.userimage.setImageResource(user.getImageId());
        holder.userthings.setText( user.getName()+"     向你提问了：  " + user.getThings());
    }

    @Override
    public int getItemCount() {
        return muserList.size();
    }
}
