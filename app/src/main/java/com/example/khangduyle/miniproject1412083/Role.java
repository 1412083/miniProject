package com.example.khangduyle.miniproject1412083;

/**
 * Created by KHANGDUYLE on 26/12/2017.
 */

public class Role {
    public String Uid;
    public String role;

    public Role(){};
    // Reader, poster, admin
    public Role(String uid, String role){
        this.Uid =uid;
        this.role = role;
    }
}
