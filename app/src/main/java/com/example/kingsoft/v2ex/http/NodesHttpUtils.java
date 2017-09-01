package com.example.kingsoft.v2ex.http;

import android.content.Context;

import com.example.kingsoft.v2ex.Model.Member;
import com.example.kingsoft.v2ex.Model.Reply;
import com.example.kingsoft.v2ex.Model.Topic;
import com.example.kingsoft.v2ex.Constants.ServerUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by kingsoft on 2017/8/14.
 */

public class NodesHttpUtils {





    private static final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .build();







    public void getLatestNodes(final HttpCallBack callBack) {
        Type t = new TypeToken<List<Topic>>(){}.getType();
        getData(ServerUrl.LATEST_TOPICS, callBack, t);
    }

    public void getHotNodes(final HttpCallBack callBack) {
        Type t = new TypeToken<List<Topic>>(){}.getType();
        getData(ServerUrl.HOT_TOPICS, callBack, t);
    }

    public void getTopicById(final HttpCallBack callBack, String id) {
        Type t = new TypeToken<List<Topic>>(){}.getType();
        getData(ServerUrl.TOPIC_SHOW_BY_NAME+id, callBack, t);
    }

    public void getRepliesById(final HttpCallBack callBack, String id) {
        Type t = new TypeToken<List<Reply>>(){}.getType();
        getRepliesData(ServerUrl.REPLIES_BY_ID+id, callBack, t);
    }



    public void getNodesByTab(final HttpCallBack callBack, Context context, String tab) {
        getTopics(callBack,context,tab);
    }



    public void getTopics(final HttpCallBack callBack, Context context, String tab) {
        //

        final Request request = new Request.Builder()
                .get()
                .tag(context)
                .url("http://www.v2ex.com/?tab="+tab)
                .build();
            //开启线程，访问网络数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;

                try {
                    ArrayList<Topic> arrayListTopics = new ArrayList<Topic>();
                    //client.setHostnameVerifier();
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Document document = Jsoup.parse(json);
                        Elements elements = document.getElementById("Main")
                                .getElementsByClass("box").get(0)
                                .getElementsByClass("item");
                        for (int i = 0; i < elements.size(); i++) {

                            Topic topics = new Topic();
                            Elements elementsA = elements.get(i).getElementsByTag("a");
                            topics.setTitle(elementsA.get(1).text());
                            String id = elementsA.get(1).attr("href");
                            int index = id.indexOf('#');
                            id = id.substring(3, index);
                            topics.setId(id);

                            topics.setUrl("http://www.v2ex.com"
                                    + elementsA
                                    .get(1)
                                    .attr("href")
                                    .substring(
                                            0,
                                            elementsA.get(1).attr("href")
                                                    .indexOf("#")));
                            topics.setAvatar(elements.get(i).getElementsByTag("img").get(0)
                                    .attr("src"));
                            topics.setNode(elementsA.get(2).text());
                            topics.setUsername(elementsA.get(3).text());
                            if (elementsA.size() != 4) {
                                topics.setReplies(elementsA.get(5).text());
                            } else {
                                topics.setReplies("0");
                            }
                            String small = elements.get(i).getElementsByClass("small").text();
                            index = small.indexOf("•");
                            small = small.substring(index+1);
                            index = small.indexOf("•");
                            if (index != -1) {
                                small = small.substring(index+1).trim();
                            }else{
                                small = "";
                            }
                            topics.setNodeTips(small);
                            arrayListTopics.add(topics);
                        }
                        callBack.onSuccess(arrayListTopics);
                    }else {
                        String message = "Unexpected code " + response;

                        callBack.onFailure(message);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }



    public void getRepliesData(String url, final HttpCallBack callBack, final Type type) {
        //创建请求对象

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();
        //开启线程，访问网络数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        List<Topic> data = gson.fromJson(json, type);
//                        List<Topic> data = parseJSONWithJSONObject(json);
                        callBack.onSuccess(data);

                    } else {
                        String message = "Unexpected code " + response;

                        callBack.onFailure(message);
                    }


                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }










    public void getData(String url, final HttpCallBack callBack, final Type type) {
        //创建请求对象

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();
        //开启线程，访问网络数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {

                    response = client.newCall(request).execute();


                    if (response.isSuccessful()) {

                        String json = response.body().string();

//                        Gson gson = new Gson();
//                        List<Topic> data = gson.fromJson(json, type);
                        List<Topic> data = parseJSONWithJSONObject(json);

                        callBack.onSuccess(data);

                    } else {
                        String message = "Unexpected code " + response;

                        callBack.onFailure(message);
                    }


                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }







    private ArrayList<Topic> parseJSONWithJSONObject(String jsonData) {
        ArrayList<Topic> arrayList = new ArrayList<>();
        Topic topic = new Topic();
        Member member = new Member();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i < jsonArray.length(); ++i) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                topic.setTitle(jsonObject.getString("title"));
                topic.setContent(jsonObject.getString("content"));
                topic.setContent_rendered(jsonObject.getString("content_rendered"));
                topic.setReplies(String.valueOf(jsonObject.getInt("replies")));
                JSONObject mem = (jsonObject.getJSONObject("member"));
                member.setId(mem.getString("id"));
                member.setAvatar_large(mem.getString("avatar_large"));
                topic.setMember(member);
                arrayList.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  arrayList;
    }


}
