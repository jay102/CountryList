package com.jaycodes.rebtel_assignment.repository.database.converters;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaycodes.rebtel_assignment.repository.models.Currencies;
import com.jaycodes.rebtel_assignment.repository.models.Languages;

import java.lang.reflect.Type;
import java.util.ArrayList;

//Convert ArrayLists and String array values to strings and back

public class ArrayListConverter {
    @TypeConverter
    public static ArrayList<Currencies> currencyConverter(String value) {
        Type listType = new TypeToken<ArrayList<Currencies>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String currencyArrayList(ArrayList<Currencies> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
    @TypeConverter
    public static ArrayList<Languages> languageConverter(String value) {
        Type listType = new TypeToken<ArrayList<Languages>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String languageArrayList(ArrayList<Languages> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static String toString(String[] value){
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    @TypeConverter
    public static String[] toStringArray(String value){
        Type listType = new TypeToken<String[]>(){}.getType();
            return new Gson().fromJson(value,listType);
}
}
