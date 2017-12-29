package com.example.khangduyle.miniproject1412083;

import java.util.Random;

/**
 * Created by KHANGDUYLE on 26/12/2017.
 */

public class RandomKey {
    private static RandomKey instance;
    private static final int MAX_LENGTH=10;

    private RandomKey(){}

    public static RandomKey getInstance(){

        if (instance==null){
            synchronized (RandomKey.class){
                if (instance == null) {
                    instance = new RandomKey();

                }
            }
        }
        return instance;
    }

    public String generateKey(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();

        char tempChar;
        for (int i = 0; i < MAX_LENGTH; i++){
            tempChar = (char) (generator.nextInt(23) + 97);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
