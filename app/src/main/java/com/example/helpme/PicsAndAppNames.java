package com.example.helpme;

import android.graphics.drawable.Drawable;

public class PicsAndAppNames {

    Drawable resim;
    String yazi;

    public Drawable getResim() {
        return resim;
    }

    public String getYazi() {
        return yazi;
    }

    public PicsAndAppNames(Drawable resim,  String yazi) {
        this.resim = resim;
        this.yazi = yazi;
    }
}

//public PicsAndAppNames(Drawable resim, Drawable resim2,Drawable resim3, String yazi, String yazi2, String yazi3)