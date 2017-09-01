package com.example.kingsoft.v2ex.Model;

import com.example.kingsoft.v2ex.Model.Member;

/**
 * Created by kingsoft on 2017/8/14.
 */

public class Topic {
    private String id;
    private String title;
    private String url;
    private String avatar;
    private String username;
    private String content;
    private String content_rendered;
    private String replies;
    private Member member;
    private String node;
    private String nodeTips;
    private String click;

    public String getNodeTips() {
        return nodeTips;
    }

    public void setNodeTips(String nodeTips) {
        this.nodeTips = nodeTips;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", replies='" + replies + '\'' +
                ", node='" + node + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_rendered() {
        return content_rendered;
    }

    public void setContent_rendered(String content_rendered) {
        this.content_rendered = content_rendered;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }
}
