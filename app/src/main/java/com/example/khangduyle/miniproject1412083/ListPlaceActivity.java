package com.example.khangduyle.miniproject1412083;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPlaceActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Place> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        mListView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        arr= new  ArrayList<Place>();

        // Load data
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference().child("Places");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Place place=dataSnapshot.getValue(Place.class);
                Place place;
                Toast.makeText(ListPlaceActivity.this,"Load data",Toast.LENGTH_SHORT).show();
                for(DataSnapshot mydata:dataSnapshot.getChildren()){
                    place =mydata.getValue(Place.class);
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
