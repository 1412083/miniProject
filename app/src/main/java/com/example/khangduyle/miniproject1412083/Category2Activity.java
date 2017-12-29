package com.example.khangduyle.miniproject1412083;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Category2Activity extends AppCompatActivity {

    Button btnMarket;
    Button btnMuseum;
    Button btnHotel;
    Button btnRestaurant;
    Button btnCinema;
    Button btnBank;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnMarket = (Button)findViewById(R.id.btn_market);
        btnMuseum = (Button) findViewById(R.id.btn_museum);
        btnHotel = (Button) findViewById(R.id.btn_hotel);
        btnRestaurant = (Button) findViewById(R.id.btn_restaurant);
        btnCinema = (Button) findViewById(R.id.btn_cinema);
        btnBank = (Button) findViewById(R.id.btn_bank);

        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Market");
                startActivity(intent);
            }
        });

        btnMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Museum");
                startActivity(intent);
            }
        });

        btnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Hotel");
                startActivity(intent);
            }
        });

        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Restaurant");
                startActivity(intent);
            }
        });

        btnCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Cinema");
                startActivity(intent);
            }
        });

        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                intent.putExtra("type","Bank");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
