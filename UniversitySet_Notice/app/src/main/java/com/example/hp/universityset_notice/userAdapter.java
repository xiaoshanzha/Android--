
package com.example.hp.universityset_notice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 2018/5/28.
 */

public   class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private List<users>muserList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userimage;
        TextView userthings;
        TextView username;

        public ViewHolder(View view){
            super(view);
            userimage = (ImageView)view.findViewById(R.id.user_image);
            userthings = (TextView)view.findViewById(R.id.user_things);
        }
    }

    public userAdapter(List<users> usersList){
        muserList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        users user = muserList.get(position);
        holder.userimage.setImageResource(user.getImageId());
        holder.userthings.setText(user.getName()+"     向你提问了：\n\t\t\t\t" + user.getThings());
    }

    @Override
    public int getItemCount() {
        return muserList.size();
    }
}
