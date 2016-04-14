package com.d954mas.android.yandextest.utils;

/**
 * Created by user on 14.04.2016.
 */
public class TextUtils {
    public static String getStringByNumber(int number ,String string1,String string2,String string5){
        number=number%10;
        if(number==1)return string1;
        else if(number<5)return string2;
        else return string5;
    }
}
