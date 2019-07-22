package com.jaycodes.rebtel_assignment.repository.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.jaycodes.rebtel_assignment.repository.database.converters.ArrayListConverter;

import java.util.ArrayList;
import java.util.Arrays;

@Entity(tableName = "countryList")
@TypeConverters({ArrayListConverter.class})

public class countryModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String flag;
    private String capital;
    private String population;
    private String[] timezones;
    private ArrayList<Currencies> currencies;
    private ArrayList<Languages> languages;
    private String[] callingCodes;

    public countryModel(String name, String flag, String capital, String population, String[] timezones, ArrayList<Currencies> currencies, ArrayList<Languages> languages, String[] callingCodes) {
        this.name = name;
        this.flag = flag;
        this.capital = capital;
        this.population = population;
        this.timezones = timezones;
        this.currencies = currencies;
        this.languages = languages;
        this.callingCodes = callingCodes;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCapital() {
        return capital;
    }

    public String getPopulation() {
        return population;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public ArrayList<Currencies> getCurrencies() {
        return currencies;
    }

    public ArrayList<Languages> getLanguages() {
        return languages;
    }

    public String[] getCallingCodes() {
        return callingCodes;
    }

    @NonNull
    @Override
    public String toString() {
        return "countryModel{" +
                "Country='" + name + '\'' +
                ", Flag='" + flag + '\'' +
                ", capital='" + capital + '\'' +
                ", Population='" + population + '\'' +
                ", timezone=" + Arrays.toString(timezones) +
                ", currency=" + currencies +
                ", language=" + languages +
                ", callCodes=" + Arrays.toString(callingCodes) +
                '}';
    }
}
