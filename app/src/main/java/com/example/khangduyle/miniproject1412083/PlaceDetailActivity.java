package com.example.khangduyle.miniproject1412083;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {
    private TextView mPhone;
    private TextView mMail;
    private TextView mName;
    private TextView mWeb;
    private TextView mDesc;
    private TextView mAdd;
    private Button btnCall;
    private Button btnSend;
    private Button btnSurf;
    private ImageView mImg;
    public String phone;
    public String mail;
    public String web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        getSupportActionBar().hide();
      /*  Intent intent =getIntent();
        Bundle bdl =intent.getExtras();
        init();


        if (bdl!=null){
            phone=(String) bdl.get("phoneNumber");
            mail= (String) bdl.get("email");
            web= (String) bdl.get("web");
            mPhone.setText(phone);
            mMail.setText(mail);
            mWeb.setText(web);
            mAdd.setText((String) bdl.get("Add"));
            mName.setText((String) bdl.get("name"));
            mDesc.setText((String) bdl.get("desc"));
            String path=(String) bdl.get("idAvatar");
            Picasso.with(this)
                    .load(path)
                    .into(mImg);
        }

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:"+phone;
                Intent call = new Intent(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                Intent new_intent = Intent.createChooser(emailIntent, "Share via");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(new_intent);
            }
        });

        btnSurf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = web;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void init() {
        mPhone= (TextView) findViewById(R.id.detailPhone);
        mMail = (TextView) findViewById(R.id.detailEmail);
        mName = (TextView) findViewById(R.id.detailStore);
        mAdd = (TextView) findViewById(R.id.detailAdd);
        mWeb = (TextView) findViewById(R.id.detailWeb);
        mDesc = (TextView) findViewById(R.id.detailDesc);
        btnCall = (Button) findViewById(R.id.detailCall);
        btnSend = (Button) findViewById(R.id.detailSend);
        btnSurf = (Button) findViewById(R.id.detailSurf);
        mImg = (ImageView) findViewById(R.id.detailImg);

    }*/
    }
}
