package com.example.khangduyle.miniproject1412083;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by KHANGDUYLE on 07/05/2017.
 */

public class Place {
    public String mName;
    public String mDescription;
    public String mImg;
    public String mAdd;
    public String mEmail;
    public String mNumber;
    public String mWebsite;
    public String mUserPost;
    public Double mLat;
    public Double mLng;
    public Place() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Place(String name,String add,LatLng point, String description, String img,String email,String number,String web) {
        this.mDescription = description;
        this.mName = name;
        this.mImg= img;
        this.mUserPost="KhangDuyLe";
        this.mAdd=add;
        this.mLat=point.latitude;
        this.mLng=point.longitude;
        this.mEmail=email;
        this.mNumber=number;
        this.mWebsite=web;
    }
}
