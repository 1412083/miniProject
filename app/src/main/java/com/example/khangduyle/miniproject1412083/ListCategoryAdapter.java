package com.example.khangduyle.miniproject1412083;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 28/06/2017.
 */

public class ListCategoryAdapter extends ArrayAdapter<Category> {

    Context context;

    public ListCategoryAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            view = layoutInflater.inflate(R.layout.category_row, null);
        }
        Category category = getItem(position);
        if (category != null) {
            ((TextView)view.findViewById(R.id.text_view_name)).setText(category.getName());
            ((ImageView)view.findViewById(R.id.img_view)).setImageBitmap(category.getImage());
        }
        return view;
    }
}
