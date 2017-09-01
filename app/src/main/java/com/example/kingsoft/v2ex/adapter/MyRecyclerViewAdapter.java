package com.example.kingsoft.v2ex.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsoft.v2ex.Model.Topic;
import com.example.kingsoft.v2ex.R;
import com.example.kingsoft.v2ex.activity.BaseNodeActivity;

import java.util.List;

/**
 * Created by kingsoft on 2017/8/14.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Topic> mNodeList;

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView view;
        ImageView nameIcon;
        TextView topicTitle;
        TextView node;
        TextView nodeTip;
        TextView userName;
        TextView replies;
        public ViewHolder(View view) {
            super(view);
            this.view = (CardView) view;
            nameIcon = (ImageView) view.findViewById(R.id.iv_userIcon);
            userName = (TextView) view.findViewById(R.id.tv_username);
            node = (TextView) view.findViewById(R.id.tv_topic_node);
            nodeTip = (TextView) view.findViewById(R.id.tv_topic_tips);
            replies = (TextView) view.findViewById(R.id.tv_replies);
            topicTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public MyRecyclerViewAdapter(List<Topic> mNodeList, Context context) {
        this.mNodeList = mNodeList;
        this.context = context;
    }

    public void setData(List<Topic> mNodeList) {
        this.mNodeList = mNodeList;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Topic topic = mNodeList.get(position);
                Intent intent = new Intent(context, BaseNodeActivity.class);
                intent.putExtra(BaseNodeActivity.TOPIC_ID, topic.getId());
                context.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        Topic node = mNodeList.get(position);
        Glide.with(context).load("http:"+node.getAvatar()).centerCrop().into(holder.nameIcon);
        holder.topicTitle.setText(node.getTitle());
        holder.node.setText(node.getNode());
        holder.nodeTip.setText(node.getNodeTips());
        holder.userName.setText(node.getUsername());
        holder.replies.setText(node.getReplies());

    }

    @Override
    public int getItemCount() {
        return mNodeList.size();
    }




}
