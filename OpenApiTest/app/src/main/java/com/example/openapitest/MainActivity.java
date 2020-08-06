package com.example.openapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> dataList = new ArrayList<>();
    Retrofit retrofit;
    ThemeService themeService;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.editText);
        //https://openapi.naver.com/v1/search/news.json?query=%EC%A3%BC%EC%8B%9D&display=10&start=1&sort=date&X-Naver-Client-Id:%20{DKj6Z8B2QEbpGhz3aBNM}&X-Naver-Client-Secret:%20{bCPpzWaaio}
        //final String BASE_URL = "http://www.kobis.or.kr";
        //final String BASE_URL = "https://openapi.naver.com";
        String BASE_URL = "http://api.visitkorea.or.kr";
        //final String BASE_URL ="http://api.visitkorea.or.kr/openapi/service/rest/KorService";
        Retrofit retrofit;
        BoxOfficeService boxOfficeService;

        String API_KEY = "430156241533f1d058c603178cc3ca0e";
        String query = "사랑";
        String display = "10";
        //String type = "movie.json";
        String start = "1";
        String sort = "1";
        String clientId = "DKj6Z8B2QEbpGhz3aBNM";
        String clientSecret = "bCPpzWaaio";
        List<WeeklyBoxOfficeList> weeklyBoxOfficeLists = new ArrayList<>();

        String serviceKey = "OEZDFxQGYkA8crUzSlj51nwQQb9Jh78Y5UWvaW5gXccZ5t2ttRXNjcdXjJJ8FsHlriUWu%2B%2FVhFfuI32FbuMhTA%3D%3D";
        //URLEncoder.encode(serviceKey,"UTP-8");
        int areaCode = 1;
        int numOfRows = 2;
        int pageNo = 1;
        String MobileOS = "AND";
        String MobileApp = "StampInSeoul2";
        String type = "json";
        mGson = new GsonBuilder().setLenient().create();
        String url = "http://api.visitkorefa.or.kr";
        //Retrofit 객체생성
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    /*addConverterFactory(GsonConverterFactory.create())은
    Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        ThemeService themeService = retrofit.create(ThemeService.class);
        ///boxOfficeService.getSearch(clientId, clientSecret, type, query).enqueue(new Callback<ResponseBody>() {
        themeService.getTourData(serviceKey, areaCode, numOfRows, pageNo, MobileOS, MobileApp, type).enqueue(new Callback<ThemeData>() {
            @Override
            public void onResponse(Call<ThemeData> call, Response<ThemeData> response) {
                if (response.isSuccessful()) {
                    Log.d("retro", "성공");
                    editText.setText(response.body().getResponse().getHeader().getResultMsg());

                } else {
                    Log.d("retro", "실패");
                }
            }

            @Override
            public void onFailure(Call<ThemeData> call, Throwable t) {

            }
        });

    }
}
