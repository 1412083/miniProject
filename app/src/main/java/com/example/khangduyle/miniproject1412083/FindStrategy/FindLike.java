package com.example.khangduyle.miniproject1412083.FindStrategy;

import android.app.Application;
import android.content.Context;
import android.widget.ListView;

import com.example.khangduyle.miniproject1412083.LikeList;
import com.example.khangduyle.miniproject1412083.Place;
import com.example.khangduyle.miniproject1412083.R;
import com.example.khangduyle.miniproject1412083.viewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by KHANGDUYLE on 27/12/2017.
 */

public class FindLike extends IFind {
    ArrayList<Place> arr= new  ArrayList<Place>();
    private ListView mmListView;
    Boolean check=false;
    @Override
    public void getList(String value, final Context context,ListView mListView, Application app) {
        arr.clear();
        mmListView = (ListView) mListView.findViewById(R.id.listView);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("LikeList");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LikeList item;
                for(DataSnapshot mydata:dataSnapshot.getChildren()) {
                    item = mydata.getValue(LikeList.class);
                    String key = item.mPlace;
                    DatabaseReference refPlace = FirebaseDatabase.getInstance().getReference().child("Places").child(key);
                    refPlace.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Place place;
                            place = dataSnapshot.getValue(Place.class);
                            place.putKey(dataSnapshot.getKey());
                            System.out.println(place);
                            arr.add(place);
                            viewAdapter adapt1 = new viewAdapter(context,arr);
                            mmListView.setAdapter(adapt1);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            check =true;
                        }
                    });
                }
                check= true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getIntoItem(mmListView,arr,app);
    }
}
