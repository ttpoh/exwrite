package com.nova.exwrite.meal;

import java.io.Serializable;

public class mealData implements Serializable {

    private String mtitle;
    private String mtime;
    private String mamount;
    private String mcontents;
    private byte[] meal_pic;


    public mealData(String mtitle, String mtime, String mamount, String mcontents,byte[] meal_pic) {

        this.mtitle = mtitle;
        this.mamount = mamount;
        this.mtime = mtime;
        this.mcontents = mcontents;
        this.meal_pic = meal_pic;
    }


    public byte[] getMeal_pic() {

        return meal_pic;
    }

    public void setMeal_pic(byte[] meal_pic) {

        this.meal_pic = meal_pic;
    }

    public String getMtitle() {

        return mtitle;
    }

    public void setMtitle(String mtitle) {

        this.mtitle = mtitle;
    }

    public String getMamount() {

        return mamount;
    }

    public void setMamout(String mamount) {

        this.mamount = mamount;
    }

    public String getMtime() {

        return mtime;
    }

    public void setMtime(String mtime) {

        this.mtime = mtime;
    }

    public String getMcontents() {

        return mcontents;
    }

    public void setMcontents(String mcontents) {

        this.mcontents = mcontents;
    }
}
