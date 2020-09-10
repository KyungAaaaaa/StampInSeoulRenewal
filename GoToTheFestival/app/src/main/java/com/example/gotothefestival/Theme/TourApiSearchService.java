package com.example.gotothefestival.Theme;

import com.example.gotothefestival.Model.ThemeData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TourApiSearchService {


    @GET("/openapi/service/rest/KorService/searchKeyword?")
    Call<ThemeData> getSearchData(@QueryMap(encoded = true) Map<String, String> query);
}
