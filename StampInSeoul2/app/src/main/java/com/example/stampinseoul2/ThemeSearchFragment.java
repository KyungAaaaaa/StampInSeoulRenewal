package com.example.stampinseoul2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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
    ThemeAdapter adapter;
    ThemeActivity themeActivity;
    static final String APP_NAME = "Apptest";
    ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeActivity = (ThemeActivity) getActivity();
        //Bundle bundle=getArguments();
        Bundle bundle = themeActivity.getThemeSearchFragment().getArguments();
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        themeRecyclerView.setLayoutManager(layoutManager);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        list = new ArrayList<>();
        requestLotto(bundle.getString("keyword"));
        adapter = new ThemeAdapter(list);
        themeRecyclerView.setAdapter(adapter);

        return rootView;
    }


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
                "&areaCode=1&numOfRows=20&pageNo=1" +
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
                            e.printStackTrace();
                            View viewDialog = View.inflate(getActivity(), R.layout.dialog_search_message, null);

                            Button btnExit = viewDialog.findViewById(R.id.btnExit);

                            final Dialog noSearchDlg = new Dialog(getActivity());

                            noSearchDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            noSearchDlg.setContentView(viewDialog);
                            noSearchDlg.show();

                            btnExit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    noSearchDlg.dismiss();
                                }
                            });

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
