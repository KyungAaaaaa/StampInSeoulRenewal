package com.example.openapitest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoxOfficeService {
    //@GET("/openapi/service/rest/KorService/searchFestival?")
    @GET("/openapi/service/rest/KorService/searchFestival?&")
    Call<ResponseBody> getSearch(
            @Query("ServiceKey") String serviceKey,
            @Query("areaCode") String areaCode,
            @Query("numOfRows") String numOfRows,
            @Query("pageNo") String pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("_type") String type);

//    @GET("search/{type}")
//    Call<ResponseBody> getSearch(@retrofit2.http.Header("X-Naver-Client-Id") String clientId,
//                                 @retrofit2.http.Header("X-Naver-Client-Secret") String clientPw,
//                                 @Path("type") String type,
//                                 @Query("query") String query
//    );


}
