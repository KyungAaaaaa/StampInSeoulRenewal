package com.example.gotothefestival.Theme;

import com.example.gotothefestival.Model.ThemeData2;

import java.util.Map;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TourApiService {

    @GET("/openapi/service/rest/KorService/searchFestival?")
    Call<ThemeData2> getData(@QueryMap (encoded = true)Map<String,String> query);
}
