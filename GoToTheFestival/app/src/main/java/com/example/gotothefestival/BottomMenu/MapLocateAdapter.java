package com.example.gotothefestival.BottomMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;

import java.util.ArrayList;

public class MapLocateAdapter extends RecyclerView.Adapter<MapLocateAdapter.CustomViewHolder> {
    private int layout;
    private ArrayList<ThemeData> list;

    static int number = 0;

    public MapLocateAdapter(int layout, ArrayList<ThemeData> themedatalist) {
        this.layout = layout;
        this.list = themedatalist;
    }

    @NonNull
    @Override
    public MapLocateAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    //직접 만든 UI의 값을 주는 함수 (Parcelable을 사용해서 메인화면애서 선택된값을 맵클래스에있는 리사클러뷰에 다시 뿌려준다)
    @Override
    public void onBindViewHolder(@NonNull final MapLocateAdapter.CustomViewHolder customViewHolder, final int position) {

        ThemeData data = list.get(position);

        customViewHolder.txtName.setText(data.getTitle());
        customViewHolder.txtContent.setText(data.getAddr());

        customViewHolder.imaProfile.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //선택된 정보에대해서 값을 받아서 구글에 자동으로 검색해주는 기능
                String str = list.get(position).getTitle();

                Uri uri = Uri.parse("https://www.google.com/search?q="+str+"&oq="+str+"&aqs=chrome");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                intent.setPackage("com.android.chrome");

                v.getContext().startActivity(intent);

            }
        });

        //이미지버튼 클릭 이벤트 ,
        customViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CameraActivity.class);
                intent.putExtra("themadata",data);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (list != null) ? (list.size()) : (0); // 리스트에 값이 들어있으면 카운트를 해준다.
    }

    //내부 클래스...
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public ImageView imaProfile;
        public ImageView imgPhoto;

        public TextView txtName;
        public TextView txtContent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            imaProfile = itemView.findViewById(R.id.fImgProfile);
            imgPhoto = itemView.findViewById(R.id.fImgCamera);

            txtName = itemView.findViewById(R.id.fTxtName);
            txtContent = itemView.findViewById(R.id.txtContent);

        }
    }
}
