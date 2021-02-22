package com.android.roteiroentremares.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
}
