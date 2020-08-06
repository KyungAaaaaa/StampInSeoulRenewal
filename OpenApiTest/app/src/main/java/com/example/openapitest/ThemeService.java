package com.example.openapitest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThemeService {
    String uri = "/openapi/service/rest/KorService/searchFestival?";


    //@FormUrlEncoded
    @GET(uri)
    Call<ThemeData> getTourData(@Query("ServiceKey") String serviceKey,
                                @Query("areaCode") int areaCode,
                                @Query("numOfRows") int numOfRows,
                                @Query("pageNo") int pageNo,
                                @Query("MobileOS") String MobileOS,
                                @Query("MobileApp") String MobileApp,
                                @Query("_type") String type);
}