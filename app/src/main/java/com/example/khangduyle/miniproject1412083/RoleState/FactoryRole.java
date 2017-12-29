package com.example.khangduyle.miniproject1412083.RoleState;

/**
 * Created by KHANGDUYLE on 26/12/2017.
 */

public class FactoryRole {
    private static FactoryRole instance =null;
    private FactoryRole(){};
    public static FactoryRole getFactoryRole(){
        if (instance==null){
            synchronized (FactoryRole.class){
                if (instance == null) {
                    instance = new FactoryRole();
                }
            }
        }
        return instance;
    }

    public IRole getRole(String roleName){
        switch (roleName){
            case "admin": return new Admin();
            case "Poster": return new Poster();
        }
        return new Reader();
    }
}
