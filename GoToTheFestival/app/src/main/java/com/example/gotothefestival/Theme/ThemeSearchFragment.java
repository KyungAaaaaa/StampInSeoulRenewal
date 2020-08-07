package com.example.gotothefestival.Theme;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.gotothefestival.MainActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;


public class ThemeSearchFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private LinearLayoutManager layoutManager;

    RequestQueue queue;
    ArrayList<ThemeData> list;
    JsonObject jsonObject;
    RequestQueue requestQueue;
    com.example.gotothefestival.Theme.ThemeAdapter adapter;
    com.example.gotothefestival.Theme.ThemeActivity themeActivity;
    static final String APP_NAME = "Apptest";
    ProgressDialog pDialog;
    Bundle bundle;

    public static ThemeSearchFragment newInstance() {
        ThemeSearchFragment themeSearchFragment = new ThemeSearchFragment();
        return themeSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeActivity = (com.example.gotothefestival.Theme.ThemeActivity) getActivity();

        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        themeRecyclerView.setLayoutManager(layoutManager);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        list = new ArrayList<>();



        ThemeSearchFragment.AsyncTaskClassMain async = new ThemeSearchFragment.AsyncTaskClassMain();
        async.execute();
        adapter = new com.example.gotothefestival.Theme.ThemeAdapter(list,getView());
        themeRecyclerView.setAdapter(adapter);

        return rootView;
    }

    class AsyncTaskClassMain extends android.os.AsyncTask<Integer, Long, String> {

        // 일반쓰레드 돌리기 전 메인쓰레드에서 보여줄 화면처리
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayLoader();
        }

        // 일반쓰레드에서 돌릴 네트워크 작업
        @Override
        protected String doInBackground(Integer... integers) {
            try {
                bundle = themeActivity.getThemeSearchFragment().getArguments();
                requestLotto(bundle.getString("keyword"));
            } catch (NullPointerException e) {

            }
            // publishProgress()를 호출하면 onProgressUpdate가 실행되고 메인쓰레드에서 UI 처리를 한다
            // publishProgress();
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
            pDialog.cancel();
        }

    } // end of AsyncTaskClassMain


    public void requestLotto(String word) {
        String keyword = null;
        try {
            keyword = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?" +
                "ServiceKey=" + MainActivity.KEY + "&keyword=" + keyword +
                "&contentTypeId=15&listYN=Y&arrange=P&numOfRows=20&pageNo=1" +
                "&MobileOS=AND&MobileApp=" + APP_NAME +
                "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject parse_response = (JSONObject) response.get("response");
                            JSONObject parse_body = (JSONObject) parse_response.get("body");
                            JSONObject parse_items = (JSONObject) parse_body.get("items");
                            JSONArray parse_itemlist = (JSONArray) parse_items.get("item");
                            list.removeAll(list);
                            for (int i = 0; i < parse_itemlist.length(); i++) {
                                JSONObject imsi = (JSONObject) parse_itemlist.get(i);
                                ThemeData themeData = new ThemeData();
                                themeData.setFirstImage(imsi.getString("firstimage"));
                                themeData.setTitle(imsi.getString("title"));
                                themeData.setAddr(imsi.getString("addr1"));
                                themeData.setMapX(imsi.getDouble("mapx"));
                                themeData.setMapY(imsi.getDouble("mapy"));
                                themeData.setContentsID(Integer.valueOf(imsi.getString("contentid")));
                                list.add(themeData);
                            }
                            themeRecyclerView.setAdapter(adapter);
                        } catch (ClassCastException e) {
                            Log.d("JSON 오류", e.getMessage());
                            Snackbar.make(getView(), "검색결과가 없습니다.", Snackbar.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    JSONObject jsonObject = new JSONObject(utf8String);
                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        queue.add(jsObjRequest);

    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("잠시만 기다려 주세요..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


}
