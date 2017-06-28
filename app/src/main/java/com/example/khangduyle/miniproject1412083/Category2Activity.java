package com.example.khangduyle.miniproject1412083;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Category2Activity extends AppCompatActivity {

    Button btnMarket;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        btnMarket = (Button)findViewById(R.id.btn_market);
        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListPlaceActivity.class);
                startActivity(intent);
            }
        });
    }
}
