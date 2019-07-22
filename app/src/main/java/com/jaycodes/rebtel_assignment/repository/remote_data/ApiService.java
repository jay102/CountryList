package com.jaycodes.rebtel_assignment.repository.remote_data;

import com.jaycodes.rebtel_assignment.repository.models.countryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("rest/v2/all")
    Call<List<countryModel>> getCountryList(); //define our retrofit call method
}
