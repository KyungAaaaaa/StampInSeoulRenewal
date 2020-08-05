package com.example.stampinseoul2;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThemeService {
    String uri = "/openapi/service/rest/KorService/searchFestival?";


//    @FormUrlEncoded
    @GET(uri)
    Call<Response> getTourData(@Query("ServiceKey") String serviceKey,
                                @Query("areaCode") String areaCode,
                                @Query("numOfRows") String numOfRows,
                                @Query("pageNo") String pageNo,
                                @Query("MobileOS") String MobileOS,
                                @Query("MobileApp") String MobileApp,
                                @Query("_type") String type);
}