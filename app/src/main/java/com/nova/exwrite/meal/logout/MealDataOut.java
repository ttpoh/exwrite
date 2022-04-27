package com.nova.exwrite.meal.logout;

import java.io.Serializable;

public class MealDataOut implements Serializable {
    private int mealNo;
    private String mtitle;
    private String mtime;
    private String mamount;
    private String mcontents;
    private byte[] meal_pic;


    public MealDataOut(String mname, String mtime, String mamount, String mmemo,byte[] meal_pic) {
//        this.mealNo = no;
        this.mtitle = mname;
        this.mtime = mtime;
        this.mamount = mamount;
        this.mcontents = mmemo;
        this.meal_pic = meal_pic;
    }
    public int getMealNumber() {

        return mealNo;
    }

    public void setMealNumber(int no) {

        this.mealNo = no;
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
