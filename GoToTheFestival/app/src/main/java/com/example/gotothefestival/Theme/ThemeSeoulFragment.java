package com.example.gotothefestival.Theme;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Login.MainActivity;
import com.example.gotothefestival.Model.ThemeData2;
import com.example.gotothefestival.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemeSeoulFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private ProgressDialog lodingDialog;

    ///////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
//        lodingDialog = themeActivity.displayLoader();
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);
        dataLoad();
        return rootView;
    }

    private void dataLoad(){
        //////////////////////////////
        //Retrofit 객체생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드 */
        TourApiService tourApiService = retrofit.create(TourApiService.class);

        HashMap<String, String> query = new HashMap<>();
        query.put("ServiceKey", MainActivity.KEY);
        query.put("areaCode", "1");
        query.put("contentTypeId", "15");
        query.put("listYN", "Y");
        query.put("arrange", "P");
        query.put("numOfRows", "20");
        query.put("pageNo", "1");
        query.put("MobileOS", "AND");
        query.put("MobileApp", MainActivity.APP_NAME);
        query.put("_type", "json");
        tourApiService.getData(query).enqueue(new Callback<ThemeData2>() {
            @Override
            public void onResponse(Call<ThemeData2> call, retrofit2.Response<ThemeData2> response) {
                if (response.isSuccessful()) {
                    Log.d("retro", 1 + "");
                    ThemeData2 result = response.body();

                    ThemeData2.Response response1 = result.getResponse();
                    ThemeData2.Body body = response1.getBody();
                    ThemeData2.Items items = body.getItems();
                    List<ThemeData2.Item> item = items.getItem();

                    //////////////////////////////////////////////////
                    ArrayList<ThemeData2.Item> list = new ArrayList<>(item);
                    ThemeAdapter adapter = new com.example.gotothefestival.Theme.ThemeAdapter(list, getView());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    themeRecyclerView.setLayoutManager(layoutManager);
                    themeRecyclerView.setAdapter(adapter);
                } else {
                    Log.d("retro", 2 + "Error");
                }
            }


            @Override
            public void onFailure(Call<ThemeData2> call, Throwable t) {
                Log.d("retro", t.getMessage());
            }
        });
    }
//    public static ThemeSeoulFragment newInstance() {
//        ThemeSeoulFragment themeSeoulFragment = new ThemeSeoulFragment();
//        return themeSeoulFragment;
//    }
}
