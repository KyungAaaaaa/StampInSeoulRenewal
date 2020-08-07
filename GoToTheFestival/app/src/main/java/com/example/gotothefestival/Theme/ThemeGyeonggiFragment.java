package com.example.gotothefestival.Theme;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;


public class ThemeGyeonggiFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private ArrayList<ThemeData> list;
    private com.example.gotothefestival.Theme.ThemeAdapter adapter;
    private ProgressDialog lodingDialog;
    private com.example.gotothefestival.Theme.ThemeActivity themeActivity;

    public static ThemeGyeonggiFragment newInstance() {
        ThemeGyeonggiFragment themeGyeonggiFragment = new ThemeGyeonggiFragment();
        return themeGyeonggiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeActivity = (com.example.gotothefestival.Theme.ThemeActivity) getActivity();
        lodingDialog = themeActivity.displayLoader();
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        themeRecyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        ThemeGyeonggiFragment.AsyncTaskClassMain async = new ThemeGyeonggiFragment.AsyncTaskClassMain();
        async.execute();
        return rootView;
    }

    class AsyncTaskClassMain extends android.os.AsyncTask<Integer, Long, String> {
        // 일반쓰레드 돌리기 전 메인쓰레드에서 보여줄 화면처리
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lodingDialog.show();
        }

        // 일반쓰레드에서 돌릴 네트워크 작업
        @Override
        protected String doInBackground(Integer... integers) {
            list = themeActivity.requestData(31);
            adapter = new com.example.gotothefestival.Theme.ThemeAdapter(list,getView());
            // publishProgress()를 호출하면 onProgressUpdate가 실행되고 메인쓰레드에서 UI 처리를 한다
            return "작업 종료";
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            // 일반 쓰레드가 도는 도중에 메인 쓰레드에서 처리할 UI작업
            super.onProgressUpdate(values);
        }

        // doInBackground 메서드가 완료되면 메인 쓰레드가 얘를 호출한다(doInBackground가 반환한 값을 매개변수로 받음)
        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
            themeRecyclerView.setAdapter(adapter);
            lodingDialog.cancel();
        }
    } // end of AsyncTaskClassMain


}
