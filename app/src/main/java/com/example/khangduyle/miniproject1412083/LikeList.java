package com.example.khangduyle.miniproject1412083;

/**
 * Created by KHANGDUYLE on 06/07/2017.
 */

public class LikeList {
    public String mUser;
    public String mPlace;

    public LikeList() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LikeList(String user, String place) {
        this.mUser = user;
        this.mPlace = place;
    }

}
