package com.example.kingsoft.v2ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kingsoft.v2ex.Model.Reply;
import com.example.kingsoft.v2ex.Model.Topic;
import com.example.kingsoft.v2ex.R;
import com.example.kingsoft.v2ex.adapter.RepliesRecycleViewAdapter;
import com.example.kingsoft.v2ex.http.HttpCallBack;
import com.example.kingsoft.v2ex.http.NodesHttpUtils;

import java.util.ArrayList;
import java.util.List;

import us.feras.mdv.MarkdownView;


/**
 * Created by kingsoft on 2017/8/14.
 */

public class BaseNodeActivity extends AppCompatActivity {
    public static final String TOPIC_ID = "topic_id";   //节点缩略名
    private NodesCallBack nodesCallBack;
    private RepliesCallBack repliesCallBack;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imageView;
    MarkdownView displayMarkdownView;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView repliesRecyclerView;
    private RepliesRecycleViewAdapter repliesRecycleViewAdapter;
    private List<Reply> mRepiles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basenode);
        Intent intent = getIntent();
        final String topicId = intent.getStringExtra(TOPIC_ID);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.basenode_swipeRefresh);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//            @Override
//            public void onRefresh() {
//                refreshFragment(topicId);
//            }
//        });




        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        imageView = (ImageView) findViewById(R.id.basenode_image_view);
        displayMarkdownView = (MarkdownView) findViewById(R.id.node_content);
        nodesCallBack = new NodesCallBack();
        new NodesHttpUtils().getTopicById(nodesCallBack, topicId);


        repliesCallBack = new RepliesCallBack();
        new NodesHttpUtils().getRepliesById(repliesCallBack, topicId);
        repliesRecyclerView = (RecyclerView) findViewById(R.id.rv_replies);
        repliesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repliesRecycleViewAdapter = new RepliesRecycleViewAdapter(mRepiles, this);
        repliesRecyclerView.setAdapter(repliesRecycleViewAdapter);
        repliesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    public void refreshFragment(String id) {
        nodesCallBack = new NodesCallBack();
        new NodesHttpUtils().getTopicById(nodesCallBack, id);
        swipeRefreshLayout.setRefreshing(false);
    }

    class RepliesCallBack implements HttpCallBack {
        @Override
        public void onSuccess(Object data) {
            mRepiles = (List<Reply>) data;
            repliesRecycleViewAdapter.setData(mRepiles);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    repliesRecycleViewAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFailure(String message) {
            Log.e("ERROR", message);
        }
    }



    class NodesCallBack implements HttpCallBack {
        @Override
        public void onSuccess(Object data) {
            List<Topic> arrayList = (List<Topic>) data;
            final Topic topic = arrayList.get(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Log.i("topic", topic.getTitle() + " " + topic.getContent()+"!!!!");
                    collapsingToolbarLayout.setTitle(topic.getTitle());
                    Glide.with(BaseNodeActivity.this).load("http://"+topic.getMember().getAvatar_large()).into(imageView);
                    displayMarkdownView.loadMarkdown(topic.getContent());
                }
            });
        }
        @Override
        public void onFailure(String message) {
            Log.i("ERROR:", message);
            Toast.makeText(BaseNodeActivity.this, "ERROR:" + message, Toast.LENGTH_SHORT).show();
        }
    }
}
