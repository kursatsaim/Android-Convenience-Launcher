package com.example.helpme;

import android.graphics.drawable.Drawable;

public class PicsAndAppNames {

    Drawable resim, resim2,resim3;
    String yazi,yazi2,yazi3;

    public Drawable getResim() {
        return resim;
    }

    public String getYazi() {
        return yazi;
    }

    public PicsAndAppNames(Drawable resim,  String yazi) {
        this.resim = resim;
        //this.resim2 = resim2;
        //this.resim3 = resim3;
        this.yazi = yazi;
        //this.yazi2 = yazi2;
        //this.yazi3 = yazi3;
    }
}

//public PicsAndAppNames(Drawable resim, Drawable resim2,Drawable resim3, String yazi, String yazi2, String yazi3)