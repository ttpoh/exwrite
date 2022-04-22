package com.nova.exwrite.bodywrite.server;

import java.io.Serializable;

public class BodyData2 implements Serializable {

    private int bodyNo;
    private String bodyNickname;
    private String bodyweight;
    private String bodymuscle;
    private String bodyfat;
    private String bodycontents;
//    private byte[] body_pic;


    public BodyData2(int no, String bodyNickname, String bodyweight, String bodymuscle, String bodyfat) {


        this.bodyNo = no;
        this.bodyNickname = bodyNickname;
        this.bodyweight = bodyweight;
        this.bodymuscle = bodymuscle;
        this.bodyfat = bodyfat;
//        this.bodycontents = bodycontents;
////        this.body_pic = bodyImg;
    }
    public int getBodyNumber() {

        return bodyNo;
    }

    public void setBodyNumber(int no) {

        this.bodyNo = no;
    }
    public String getBodyNickname() {

        return bodyNickname;
    }

    public void setBodyNickname(String bodyNickname) {

        this.bodyNickname = bodyNickname;
    }


//    public byte[] getBody_pic() {
//
//        return body_pic;
//    }
//
//    public void setBody_pic(byte[] body_pic) {
//
//        this.body_pic = body_pic;
//    }

    public String getBodyweight() {

        return bodyweight;
    }

    public void setBodyweight(String bodyweight) {

        this.bodyweight = bodyweight;
    }

    public String getBodymuscle() {

        return bodymuscle;
    }

    public void setBodymuscle(String bodymuscle) {

        this.bodymuscle = bodymuscle;
    }

    public String getBodyfat() {

        return bodyfat;
    }

    public void setBodyfat(String bodyfat) {

        this.bodyfat = bodyfat;
    }

//    public String getBodycontents() {
//
//        return bodycontents;
//    }
//
//    public void setBodycontents(String bodycontents) {
//
//        this.bodycontents = bodycontents;
//    }
}
