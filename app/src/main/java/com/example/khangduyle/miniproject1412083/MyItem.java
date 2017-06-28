package com.example.khangduyle.miniproject1412083;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by KHANGDUYLE on 08/05/2017.
 */

public class MyItem  implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle;
    private  String mSnippet;
    public  int mProfilePhoto;

    public Place mPlace;
    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem(double lat, double lng, String title,int pic,Place place) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mProfilePhoto= pic;
        mPlace= place;
    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public int getImg(){
        return mProfilePhoto;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}