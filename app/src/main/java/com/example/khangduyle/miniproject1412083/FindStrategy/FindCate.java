package com.example.khangduyle.miniproject1412083.FindStrategy;

import android.app.Application;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khangduyle.miniproject1412083.ListPlaceActivity;
import com.example.khangduyle.miniproject1412083.Place;
import com.example.khangduyle.miniproject1412083.R;
import com.example.khangduyle.miniproject1412083.viewAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by KHANGDUYLE on 27/12/2017.
 */

public class FindCate extends IFind {
    ArrayList<Place> arr =  new ArrayList<Place>();
    private ListView mmListView;
    Boolean check=false;
    @Override
    public void getList(String value, final Context context,ListView mListView, Application app) {
        // Load data

        arr.clear();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference().child("Places");
        mmListView = (ListView) mListView.findViewById(R.id.listView);
        ref.orderByChild("mCategory").equalTo(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Place place=dataSnapshot.getValue(Place.class);
                Place place;
                //Toast.makeText(ListPlaceActivity.this,"Load data",Toast.LENGTH_SHORT).show();
                for(DataSnapshot mydata:dataSnapshot.getChildren()){
                    place =mydata.getValue(Place.class);
                    place.putKey(mydata.getKey());
                    arr.add(place);
                }
                check =true;
                final viewAdapter adapt = new viewAdapter(context,arr);
                mmListView.setAdapter(adapt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                check =true;
            }


        });
        getIntoItem(mmListView,arr,app);
    }
}
