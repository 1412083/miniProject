package com.example.khangduyle.miniproject1412083;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {
    private TextView mPhone;
    private TextView mMail;
    private TextView mName;
    private TextView mWeb;
    private TextView mDesc;
    private TextView mAdd;
    private Button btnCall;
    private Button btnlike;
    private Button btnSurf;
    private Button btnInfo;
    private Button btnShare;

    private ImageView mImg;
    public String phone;
    public String mail;
    public String web;
    public String name;
    public String add;
    public String cate;
    public String key;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        getSupportActionBar().hide();
        btnInfo = (Button) findViewById(R.id.btn_des);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                intent.putExtra("phoneNumber", phone);
                intent.putExtra("name", name);
                intent.putExtra("email", mail);
                intent.putExtra("web", web);
                intent.putExtra("category", cate);
                intent.putExtra("Add", add);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

        init();


        Intent intent =getIntent();
        Bundle bdl =intent.getExtras();



        if (bdl!=null){
            phone=(String) bdl.get("phoneNumber");
            mail= (String) bdl.get("email");
            web= (String) bdl.get("web");
            name = (String) bdl.get("name");
            cate =  (String) bdl.get("category");
            add =  (String) bdl.get("Add");
            key =  (String) bdl.get("key");
            mPhone.setText(phone);
         //   mMail.setText(mail);
            mWeb.setText(web);
         //   mAdd.setText((String) bdl.get("Add"));
            mName.setText(name);
          //  mDesc.setText((String) bdl.get("desc"));
            String path=(String) bdl.get("idAvatar");
            Picasso.with(this)
                    .load(path)
                    .into(mImg);
        }

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+phone));
                if (ActivityCompat.checkSelfPermission(PlaceDetailActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult (int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(phoneIntent);
            }
        });
        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+phone));
                if (ActivityCompat.checkSelfPermission(PlaceDetailActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult (int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(phoneIntent);
            }
        });

        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getInstance().getPermission("Like",getApplicationContext())){
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    LikeList likeIt = new LikeList(currentUser.getEmail(),key);
                    mDatabase.child("LikeList").child(key).setValue(likeIt);
                }

                //Toast.makeText(PlaceDetailActivity.this,  currentUser.getEmail(),Toast.LENGTH_LONG).show();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, name + "\n" + add + "\n");
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });
        mWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(web));
                startActivity(i);
            }
        });

        /*btnCall.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    private void init() {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mPhone= (TextView) findViewById(R.id.tv_call);
        //mMail = (TextView) findViewById(R.id.detailEmail);
        mName = (TextView) findViewById(R.id.tv_name);
        //mAdd = (TextView) findViewById(R.id.detailAdd);
         mWeb = (TextView) findViewById(R.id.tvWebsite);
        //mDesc = (TextView) findViewById(R.id.detailDesc);
         btnCall = (Button) findViewById(R.id.btn_call);
         btnlike = (Button) findViewById(R.id.btn_like);
        btnShare = (Button)findViewById(R.id.btnShare);
        //btnSurf = (Button) findViewById(R.id.detailSurf);
        mImg = (ImageView) findViewById(R.id.tv_img);

    }

}
