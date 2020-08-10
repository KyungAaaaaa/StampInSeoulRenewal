package com.example.gotothefestival.Theme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.bumptech.glide.Glide;
import com.example.gotothefestival.BottomMenu.BottomMenuActivity;
import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.Login.MainActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.example.gotothefestival.UserDBHelper;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener, ThemeViewPager.OnPageChangeListener, TabLayout.BaseOnTabSelectedListener {
    private EditText edtSearch;
    private ImageButton ibSearch;
    private CircleImageView civImage;
    private FloatingActionButton fab, fab1, fab2;
    private TabLayout tabLayout;
    private ThemeViewPager viewPager;

    private ThemeViewPagerAdapter fragmentStatePagerAdapter;
    private ThemeSearchFragment themeSearchFragment;

    private Animation fab_open, fab_close;
    private boolean isDragged;
    private boolean isFabOpen;
    private boolean searchFlag;
    ArrayList<ThemeData> checkedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        findViewByIdFunc(); //UI 처리 메소드
        screenSetting();    //화면 셋팅 메소드

    }

    //화면 셋팅 메소드
    private void screenSetting() {
        //viewPager와 탭레이아웃 셋팅
        fragmentStatePagerAdapter = new ThemeViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentStatePagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(Color.LTGRAY, Color.BLACK);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#73CED8"));

        //로그인에서 넘겨준 인텐트를 가져온다
        Intent intent = getIntent();
        String strProfile = intent.getStringExtra("profile");
        String strNickName = intent.getStringExtra("name");


        //이미지 처리 라이브러리를 이용해 프로필 사진URL을 이용해 뷰에 적용
        civImage.setBorderColor(Color.BLACK);
        civImage.setBorderWidth(1);
        Glide.with(getApplicationContext()).load(strProfile).into(civImage);

        Snackbar.make(getWindow().getDecorView().getRootView(), strNickName + "님 환영합니다!", Snackbar.LENGTH_LONG).show();
    }

    private void findViewByIdFunc() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        edtSearch = findViewById(R.id.edtSearch);
        ibSearch = findViewById(R.id.ibSearch);
        civImage = findViewById(R.id.civImage);

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
                anim();
                likeListSetting(view);


                break;
            case R.id.ibSearch:
                if (searchFlag) {
                    Snackbar.make(view, "뒤로가기 버튼을 누른후 다시 시도하세요", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String word = edtSearch.getText().toString().trim();
                //검색어가 두글자 이상이면
                if (word.length() >= 2) {
                    if (!searchFlag) tabLayout.setVisibility(View.GONE);
                    else tabLayout.setVisibility(View.VISIBLE);

                    themeSearchFragment = new ThemeSearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", word);
                    themeSearchFragment.setArguments(bundle);
                    searchFlag = true;
                    viewPager.setCurrentItem(8);
                    viewPager.setPagingDisabled();
                } else {
                    Snackbar.make(view,"두 글자 이상 입력해 주세요",Snackbar.LENGTH_SHORT).show();
                }
                break;
        }

    }

    //좋아요 리스트 확인& 삭제 다이얼로그 메소드
    private void likeListSetting(View view) {
        UserDBHelper dbHelper = UserDBHelper.getInstance(getApplicationContext());
        ArrayList<String> likeList = dbHelper.likeLoad(LoginActivity.userData);
        checkedList.clear();

        View viewDialog = view.inflate(view.getContext(), R.layout.dialog_like_list, null);
        ListView likeListView = viewDialog.findViewById(R.id.likeListView);
        Button btnLikeDelete = viewDialog.findViewById(R.id.btnLikeDelete);
        Button btnExit = viewDialog.findViewById(R.id.btnExit);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_check_box_color, likeList);
        checkedList = new ArrayList<>();
        likeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        likeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Dialog dialog = new Dialog(viewDialog.getContext());
        likeListView.setOnItemClickListener((adapterView, view1, i, l) -> {
            SparseBooleanArray booleans = likeListView.getCheckedItemPositions();
            if (booleans.get(i)) checkedList.add(new ThemeData(likeList.get(i)));
        });

        //삭제 버튼을 누르면 선택된 타이틀의 축제정보를 DB에 좋아요 값을 수정하고 어댑터를 다시 로드
        btnLikeDelete.setOnClickListener(view1 -> {
            for (int j = 0; j < checkedList.size(); j++) {
                dbHelper.likeDelete(LoginActivity.userData, checkedList.get(j));
            }
            Snackbar.make(view1, "수정 완료", Snackbar.LENGTH_LONG).show();
            dialog.dismiss();
            viewPager.setAdapter(fragmentStatePagerAdapter);
        });

        //다이얼로그에 레이아웃 설정
        dialog.setContentView(viewDialog);
        dialog.show();

        // 확인버튼 누르면 다이얼로그 종료
        btnExit.setOnClickListener((v) -> dialog.dismiss());

    }


    //플로팅버튼 애니메이션
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

    //관광공사 api 받아오기
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ThemeSearchFragment getThemeSearchFragment() {
        return themeSearchFragment;
    }

    public void setThemeSearchFragment(ThemeSearchFragment themeSearchFragment) {
        this.themeSearchFragment = themeSearchFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentStatePagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (!isDragged) {
            viewPager.setCurrentItem(tab.getPosition());
        }
        isDragged = false;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if (i == ViewPager.SCROLL_STATE_DRAGGING)
            isDragged = true;
    }

}
