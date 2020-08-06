package com.example.stampinseoul2;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    private ArrayList<ThemeData> dataArrayList = null;
    //private ArrayList<ThemeData> dataArrayList = null;
    Context context;
    RequestQueue queue;
    View view;
    View viewDialog;
    int i=0;
    final static String TAG = "ThemeActivity";
    ArrayList<ThemeData> list;
    int layout;
    static final String KEY = "OEZDFxQGYkA8crUzSlj51nwQQb9Jh78Y5UWvaW5gXccZ5t2ttRXNjcdXjJJ8FsHlriUWu%2B%2FVhFfuI32FbuMhTA%3D%3D";
    static final String appName = "Zella";
    ThemeData detailThemeData = new ThemeData();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_theme, parent, false);
        ThemeAdapter.ViewHolder themeViewHolder = new ThemeAdapter.ViewHolder(view);
        return themeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThemeData data = list.get(position);
        //ThemeData data = dataArrayList.get(position);
        holder.txtView.setText(data.getTitle());
        Glide.with(context).load(data.getFirstImage()).into(holder.imgView);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = (int) v.getTag();

                ThemeAdapter.AsyncTaskClassSub asyncSub = new ThemeAdapter.AsyncTaskClassSub();
                asyncSub.execute(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;
        public ImageView imgView;
        public ImageView imagebtn;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            txtView = itemView.findViewById(R.id.txtView);
            imgView = itemView.findViewById(R.id.imgView);
            imagebtn = itemView.findViewById(R.id.imagebtn);
        }
    }
      public  ThemeAdapter(ArrayList<ThemeData> list) {
        this.list = list;
    }
    private ThemeData getData(int contentID) {

        queue = Volley.newRequestQueue(context);

        String url = "http://api.visitkorea.or.kr/openapi/service/"
                + "rest/KorService/detailCommon?ServiceKey=" + KEY
                + "&contentId=" + contentID
                + "&firstImageYN=Y&mapinfoYN=Y&addrinfoYN=Y&defaultYN=Y&overviewYN=Y"
                + "&pageNo=1&MobileOS=AND&MobileApp="
                + appName + "&_type=json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject parse_response = (JSONObject) response.get("response");
                            JSONObject parse_body = (JSONObject) parse_response.get("body");
                            JSONObject parse_items = (JSONObject) parse_body.get("items");
                            JSONObject parse_itemlist = (JSONObject) parse_items.get("item");

                            detailThemeData.setFirstImage(parse_itemlist.getString("firstimage"));
                            detailThemeData.setTitle(parse_itemlist.getString("title"));
                            detailThemeData.setAddr(parse_itemlist.getString("addr1"));
                            detailThemeData.setOverView(parse_itemlist.getString("overview"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsObjRequest);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return detailThemeData;
    }

    public ThemeData TourData(int position){

        return list != null ? list.get(position) : null;
    }


    class AsyncTaskClassSub extends android.os.AsyncTask<Integer, ThemeData, ThemeData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ThemeData doInBackground(Integer... integers) {

            int position = integers[0];

            ThemeData myThemeData1 = list.get(position);

            ThemeData themeData = getData(myThemeData1.getContentsID());

            return themeData;
        }

        @Override
        protected void onProgressUpdate(ThemeData... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ThemeData themeData) {
            super.onPostExecute(themeData);

            viewDialog = View.inflate(context, R.layout.dialog_info, null);

            TextView txt_Detail_title = viewDialog.findViewById(R.id.txt_Detail_title);
            TextView txt_Detail_addr = viewDialog.findViewById(R.id.txt_Detail_addr);
            TextView txt_Detail_info = viewDialog.findViewById(R.id.txt_Detail_info);

            Button btnExit = viewDialog.findViewById(R.id.btnExit);

            ImageView img_Datail_info = viewDialog.findViewById(R.id.img_Datail_info);

            txt_Detail_title.setText(themeData.getTitle());
            txt_Detail_addr.setText(themeData.getAddr());
            txt_Detail_info.setText(themeData.getOverView());

            Glide.with(context).load(themeData.getFirstImage()).override(500, 300).into(img_Datail_info);

            final Dialog dialog = new Dialog(viewDialog.getContext());

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.setContentView(viewDialog);
            dialog.show();

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
    } // end of AsyncTaskClass
}

