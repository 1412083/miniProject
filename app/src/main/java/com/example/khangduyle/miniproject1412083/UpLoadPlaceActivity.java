package com.example.khangduyle.miniproject1412083;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.khangduyle.miniproject1412083.ModuleDirection.DirectionFinder;
import com.example.khangduyle.miniproject1412083.ModuleDirection.LocationFinder;
import com.example.khangduyle.miniproject1412083.ModuleDirection.LocationFinderListener;
import com.example.khangduyle.miniproject1412083.ModuleDirection.Route;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpLoadPlaceActivity extends AppCompatActivity implements LocationFinderListener {
    private StorageReference mStorage;
    private ImageButton mSelectImg;
    private EditText mEdtName; // Nhập tên địa điểm
    private EditText mEdtDecript; // Nhập mô tả
    private EditText mEdtAddress; // Nhập địa chỉ
    private EditText mEdtPhone; // Nhập số điện thoại
    private EditText mEdtEmail; // Nhập email
    private EditText mEdtWeb;
    private Button btn;  // Nút submit
    private static final int GALLERY_INTENT = 2;
    private Uri uri;
    private DatabaseReference mDatabase;
    private static final int MAX_LENGTH=10;
    private ProgressDialog mProgress;
    private int mCheck;
    private  Spinner spinner;
    private Point curPoint;
    private String cate="Market";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_place);
        init();
        // Nhấp vào hình để chọn hình ở thư viện
        mSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        mCheck=0;
        btn= (Button)findViewById(R.id.btnSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }
    //Post dữ liệu địa điểm lên database
    private void startPosting() {
        mProgress.setMessage("Posting to map...");
        mProgress.show();

        String name= mEdtName.getText().toString().trim();
        String descript= mEdtDecript.getText().toString().trim();
        String adrress=mEdtAddress.getText().toString().trim();

        try {
            new LocationFinder(this,adrress).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCheck==1){
            Toast.makeText(this,"Địa chỉ nhập không tồn tại",Toast.LENGTH_LONG).show();
            return;
        }



    }
    //Sinh ra một chuỗi ngẫu nhiên
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();

        char tempChar;
        for (int i = 0; i < MAX_LENGTH; i++){
            tempChar = (char) (generator.nextInt(23) + 97);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    // Khởi tạo các tham số
    private void init() {
        //Khởi tạo để thao tác với việc lưu trữ file
        mStorage = FirebaseStorage.getInstance().getReference();
        // Khởi tạo để thao tác với database trên firebase
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mEdtName= (EditText)findViewById(R.id.edtName);
        mEdtDecript= (EditText)findViewById(R.id.edtDescript);
        mSelectImg = (ImageButton) findViewById(R.id.imbBtn1);
        mEdtAddress= (EditText)findViewById(R.id.edtAddress);
        mEdtEmail = (EditText) findViewById(R.id.edtmail);
        mEdtPhone = (EditText) findViewById(R.id.edtphone);
        mEdtWeb = (EditText) findViewById(R.id.edtweb);
        mProgress = new ProgressDialog(this);
        // Spinner element
        spinner = (Spinner) findViewById(R.id.edtspinner);
        List<String> categories = new ArrayList<String>();

        categories.add("Market");
        categories.add("Museum");
        categories.add("Hotel");
        categories.add("Restaurant");
        categories.add("Cinema");
        categories.add("Bank");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    // Lắng nghe việc tả về hình ảnh và upload  lên firebase
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mProgress.setMessage("Upload image...");
        mProgress.show();
        if (requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            uri=data.getData();

            // Show hình ảnh lên buuton image
            try {

                final InputStream imageStream = getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mSelectImg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


            StorageReference filePath=mStorage.child("Photos").child("Places").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UpLoadPlaceActivity.this,"Upload success! ",Toast.LENGTH_LONG).show();
                    mProgress.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpLoadPlaceActivity.this,"Upload fail! ",Toast.LENGTH_LONG).show();
                    mProgress.dismiss();
                }
            });

        }
    }


    @Override
    public void onLocationFinderStart() {
        mProgress = ProgressDialog.show(this, "Please wait", "Finding direction...", true);
    }

    @Override
    public void onLoctionFinderSuccess(Point point) {

        mProgress.dismiss();
        if (point!=null){
            mCheck=2;
            curPoint=point;
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();

            Toast.makeText(this,curPoint.getAdd(),Toast.LENGTH_LONG).show();
            final String name= mEdtName.getText().toString().trim();
            final String descript= mEdtDecript.getText().toString().trim();
            final String phone = mEdtPhone.getText().toString().trim();
            final String email = mEdtEmail.getText().toString().trim();
            final String website = mEdtWeb.getText().toString().trim();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0 ) cate="Market";
                    if (position == 1 ) cate="Museum";
                    if (position == 2 ) cate="Hotel";
                    if (position == 3 ) cate="Restaurant";
                    if (position == 4 ) cate="Cinema";
                    if (position == 5 ) cate="Bank";

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(descript) && uri!=null){

                StorageReference filepath=mStorage.child("Photos").child("Places").child(uri.getLastPathSegment());
                mEdtAddress.setText(curPoint.getAdd());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri=taskSnapshot.getDownloadUrl();

                        Place newOne= new Place(name,curPoint.getAdd(),curPoint.getPoint(),descript,downloadUri.toString(),email,phone,website,cate);
                        newOne.pushUser(User.getInstance().getName());
                        String key=random();
                        mDatabase.child("Places").child(key).setValue(newOne);
                        Toast.makeText(UpLoadPlaceActivity.this,"Upload success! ",Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(UpLoadPlaceActivity.this,mapActivity.class);
                        startActivity(intent);
                        mProgress.dismiss();
                    }
                });

            }
        }
        else mCheck=1;
    }
}
