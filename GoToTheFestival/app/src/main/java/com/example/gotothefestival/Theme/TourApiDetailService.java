package com.example.gotothefestival.Theme;

import com.example.gotothefestival.Login.MainActivity;
import com.example.gotothefestival.Model.ThemeData2;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TourApiDetailService {
    @GET("/openapi/service/rest/KorService/detailCommon?")
    Call<ThemeData2> getDetailData(@QueryMap(encoded = true) Map<String, String> query);
}
