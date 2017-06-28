package com.example.khangduyle.miniproject1412083;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 28/06/2017.
 */


public class Category {
    private String name;
    private Bitmap image;

    public Category(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
