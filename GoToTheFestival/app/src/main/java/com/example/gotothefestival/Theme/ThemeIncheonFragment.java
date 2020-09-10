package com.example.gotothefestival.Theme;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Login.MainActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemeIncheonFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private ProgressDialog dialog;
    ///////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);
        dialog=displayLoader();
        dialog.show();
        dataLoad();
        return rootView;
    }

    private ProgressDialog displayLoader() {
        ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("잠시만 기다려 주세요..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        return pDialog;
    }

    private void dataLoad(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TourApiService tourApiService = retrofit.create(TourApiService.class);

        HashMap<String, String> query = new HashMap<>();
        query.put("ServiceKey", MainActivity.KEY);
        query.put("areaCode", "2");
        query.put("contentTypeId", "15");
        query.put("listYN", "Y");
        query.put("arrange", "P");
        query.put("numOfRows", "20");
        query.put("pageNo", "1");
        query.put("MobileOS", "AND");
        query.put("MobileApp", MainActivity.APP_NAME);
        query.put("_type", "json");

        tourApiService.getData(query).enqueue(new Callback<ThemeData>() {
            @Override
            public void onResponse(Call<ThemeData> call, retrofit2.Response<ThemeData> response) {
                if (response.isSuccessful()) {
                    Log.d("retro", 1 + "");
                    ThemeData result = response.body();

                    ThemeData.Response response1 = result.getResponse();
                    ThemeData.Body body = response1.getBody();
                    ThemeData.Items items = body.getItems();
                    List<ThemeData.Item> item = items.getItem();

                    //////////////////////////////////////////////////
                    ArrayList<ThemeData.Item> list = new ArrayList<>(item);
                    ThemeAdapter adapter = new com.example.gotothefestival.Theme.ThemeAdapter(list, getView());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    themeRecyclerView.setLayoutManager(layoutManager);
                    themeRecyclerView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    Log.d("retro", 2 + "Error");
                }
            }


            @Override
            public void onFailure(Call<ThemeData> call, Throwable t) {
                Log.d("retro", t.getMessage());
            }
        });
    }

}
