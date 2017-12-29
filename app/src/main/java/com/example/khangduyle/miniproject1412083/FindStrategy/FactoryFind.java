package com.example.khangduyle.miniproject1412083.FindStrategy;

import com.example.khangduyle.miniproject1412083.RoleState.FactoryRole;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by KHANGDUYLE on 27/12/2017.
 */

public class FactoryFind {
    private static FactoryFind instance;
    private FactoryFind(){};
    private Hashtable<String,IFind> findList =new Hashtable<String,IFind>();
    public static FactoryFind getInstance(){
        if (instance==null){
            synchronized (FactoryFind.class){
                if (instance == null) {
                    instance = new FactoryFind();
                }
            }
        }
        return instance;
    }

    private IFind getFind(String find){
        switch (find){
            case "Cate": return new FindCate();
            case "My": return new FindMy();
            case "Like": return new FindLike();
        }
        return null;
    }

    private IFind getIternalMethodFind(String find){
        if (findList.containsKey(find)){
            return findList.get(find);
        } else {
            IFind newOne = getFind(find);
            findList.put(find,newOne);
            return newOne;
        }
    }

    public IFind getFindMethod(String find){
        return getIternalMethodFind(find);
    }
}
