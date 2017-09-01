package com.example.kingsoft.v2ex.Model;

import com.example.kingsoft.v2ex.Model.Member;

/**
 * Created by kingsoft on 2017/8/17.
 */

public class Reply {
    private String id;
    private String thanks;
    private String content;
    private String content_rendered;
    private Member member;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThanks() {
        return thanks;
    }

    public void setThanks(String thanks) {
        this.thanks = thanks;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
