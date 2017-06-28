package com.example.khangduyle.miniproject1412083;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ListView listViewCategory;
    ListCategoryAdapter adapter;
    ArrayList<Category> categoryArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initControls();
        loadData();
    }
    private void loadData() {
        categoryArrayList.add(new Category("Hotels",
                BitmapFactory.decodeResource(getResources(), R.drawable.bt1)));
        categoryArrayList.add(new Category("Restaurants",
                BitmapFactory.decodeResource(getResources(), R.drawable.bt1)));
        categoryArrayList.add(new Category("Museums",
                BitmapFactory.decodeResource(getResources(), R.drawable.bt1)));
        categoryArrayList.add(new Category("Markets",
                BitmapFactory.decodeResource(getResources(), R.drawable.bt1)));
        categoryArrayList.add(new Category("Drinks",
                BitmapFactory.decodeResource(getResources(), R.drawable.bt1)));

        adapter.addAll(categoryArrayList);

    }
    private void initControls() {
        listViewCategory = (ListView)findViewById(R.id.list_view_category);
        adapter = new ListCategoryAdapter(this);
        categoryArrayList = new ArrayList<>();
        listViewCategory.setAdapter(adapter);
        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Trailer trailer = (Trailer) listViewTrailer.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ListPlaceActivity.class);
                //intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
    }
}
