package com.example.kingsoft.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsoft.v2ex.Model.Member;
import com.example.kingsoft.v2ex.Model.Reply;
import com.example.kingsoft.v2ex.R;

import java.util.List;

/**
 * Created by kingsoft on 2017/8/17.
 */

public class RepliesRecycleViewAdapter extends RecyclerView.Adapter<RepliesRecycleViewAdapter.ViewHolder> {

    private List<Reply> mRepiles;

    private Context context;


    public RepliesRecycleViewAdapter(List<Reply> mRepiles, Context context) {
        this.mRepiles = mRepiles;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView nameIcon;
        TextView repliesContent;
        TextView nodeTip;
        TextView userName;
        TextView thanks;
        public ViewHolder(View view) {
            super(view);
            nameIcon = (ImageView) view.findViewById(R.id.iv_userIcon);
            userName = (TextView) view.findViewById(R.id.tv_username);
            nodeTip = (TextView) view.findViewById(R.id.tv_topic_tips);
            thanks = (TextView) view.findViewById(R.id.tv_loves);
            repliesContent = (TextView) view.findViewById(R.id.tv_replies_content);
        }
    }

    public void setData(List<Reply> data) {
        this.mRepiles = data;
    }



    @Override
    public RepliesRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.replies_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepliesRecycleViewAdapter.ViewHolder holder, int position) {
        Reply reply = mRepiles.get(position);
        Member member = reply.getMember();
        Glide.with(context).load("http:" + member.getAvatar_large()).into(holder.nameIcon);
        holder.userName.setText(member.getUsername());
        holder.nodeTip.setText("time");
        holder.thanks.setText(reply.getThanks());
        holder.repliesContent.setText(reply.getContent());
    }


    @Override
    public int getItemCount() {
        return mRepiles.size();
    }
}
