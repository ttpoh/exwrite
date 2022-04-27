package com.nova.exwrite.meal;

import java.io.Serializable;

public class MealData implements Serializable {
    private int mealNo;
    private String mtitle;
    private String mtime;
    private String mamount;
    private String mcontents;
//    private byte[] meal_pic;


    public MealData(int no, String mname, String mtime, String mamount, String mmemo) {
        this.mealNo = no;
        this.mtitle = mname;
        this.mamount = mamount;
        this.mtime = mtime;
        this.mcontents = mmemo;
//        this.meal_pic = meal_pic;
    }
    public int getMealNumber() {

        return mealNo;
    }

    public void setMealNumber(int no) {

        this.mealNo = no;
    }

//    public byte[] getMeal_pic() {
//
//        return meal_pic;
//    }
//
//    public void setMeal_pic(byte[] meal_pic) {
//
//        this.meal_pic = meal_pic;
//    }

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
