package com.example.khangduyle.miniproject1412083;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by KHANGDUYLE on 08/05/2017.
 */

public class Point {
    public LatLng mPoint;
    public String mAdd;
    public  Point(LatLng point, String add){
        this.mAdd=add;
        this.mPoint=point;
    }
    public  LatLng getPoint(){
        return mPoint;
    }
    public String getAdd(){
        return mAdd;
    }
}
