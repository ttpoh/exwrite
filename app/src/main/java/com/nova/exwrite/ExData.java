package com.nova.exwrite;

import java.io.Serializable;

public class ExData implements Serializable {


    private String extitle;
    private String exstart;
    private String extime;
    private String excontents;
    private byte[] ex_pic;


    public ExData(String extitle, String exstart, String extime, String excontents, byte[] ex_pic) {

        this.extitle = extitle;
        this.exstart = exstart;
        this.extime = extime;
        this.excontents = excontents;
        this.ex_pic = ex_pic;
    }


    public byte[] getEx_pic() {

        return ex_pic;
    }

    public void setEx_pic(byte[] ex_pic) {

        this.ex_pic = ex_pic;
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
