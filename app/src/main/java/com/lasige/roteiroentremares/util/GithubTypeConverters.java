package com.lasige.roteiroentremares.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class GithubTypeConverters {

    @TypeConverter
    public static ArrayList<String> stringToStringArrayList(String data) {
        if (data == null) {
            return new ArrayList<String>();
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stringArrayListToString(ArrayList<String> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
