package com.example.gotothefestival.Theme;

import android.app.Dialog;
import android.content.Context;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.Login.MainActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.example.gotothefestival.UserDBHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    Context context;
    View view;
    ArrayList<ThemeData.Item> list;
    ThemeData data = new ThemeData();
    ThemeData.Item detailData = data.new Item();
    UserDBHelper userDBHelper = UserDBHelper.getInstance(context);
    static final String appName = "Zella";


    /////////////////////////////////////////////////////////////


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        TextView tvAddr;
        ImageView imgView;
        ImageView imagebtn;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            txtView = itemView.findViewById(R.id.txtView);
            tvAddr = itemView.findViewById(R.id.tvAddr);
            imgView = itemView.findViewById(R.id.imgView);
            imagebtn = itemView.findViewById(R.id.imagebtn);


            itemView.setOnClickListener((view) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // notifyDataSetChanged()에 의해 리사이클러뷰가 아이템뷰를 갱신하는 과정에서,
                    // 뷰홀더가 참조하는 아이템이 어댑터에서 삭제되면 getAdapterPosition() 메서드는 NO_POSITION을 리턴하기 때문입니다.
                    ThemeAdapter.AsyncTaskClassSub asyncSub = new ThemeAdapter.AsyncTaskClassSub();
                    asyncSub.execute(position);

                }
            });


            imagebtn.setOnClickListener(v -> {
                int position = getAdapterPosition();
                ThemeData.Item data = list.get(position);
                if (position != RecyclerView.NO_POSITION) {
                    try {
                        if (data.isHart()) {
                            // 하트 선택 해제
                            imagebtn.setSelected(false);
                            data.setHart(false);
                            Snackbar.make(v, "좋아요 목록에서 삭제 되었습니다!", BaseTransientBottomBar.LENGTH_SHORT).show();
                            userDBHelper.likeDelete(LoginActivity.userData, data);
                        } else {
                            // 하트 선택
                            imagebtn.setSelected(true);
                            data.setHart(true);
                            Snackbar.make(v, "좋아요 목록에 추가 되었습니다!", BaseTransientBottomBar.LENGTH_SHORT).show();
                            userDBHelper.likeInsert(LoginActivity.userData, data);
                        }
                    } catch (SQLException e) {

                    }
                }
            });
        }


    }

    // 어탭터 생성자. 매개변수로 list를 가져온다
    public ThemeAdapter(ArrayList<ThemeData.Item> list, View view) {
        this.list = list;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 뷰홀더(ViewHolder)는 화면에 표시될 아이템 뷰를 저장하는 객체입니다.
        // 미리 생성된 뷰홀더 객체가 있는 경우에는 새로 생성하지 않고 이미 만들어져 있는 뷰홀더를 재활용
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_theme, parent, false);
        ThemeAdapter.ViewHolder themeViewHolder = new ThemeAdapter.ViewHolder(view);
        return themeViewHolder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThemeData.Item data = list.get(position);
        holder.txtView.setText(data.getTitle());
        Glide.with(context).load(data.getFirstimage()).into(holder.imgView);
        holder.itemView.setTag(position);
        holder.tvAddr.setText(data.getAddr1());
        ArrayList<String> likeList = userDBHelper.likeLoad(LoginActivity.userData);
        int i = 0;
        if (likeList.size() != 0) {
            while (i < likeList.size()) {
                if (likeList.get(i).equals(data.getTitle())) {
                    data.setHart(true);
                }
                i++;
            }

        }
        //db에서 좋아요한 title 가져오기
        if (data.isHart()) {
            holder.imagebtn.setSelected(true);
        } else {
            holder.imagebtn.setSelected(false);
        }

    }

    //전체 데이터의 갯수 리턴
    @Override
    public int getItemCount() {
        return list.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 관광 정보(리사이클러뷰) 를 선택했을때 상세 데이터 읽어오는 메소드

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ThemeData.Item getData(int contentID) {

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://api.visitkorea.or.kr/openapi/service/"
                + "rest/KorService/detailCommon?ServiceKey=" + MainActivity.KEY
                + "&contentId=" + contentID
                + "&firstImageYN=Y&mapinfoYN=Y&addrinfoYN=Y&defaultYN=Y&overviewYN=Y"
                + "&pageNo=1&MobileOS=AND&MobileApp="
                + appName + "&_type=json";
        ThemeData.Item detailThemeData = data.new Item();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, (response) -> {
                    try {
                        JSONObject parse_response = (JSONObject) response.get("response");
                        JSONObject parse_body = (JSONObject) parse_response.get("body");
                        JSONObject parse_items = (JSONObject) parse_body.get("items");
                        JSONObject parse_itemlist = (JSONObject) parse_items.get("item");

                        detailThemeData.setFirstimage(parse_itemlist.getString("firstimage"));
                        detailThemeData.setTitle(parse_itemlist.getString("title"));
                        detailThemeData.setAddr1(parse_itemlist.getString("addr1"));
                        detailThemeData.setOverview(parse_itemlist.getString("overview"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, (error) -> Log.d("detailError", error.getMessage())) {
            //response를 UTF8로 변경해주는 소스코드
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    JSONObject jsonObject = new JSONObject(utf8String);
                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        queue.add(jsObjRequest);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return detailThemeData;
    }

    //AsynTask란 클래스를 상속하여 클래스를 만들면 해당 클래스안에 스레드를 위한 동작코드와 UI 접근 코드를 한꺼번에 넣을 수 있습니다.
    //새로운 TASK정의 (AsyncTask)
    // < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)
    class AsyncTaskClassSub extends android.os.AsyncTask<Integer, ThemeData.Item, ThemeData.Item> {
        //초기화 단계에서 사용한다. 초기화관련 코드를 작성했다.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //스레드의 백그라운드 작업 구현
        //여기서 매개변수 Intger ... values란 values란 이름의 Integer배열이라 생각하면된다.
        //배열이라 여러개를 받을 수 도 있다. ex) excute(100, 10, 20, 30); 이런식으로 전달 받으면 된다.
        @Override
        protected ThemeData.Item doInBackground(Integer... integers) {
            int position = integers[0];
            ThemeData.Item myThemeData1 = list.get(position);
            ThemeData.Item themeData = getData(myThemeData1.getContentid());
            return themeData;
        }

        //UI작업 관련 작업 (백그라운드 실행중 이 메소드를 통해 UI작업을 할 수 있다)
        //publishProgress(value)의 value를 값으로 받는다.values는 배열이라 여러개 받기가능
        @Override
        protected void onProgressUpdate(ThemeData.Item... values) {
            super.onProgressUpdate(values);
        }

        //이 Task에서(즉 이 스레드에서) 수행되던 작업이 종료되었을 때 호출됨
        @Override
        protected void onPostExecute(ThemeData.Item themeData) {
            super.onPostExecute(themeData);

            View viewDialog = View.inflate(context, R.layout.dialog_info, null);

            TextView txt_Detail_title = viewDialog.findViewById(R.id.txt_Detail_title);
            TextView txt_Detail_addr = viewDialog.findViewById(R.id.txt_Detail_addr);
            TextView txt_Detail_info = viewDialog.findViewById(R.id.txt_Detail_info);
            Button btnExit = viewDialog.findViewById(R.id.btnExit);
            ImageView img_Datail_info = viewDialog.findViewById(R.id.img_Datail_info);

            txt_Detail_title.setText(themeData.getTitle());
            txt_Detail_addr.setText(themeData.getAddr1());
            txt_Detail_info.setText(themeData.getOverview());
            Glide.with(context).load(themeData.getFirstimage()).override(500, 300).into(img_Datail_info);

            Dialog dialog = new Dialog(viewDialog.getContext());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(viewDialog);
            dialog.show();

            btnExit.setOnClickListener((view) -> dialog.dismiss());

        }
    }


}

