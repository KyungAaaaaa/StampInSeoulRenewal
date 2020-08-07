package com.example.gotothefestival.Theme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.gotothefestival.BottomMenuActivity;
import com.example.gotothefestival.MainActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.example.gotothefestival.Theme.ThemeSearchFragment;
import com.example.gotothefestival.Theme.ThemeViewPager;
import com.example.gotothefestival.Theme.ThemeViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ThemeViewPager viewPager;
    private EditText edtSearch;
    private ImageButton ibSearch;
    private FloatingActionButton fab, fab1, fab2;
    private ThemeSearchFragment themeSearchFragment;
    private Animation fab_open, fab_close;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private boolean isFabOpen;
    private boolean searchFlag;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        edtSearch = findViewById(R.id.edtSearch);
        ibSearch = findViewById(R.id.ibSearch);
        fragmentStatePagerAdapter = new ThemeViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentStatePagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(Color.LTGRAY, Color.BLACK);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#73CED8"));
        CircleImageView civImage = findViewById(R.id.civImage);

        Intent intent = getIntent();
        String strProfile = intent.getStringExtra("profile");
        String strNickName = intent.getStringExtra("name");

        Context context = getApplicationContext();
        CharSequence txt = "메시지입니다";
        int time = Toast.LENGTH_SHORT;// 아니면 Long으로
        Toast toast = Toast.makeText(context, txt, time);
        Snackbar.make(getWindow().getDecorView().getRootView(), strNickName + "님 환영합니다!", Snackbar.LENGTH_LONG).show();
        LayoutInflater inflater = getLayoutInflater();
//xml 파일과 레이아웃 정의
        View view = inflater.inflate(R.layout.custom_toastview, (ViewGroup) findViewById(R.id.containers));

        TextView txtView = view.findViewById(R.id.txtId);

        txtView.setText(strNickName);//텍스트뷰에 이름 보여주기
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(strProfile);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
            civImage.setImageBitmap(bitmap);
            civImage.setBorderColor(Color.BLACK);
            civImage.setBorderWidth(1);
        } catch (InterruptedException e) {
        }
        toast.setView(view);
        //toast.show();//토스트 메시지 보여주기

        // == 플로팅 버튼, 드로어
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        ibSearch.setOnClickListener(this);

    }

    public ThemeSearchFragment getThemeSearchFragment() {
        return themeSearchFragment;
    }

    public void setThemeSearchFragment(ThemeSearchFragment themeSearchFragment) {
        this.themeSearchFragment = themeSearchFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(ThemeActivity.this, BottomMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.fab2:
                break;
            case R.id.ibSearch:
                if (searchFlag) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "뒤로가기 버튼을 누른후 다시 시도하세요", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String word = edtSearch.getText().toString().trim();
                if (word.length() > 1) {
                    searchData();
                    themeSearchFragment = new ThemeSearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", word);
                    themeSearchFragment.setArguments(bundle);
                    searchFlag = true;
                    viewPager.setCurrentItem(8);
                    viewPager.setPagingDisabled();
                } else {
                    Toast.makeText(getApplicationContext(), "두 글자 이상 입력해 주세요", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void searchData() {
        if (!searchFlag) {
            tabLayout.setVisibility(View.GONE);
            //edtSearch.setEnabled(false);
            //ibSearch.setEnabled(false);


        } else {
            tabLayout.setVisibility(View.VISIBLE);
        }
    }

    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    public ArrayList<ThemeData> requestData(int areaCode) {
        ArrayList<ThemeData> dataArrayList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?" +
                "ServiceKey=" + MainActivity.KEY +
                "&areaCode=" + areaCode + "&contentTypeId=15&listYN=Y&arrange=P&numOfRows=20&pageNo=1" +
                "&MobileOS=AND&MobileApp=" + MainActivity.APP_NAME +
                "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
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
                                dataArrayList.add(themeData);
                            }
                            //recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.d("JSON 오류", e.getMessage());
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
        return dataArrayList;
    }

    public ProgressDialog displayLoader() {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("잠시만 기다려 주세요..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        return pDialog;
    }

    private long backKeyPressedTime = 0;//

    // 뒤로가기 버튼 이벤트
    @Override
    public void onBackPressed() {
        if (searchFlag) {
            searchFlag = false;
            viewPager.setPagingEnabled();
            //ibSearch.setEnabled(true);
            //edtSearch.setEnabled(true);
            viewPager.setCurrentItem(0);
            tabLayout.setVisibility(View.VISIBLE);
            //themeSearchFragment = null;
            return;
        }
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) finish();
    }

}
