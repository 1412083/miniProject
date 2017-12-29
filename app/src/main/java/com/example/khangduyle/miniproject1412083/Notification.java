package com.example.khangduyle.miniproject1412083;

/**
 * Created by KHANGDUYLE on 27/12/2017.
 */

public class Notification{
    public String mUser;
    public String mPlace;
    public String mPlaceName;
    public String mInfo;
    public Boolean state;
    public Notification() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Notification(String user,String place,String info,String name){
        this.mUser=user;
        this.mPlace=place;
        this.mInfo=info;
        this.state = true;
        this.mPlaceName = name;
    }
}
