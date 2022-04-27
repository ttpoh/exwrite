package com.nova.exwrite.exercise;

import java.io.Serializable;

public class ExData implements Serializable {

    private int exNo;
    private String exid;
    private String extitle;
    private String exstart;
    private String extime;
    private String excontents;
    private byte[] ex_pic;


    public ExData(int no, String id, String extitle, String exstart, String extime, String excontents) {

        this.exNo = no;
        this.exid = id;
        this.extitle = extitle;
        this.exstart = exstart;
        this.extime = extime;
        this.excontents = excontents;
//        this.ex_pic = exImg;
    }
    public int getExNumber() {

        return exNo;
    }

    public void setExNumber(int no) {

        this.exNo = no;
    }

    public byte[] getEx_pic() {

        return ex_pic;
    }

    public void setEx_pic(byte[] ex_pic) {

        this.ex_pic = ex_pic;
    }
    public String getExId() {

        return exid;
    }

    public void setExId(String exid) {

        this.exid = exid;
    }

    public String getExtitle() {

        return extitle;
    }

    public void setExtitle(String extitle) {

        this.extitle = extitle;
    }

    public String getExstart() {

        return exstart;
    }

    public void setExstart(String exstart) {

        this.exstart = exstart;
    }

    public String getExtime() {

        return extime;
    }

    public void setExtime(String extime) {

        this.extime = extime;
    }

    public String getExcontents() {

        return excontents;
    }

    public void setExcontents(String excontents) {

        this.excontents = excontents;
    }
}
