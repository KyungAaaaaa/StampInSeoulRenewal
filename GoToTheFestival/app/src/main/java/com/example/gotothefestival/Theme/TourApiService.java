package com.example.gotothefestival.Theme;

import com.example.gotothefestival.Model.ThemeData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TourApiService {

    @GET("/openapi/service/rest/KorService/searchFestival?")
    Call<ThemeData> getData(@QueryMap (encoded = true)Map<String,String> query);
}
