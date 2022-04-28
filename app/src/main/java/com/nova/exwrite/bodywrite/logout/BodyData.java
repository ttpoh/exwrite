package com.nova.exwrite.bodywrite.logout;

import java.io.Serializable;

public class BodyData implements Serializable {


    private String bodyweight;
    private String bodymuscle;
    private String bodyfat;
    private String bodycontents;
    private byte[] body_pic;


    public BodyData(String bodyweight, String bodymuscle, String bodyfat, String bodycontents,byte[] bodyImg) {

        this.bodyweight = bodyweight;
        this.bodymuscle = bodymuscle;
        this.bodyfat = bodyfat;
        this.bodycontents = bodycontents;
        this.body_pic = bodyImg;
    }


    public byte[] getBody_pic() {

        return body_pic;
    }

    public void setBody_pic(byte[] body_pic) {

        this.body_pic = body_pic;
    }

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

    public String getBodycontents() {

        return bodycontents;
    }

    public void setBodycontents(String bodycontents) {

        this.bodycontents = bodycontents;
    }
}
