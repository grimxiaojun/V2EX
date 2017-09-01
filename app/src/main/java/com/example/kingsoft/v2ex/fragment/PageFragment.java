package com.example.kingsoft.v2ex.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kingsoft.v2ex.Model.Topic;
import com.example.kingsoft.v2ex.R;
import com.example.kingsoft.v2ex.adapter.MyRecyclerViewAdapter;
import com.example.kingsoft.v2ex.http.HttpCallBack;
import com.example.kingsoft.v2ex.http.NodesHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/8/14.
 */

public class PageFragment extends Fragment {

    public MyRecyclerViewAdapter mAdapter;
    public List<Topic> nodesList = new ArrayList<>();
    public NodesCallBack nodesCallBack;
    public static PageFragment instance = null;
    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;

    public static PageFragment newInstance() {
        if (instance == null) {
            instance = new PageFragment();
        }
        return instance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodesCallBack = new NodesCallBack();
        new NodesHttpUtils().getLatestNodes(nodesCallBack);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshFragment();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new MyRecyclerViewAdapter(nodesList,view.getContext());
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    class NodesCallBack implements HttpCallBack {
        @Override
        public void onSuccess(Object data) {
            nodesList = (List<Topic>) data;
            mAdapter.setData(nodesList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        @Override
        public void onFailure(String message) {
        }
    }


    public void refreshFragment() {
        nodesCallBack = new NodesCallBack();
        new NodesHttpUtils().getLatestNodes(nodesCallBack);
        swipeRefreshLayout.setRefreshing(false);
    }





}

