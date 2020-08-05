package com.example.stampinseoul2;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    private ArrayList<Item> dataArrayList = null;
    //private ArrayList<ThemeData> dataArrayList = null;
    Context context;

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
        Item data = dataArrayList.get(position);
        //ThemeData data = dataArrayList.get(position);
        holder.txtView.setText(data.getTitle());
        Glide.with(context).load(data.getFirstimage()).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
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

        // 생성자에서 데이터 리스트 객체를 전달받음.
    ThemeAdapter(ArrayList<Item> list) {
        dataArrayList = list;
    }
    // 생성자에서 데이터 리스트 객체를 전달받음.
//    ThemeAdapter(ArrayList<ThemeData> list) {
//        dataArrayList = list;
//    }

}

