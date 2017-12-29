package com.example.khangduyle.miniproject1412083;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khangduyle.miniproject1412083.FindStrategy.FactoryFind;
import com.example.khangduyle.miniproject1412083.FindStrategy.FindCate;
import com.example.khangduyle.miniproject1412083.FindStrategy.FindLike;
import com.example.khangduyle.miniproject1412083.FindStrategy.FindMy;
import com.example.khangduyle.miniproject1412083.FindStrategy.IFind;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPlaceActivity extends AppCompatActivity {

    private ListView mListView;
     ArrayList<Place> arr;
    final  FirebaseDatabase database=FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        mListView = (ListView) findViewById(R.id.listView);
        //User.getInstance().helloWorld(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        String value="";
        arr= new  ArrayList<Place>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("type");
            //The key argument here must match that used in the other activity
        }
        /*if (value.matches("like")){
            findLike();
        }else if (value.matches("My")){
            findMy();
        } else {
            findCate(value);
        }*/
        IFind add;
        if (value.matches("like")){
            add = FactoryFind.getInstance().getFindMethod("Like");
        }else if (value.matches("My")){
            add = FactoryFind.getInstance().getFindMethod("My");
        } else {
            add = FactoryFind.getInstance().getFindMethod("Cate");
        }
        add.getList(value, getApplicationContext(),mListView,getApplication());

        //viewAdapter adapt1 = new viewAdapter(getApplicationContext(),arr);
        //mListView.setAdapter(adapt1);

    }

    private void findMy() {

    }

    private void findLike() {

        DatabaseReference ref=database.getReference().child("LikeList");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LikeList item;
                for(DataSnapshot mydata:dataSnapshot.getChildren()){
                    item =mydata.getValue(LikeList.class);
                    String key = item.mPlace;
                    DatabaseReference refPlace=database.getReference().child("Places").child(key);
                    refPlace.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Place place;
                            place =dataSnapshot.getValue(Place.class);
                            place.putKey(dataSnapshot.getKey());
                            System.out.println(place);
                            arr.add(place);
                            viewAdapter adapt1 = new viewAdapter(getApplicationContext(),arr);
                            mListView.setAdapter(adapt1);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void findCate(String value)
    {
        // Load data
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference().child("Places");

        ref.orderByChild("mCategory").equalTo(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Place place=dataSnapshot.getValue(Place.class);
                Place place;
                Toast.makeText(ListPlaceActivity.this,"Load data",Toast.LENGTH_SHORT).show();
                for(DataSnapshot mydata:dataSnapshot.getChildren()){
                    place =mydata.getValue(Place.class);
                    place.putKey(mydata.getKey());
                    arr.add(place);
                }

                final viewAdapter adapt = new viewAdapter(getApplicationContext(),arr);
                mListView.setAdapter(adapt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
