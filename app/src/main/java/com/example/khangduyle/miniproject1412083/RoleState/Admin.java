package com.example.khangduyle.miniproject1412083.RoleState;

/**
 * Created by KHANGDUYLE on 26/12/2017.
 */

public class Admin extends IRole {
    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public Boolean getPermission(String screen) {
        switch (screen){
            case "Login": return false;
            case "Upload": return true;
            case "Logout": return true;
            case "Likelist": return true;
            case "Like": return true;
            case "My": return true;
            case "Post": return true ;
        }
        return false;
    }
}
