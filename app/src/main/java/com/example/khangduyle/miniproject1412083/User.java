package com.example.khangduyle.miniproject1412083;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.khangduyle.miniproject1412083.RoleState.FactoryRole;
import com.example.khangduyle.miniproject1412083.RoleState.IRole;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by KHANGDUYLE on 25/12/2017.
 */

public class User  {
    private static User instance;
    private String mName;
    private String uid="";
    private IRole userRole =FactoryRole.getFactoryRole().getRole("reader");
    private User(){}

    public static User getInstance(){

        if (instance==null){
            synchronized (User.class){
                if (instance == null) {
                    instance = new User();

                }
            }
        }
        return instance;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        this.mName = name;
    }

    public void setUid(String uid){ this.uid = uid;}

    public String getUid(){
        return this.uid;
    }

    public void logOut(){

        FirebaseAuth.getInstance().signOut();
        mName = "Guest";
        uid ="";
        userRole =FactoryRole.getFactoryRole().getRole("reader");
    }

   /* public void helloWorld(Context context){
        Toast.makeText(context, "Hi "+User.getInstance().getName()+ ". Have a good day!",  Toast.LENGTH_SHORT).show();
    }*/
    public void likeChannel(){

    }

    public void getRole(Context context){
        if (userRole==null){
            FirebaseAuth.getInstance().signOut();
        }
        Toast.makeText(context,mName+ " - "+ userRole.getRole(),  Toast.LENGTH_SHORT).show();

    }

    public void initRole(final Application app){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Role");
        final Role[] role = {null};
         //OvrFtdu9WNWP78AQYZGve1DeN6l2
        System.out.println("Init role");
        Query query = ref.orderByChild("Uid").equalTo(this.uid);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Role childRole;
                System.out.println("onChangedData");
                for(DataSnapshot mydata:dataSnapshot.getChildren()){
                    childRole =mydata.getValue(Role.class);
                    System.out.println(childRole.role);
                    role[0] = childRole;
                    break;
                }
                if (role[0]==null){
                    System.out.println("ahihi reader");
                    userRole = FactoryRole.getFactoryRole().getRole("reader");
                } else {
                    System.out.println("ahihi admin");
                    userRole = FactoryRole.getFactoryRole().getRole("admin");
                    //mProgress.dismiss();
                    app.startActivity(new Intent(app.getApplicationContext(),MainMenuActivity.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setRole(Context context){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Role newRole = new Role(uid,mName);
        String key= RandomKey.getInstance().generateKey();
        mDatabase.child("Role").child(key).setValue(newRole);
        Toast.makeText(context, "Post role",  Toast.LENGTH_SHORT).show();
    }

    public Boolean getPermission(String screen, Context context){
        if  (userRole.getPermission(screen)){
            Toast.makeText(context, "Welcome "+screen,  Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "You do not have permission access "+screen,  Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
