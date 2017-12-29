package com.example.khangduyle.miniproject1412083.RoleState;

/**
 * Created by KHANGDUYLE on 26/12/2017.
 */

public class Reader extends IRole {
    @Override
    public String getRole() {
        return "Reader";
    }

    @Override
    public Boolean getPermission(String screen) {
        switch (screen){
            case "Login": return true;
            case "Upload": return false;
            case "Logout": return false;
            case "Likelist": return false;
            case "Like": return false;
            case "My": return false;
            case "Post": return false;
        }
        return false;
    }
}
