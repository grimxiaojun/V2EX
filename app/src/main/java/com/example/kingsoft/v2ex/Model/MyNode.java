package com.example.kingsoft.v2ex.Model;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class MyNode {
    public String imgSrc;
    public String nodeName;
    public String followCount;

    @Override
    public String toString() {
        return "MyNode{" +
                "imgSrc='" + imgSrc + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", collectNumber='" + followCount + '\'' +
                '}';
    }

}
