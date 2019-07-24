package com.jaycodes.rebtel_assignment.views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaycodes.rebtel_assignment.databinding.CountryDetailsFragmentBinding;

public class countryDetailsFragment extends Fragment {
    private TitleUpdater titleUpdater;
    public static final String COUNTRY_ID = "country_id";
    public static final String COUNTRY_FLAG = "country_flag";
    public static final String COUNTRY_CAPITAL = "country_flag";
    public static final String COUNTRY_POPULATION = "country_population";
    public static final String COUNTRY_TIMEZONE = "country_timezone";
    public static final String COUNTRY_CALLCODES = "country_callcodes";
    public static final String COUNTRY_LANGUAGES_NAME = "country_languages_name";
    public static final String COUNTRY_LANGUAGES_NATIVENAME = "country_languages_nativename";
    public static final String COUNTRY_CURRENCY_CODE = "country_currency_code";
    public static final String COUNTRY_CURRENCY_NAME = "country_currency_name";
    public static final String COUNTRY_CURRENCY_SYMBOL = "country_currency_symbol";
    String country, flag, capital, population, timezone, call_codes, language_name, language_native_name, currency_code, currency_name, currency_symbol;

    public countryDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get all data passed down from activity, do a null check then add each item to a corresponding variable
        if (getArguments() != null) {

            country = getArguments().getString(COUNTRY_ID);
            flag = getArguments().getString(COUNTRY_FLAG);
            capital = getArguments().getString(COUNTRY_CAPITAL);
            population = getArguments().getString(COUNTRY_POPULATION);
            timezone = getArguments().getString(COUNTRY_TIMEZONE);
            call_codes = getArguments().getString(COUNTRY_CALLCODES);
            language_name = getArguments().getString(COUNTRY_LANGUAGES_NAME);
            language_native_name = getArguments().getString(COUNTRY_LANGUAGES_NATIVENAME);
            currency_code = getArguments().getString(COUNTRY_CURRENCY_CODE);
            currency_name = getArguments().getString(COUNTRY_CURRENCY_NAME);
            currency_symbol = getArguments().getString(COUNTRY_CURRENCY_SYMBOL);
            titleUpdater.updateAppBar(country); //using the interface method set the title
        }

    }

    //an interface method to be implemented by the hosting activity in other to pass data to the activity which in this case is the title
    public interface TitleUpdater {
        void updateAppBar(String title);
    }

    //initialize interface on fragment onAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            titleUpdater = (TitleUpdater) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTitleUpdater");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CountryDetailsFragmentBinding countryDetailsFragmentBinding = CountryDetailsFragmentBinding.inflate(
                inflater, container, false);
        //bind the fragment and also do a null check before binding the data gotten from the activity
        if (getArguments() != null) {
            countryDetailsFragmentBinding.language.setText(language_name);
            countryDetailsFragmentBinding.capital.setText(capital);
            countryDetailsFragmentBinding.population.setText(population);
            countryDetailsFragmentBinding.nativeName.setText(language_native_name);
            countryDetailsFragmentBinding.timezone.setText(timezone);
            countryDetailsFragmentBinding.callcodes.setText(call_codes);
            countryDetailsFragmentBinding.currencyCode.setText(currency_code);
            countryDetailsFragmentBinding.currencyName.setText(currency_name);
            countryDetailsFragmentBinding.currencySymbol.setText(currency_symbol);
        }
        return countryDetailsFragmentBinding.getRoot();
    }
}
