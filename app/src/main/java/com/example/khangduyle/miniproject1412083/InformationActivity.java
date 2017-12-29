package com.example.khangduyle.miniproject1412083;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class InformationActivity extends AppCompatActivity {

    private TextView mPhone;
    private TextView mMail;
    private TextView mName;
    private TextView mWeb;
    private TextView mDesc;
    private TextView mAdd;
    private TextView mCate;
    private EditText mEdtInfo;
    private Button mBtnInfo;
    public String phone;
    public String mail;
    public String web;
    public String name;
    public String add;
    public String cate;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent =getIntent();
        Bundle bdl =intent.getExtras();

        mPhone= (TextView) findViewById(R.id.info_phone);
        mName = (TextView) findViewById(R.id.info_name);
        mAdd = (TextView) findViewById(R.id.info_add);
        mWeb = (TextView) findViewById(R.id.info_web);
        mCate = (TextView) findViewById(R.id.info_category);
        mEdtInfo = (EditText) findViewById(R.id.edt_information);
        mBtnInfo = (Button) findViewById(R.id.btn_information);

        phone=(String) bdl.get("phoneNumber");
        mail= (String) bdl.get("email");
        web= (String) bdl.get("web");
        name = (String) bdl.get("name");
        cate =  (String) bdl.get("category");
        add =  (String) bdl.get("Add");
        key= (String) bdl.get("key");

        mPhone.setText(phone);
        mWeb.setText(web);
        mName.setText(name);
        mAdd.setText(add);
        mCate.setText(cate);
        mBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getInstance().getPermission("Post",getApplicationContext())){
                    String post = mEdtInfo.getText().toString();
                    Notification newOne = new Notification(User.getInstance().getName(),key,post,name);
                    FirebaseDatabase.getInstance().getReference().child("Notification").child(key).setValue(newOne);
                }

            }
        });
    }
}
