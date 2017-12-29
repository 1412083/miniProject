package com.example.khangduyle.miniproject1412083;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KHANGDUYLE on 17/04/2017.
 */

public class viewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Place> mContact;
    LayoutInflater inflter;

    public viewAdapter(Context mcontext, ArrayList<Place> contacts) {
        this.context = mcontext;
        this.mContact = contacts;
        inflter = (LayoutInflater.from(mcontext));
    }


    @Override
    public int getCount() {
        return mContact.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = null;
        view = inflter.inflate(R.layout.custom_ui, null);
        TextView name = (TextView) view.findViewById(R.id.listName);

        TextView add = (TextView) view.findViewById(R.id.listAdd);
        TextView desc = (TextView) view.findViewById(R.id.listDesc);
        TextView user= (TextView) view.findViewById(R.id.listUser);
        ImageView img = (ImageView) view.findViewById(R.id.lisImg);

        String path = mContact.get(position).mImg;
        Picasso.with(context)
                .load(path)
                .into(img);
        String a;
        name.setText(mContact.get(position).mName);
        add.setText(mContact.get(position).mAdd);
        desc.setText(mContact.get(position).mDescription);
        user.setText(mContact.get(position).mUserPost);

        return view;
    }
}
