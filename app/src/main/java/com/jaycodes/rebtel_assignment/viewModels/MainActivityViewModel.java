package com.jaycodes.rebtel_assignment.viewModels;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.jaycodes.rebtel_assignment.repository.CountryRepository;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import com.jaycodes.rebtel_assignment.utils.Resource;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private CountryRepository countryRepository; //declare countryRepository

//initialize countryRepository
    public void init(Context context){
        countryRepository = CountryRepository.getInstance(context);
    }
//load information from room database
    public LiveData<Resource<List<countryModel>>> getCountryList(){
        return countryRepository.getCountries();
    }

}
