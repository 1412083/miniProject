package com.example.khangduyle.miniproject1412083.FindStrategy;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khangduyle.miniproject1412083.ListPlaceActivity;
import com.example.khangduyle.miniproject1412083.Place;
import com.example.khangduyle.miniproject1412083.PlaceDetailActivity;

import java.util.ArrayList;

/**
 * Created by KHANGDUYLE on 27/12/2017.
 */

public abstract class IFind {
    public abstract void getList(String value, Context context, ListView mListView, Application app);

    public void getIntoItem(ListView mListView, final ArrayList<Place> arr, final Application app){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place;
                place = arr.get(position);
                Intent intent = new Intent(app.getApplicationContext(), PlaceDetailActivity.class );// not edit yet

                intent.putExtra("phoneNumber",place.mNumber);
                intent.putExtra("name", place.mName);
                intent.putExtra("email", place.mEmail);
                intent.putExtra("idAvatar", place.mImg );
                intent.putExtra("web",place.mWebsite);
                intent.putExtra("Add",place.mAdd);
                intent.putExtra("desc",place.mDescription);
                intent.putExtra("Lat",place.mLat);
                intent.putExtra("Lng",place.mLng);
                intent.putExtra("userPost",place.mUserPost);
                intent.putExtra("category",place.mCategory);
                intent.putExtra("key",place.mkey);
                app.startActivity(intent);
            }
        });
    }
}
