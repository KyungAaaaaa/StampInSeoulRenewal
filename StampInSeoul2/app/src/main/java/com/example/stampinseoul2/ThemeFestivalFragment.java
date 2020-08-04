package com.example.stampinseoul2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThemeFestivalFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private LinearLayoutManager layoutManager;
    RequestQueue queue;
    ArrayList<ThemeData> list;
    JsonObject jsonObject;
    RequestQueue requestQueue;
    //ThemeAdapter adapter;
    /////////////////////////////////////////////
    ArrayList<Item> dataList = new ArrayList<>();
    ThemeAdapter adapter;
    //Retrofit retrofit;
    //ThemeService themeService;
    private Gson mGson;

    public static ThemeFestivalFragment newInstance() {
        ThemeFestivalFragment themeFestivalFragment = new ThemeFestivalFragment();
        return themeFestivalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);

        layoutManager = new LinearLayoutManager(getActivity());
        themeRecyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        list = (requestLotto());
        // 리사이클러뷰에 ThemeAdapter 객체 지정.
        adapter = new ThemeAdapter(list);
        themeRecyclerView.setAdapter(adapter);
        ////////////////////////////////////////////////////////////
//        setRetrofitInit();
//        String url = "http://api.visitkorea.or.kr";
//
//        //Retrofit 객체생성
//        retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    /*addConverterFactory(GsonConverterFactory.create())은
//    Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */
//
//        String serviceKey = "OEZDFxQGYkA8crUzSlj51nwQQb9Jh78Y5UWvaW5gXccZ5t2ttRXNjcdXjJJ8FsHlriUWu%2B%2FVhFfuI32FbuMhTA%3D%3D";
//        int areaCode = 1;
//        int numOfRows = 2;
//        int pageNo = 1;
//        String MobileOS = "AND";
//        String MobileApp = "StampInSeoul2";
//        String type = "json";
//
//        themeService = retrofit.create(ThemeService.class);
//        themeService.getTourData(serviceKey, areaCode, numOfRows, pageNo, MobileOS, MobileApp, type).enqueue(new Callback<ThemeData>() {
//            @Override
//            public void onResponse(Call<ThemeData> call, retrofit2.Response<ThemeData> response) {
//                if (response.isSuccessful()) {
//                    Log.d("retro", 1 + "");
////                    Result result = response.body();
////
////                    ThemeService boxOfficeResult = result.getBoxOfficeResult();
////                    List<WeeklyBoxOfficeList> weeklyBoxOfficeListLIst2 = boxOfficeResult.getWeeklyBoxOfficeList();
////                    for (WeeklyBoxOfficeList weeklyBoxOffice : weeklyBoxOfficeListLIst2){
////                        weeklyBoxOfficeLists.add(weeklyBoxOffice);
////                    }
//                    ThemeData data = response.body();
//                    //mGson.fromJson(data,Response.class);
//                    final Response data1 = data.getResponse();
//                    Body data2 = null;
//                    Body body = data1.getBody();
//                    Items data3 = body.getItems();
//                    List<Item> data4 = data3.getItem();
//
//                    dataList = new ArrayList<>(data4);
//                    adapter = new ThemeAdapter(dataList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    themeRecyclerView.setLayoutManager(linearLayoutManager);
//                    themeRecyclerView.setAdapter(adapter);
//                } else {
//                    Log.d("retro", 2 + "Error");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ThemeData> call, Throwable t) {
//
//            }
//        });
        return rootView;
    }

    public ArrayList<ThemeData> requestLotto() {
        // ThemeData data = new ThemeData("");
        ArrayList<ThemeData> dataArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?" +
                "ServiceKey=OEZDFxQGYkA8crUzSlj51nwQQb9Jh78Y5UWvaW5gXccZ5t2ttRXNjcdXjJJ8FsHlriUWu%2B%2FVhFfuI32FbuMhTA%3D%3D" +
                "&areaCode=1&numOfRows=20&pageNo=1" +
                "&MobileOS=AND&MobileApp=StampInSeoul2" +
                "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject parse_response = (JSONObject) response.get("response");
                            JSONObject parse_body = (JSONObject) parse_response.get("body");
                            JSONObject parse_items = (JSONObject) parse_body.get("items");
                            JSONArray parse_itemlist = (JSONArray) parse_items.get("item");
                            for (int i = 0; i < parse_itemlist.length(); i++) {

                                JSONObject imsi = (JSONObject) parse_itemlist.get(i);
                                ThemeData themeData = new ThemeData();
                                themeData.setFirstImage(imsi.getString("firstimage"));
                                themeData.setTitle(imsi.getString("title"));
                                themeData.setAddr(imsi.getString("addr1"));
                                themeData.setMapX(imsi.getDouble("mapx"));
                                themeData.setMapY(imsi.getDouble("mapy"));
                                themeData.setContentsID(Integer.valueOf(imsi.getString("contentid")));
                                //list.add(themeData);
                                dataArrayList.add(themeData);
                            }
                            themeRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.d("JSON 오류", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsObjRequest);

        return dataArrayList;
    }

//
//    public ArrayList<ThemeData> requestLotto() {
//        // ThemeData data = new ThemeData("");
//        ArrayList<ThemeData> dataArrayList = new ArrayList<>();
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//
//                            JSONObject parse_response = (JSONObject) response.get("response");
//                            JSONObject parse_body = (JSONObject) parse_response.get("body");
//                            JSONObject parse_items = (JSONObject) parse_body.get("items");
//                            JSONArray parse_itemlist = (JSONArray) parse_items.get("item");
//                            for (int i = 0; i < parse_itemlist.length(); i++) {
//
//                                JSONObject imsi = (JSONObject) parse_itemlist.get(i);
//                                ThemeData themeData = new ThemeData();
//                                themeData.setFirstImage(imsi.getString("firstimage"));
//                                themeData.setTitle(imsi.getString("title"));
//                                themeData.setAddr(imsi.getString("addr1"));
//                                themeData.setMapX(imsi.getDouble("mapx"));
//                                themeData.setMapY(imsi.getDouble("mapy"));
//                                themeData.setContentsID(Integer.valueOf(imsi.getString("contentid")));
//                                //list.add(themeData);
//                                dataArrayList.add(themeData);
//                            }
//                            themeRecyclerView.setAdapter(adapter);
//                        } catch (JSONException e) {
//                            Log.d("JSON 오류", e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                });
//        queue.add(jsObjRequest);
//
//        return dataArrayList;
//    }

}
