package com.jaycodes.rebtel_assignment.repository.remote_data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //defining base url
    public static final String BASE_URL = "https://restcountries.eu/";

    public static Retrofit retrofit = null;
    //getting a single instance of retrofit
    public static Retrofit getApiClient(){
        if(retrofit == null){
          retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
