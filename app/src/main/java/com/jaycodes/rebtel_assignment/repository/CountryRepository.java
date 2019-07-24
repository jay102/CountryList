package com.jaycodes.rebtel_assignment.repository;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaycodes.rebtel_assignment.repository.database.countryDatabase;
import com.jaycodes.rebtel_assignment.repository.remote_data.ApiClient;
import com.jaycodes.rebtel_assignment.repository.remote_data.ApiService;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import com.jaycodes.rebtel_assignment.utils.NetworkBoundResource;
import com.jaycodes.rebtel_assignment.utils.Resource;

import java.util.List;

import retrofit2.Call;

public class CountryRepository {
    private static final String TAG = "repo";
    private static CountryRepository countryRepository;
    private ApiService apiService;
    private countryDatabase db;

    //get single instance of countryRepository
    public static CountryRepository getInstance(Context context) {
        if (countryRepository == null) {
            countryRepository = new CountryRepository(context);
        }
        return countryRepository;
    }

    //constructor that takes a context and initializes room database and our request client
    private CountryRepository(Context context) {
        apiService = ApiClient.getApiClient().create(ApiService.class);
        db = countryDatabase.getInstance(context);

    }

    /**
     * NetworkBoundResource is a generic abstract class that handles the data being shown to the user,
     * the idea is to insert the data gotten from the api into the database and use that as cache so when theres
     * no network connectivity the user still gets to see some data pending when theres connectivity and a new data.
     * The NetworkBoundResource class uses a Mediator Livedata under the hood and gives us access to methods that lets us
     * loadfrom db or fetch from network etc.
     **/
    private LiveData<Resource<List<countryModel>>> loadCountryList() {
        return new NetworkBoundResource<List<countryModel>, List<countryModel>>() {

            @Override
            protected void saveCallResult(@NonNull List<countryModel> item) {
                db.countryDao().deleteAll();
                db.countryDao().insert(item); //save call and insert to database
            }

            @NonNull
            @Override
            protected LiveData<List<countryModel>> loadFromDb() {
                return db.countryDao().getAll(); //load data from database
            }

            @Override
            protected boolean shouldFetch(@Nullable List<countryModel> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected Call<List<countryModel>> createCall() {
                return apiService.getCountryList(); //make call request with our interface.
            }
        }.getAsLiveData(); //return as LiveData
    }

    //return information gotten from loadCountryList method
    public LiveData<Resource<List<countryModel>>> getCountries() {
        return loadCountryList();
    }
}
