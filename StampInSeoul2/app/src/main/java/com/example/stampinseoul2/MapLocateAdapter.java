package com.example.stampinseoul2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stampinseoul2.Model.ThemeData;

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


    @Override
    public void onBindViewHolder(@NonNull final MapLocateAdapter.CustomViewHolder customViewHolder, final int position) {

        //list.add(new ThemeData("아파트","서울시 도봉구 방학3동",37.662049, 127.022908));

//        customViewHolder.txtContent.setText("서울시 도봉구 방학3동");
//        customViewHolder.txtName.setText("아파트");
        ThemeData data = list.get(position);

        customViewHolder.txtName.setText(data.getTitle());
        customViewHolder.txtContent.setText(data.getAddr());
//
//        customViewHolder.itemView.setTag(position);
//
//        customViewHolder.imaProfile.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String str = list.get(position).getTitle();
//
//                Uri uri = Uri.parse("https://www.google.com/search?q="+str+"&oq="+str+"&aqs=chrome");
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//                intent.setPackage("com.android.chrome");
//
//                v.getContext().startActivity(intent);
//
//            }
//        });
//
//        customViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), CameraActivity.class);
//
//                intent.putExtra("title", list.get(position).getTitle());
//
//                v.getContext().startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return (list != null) ? (list.size()) : (0); // 리스트에 값이 들어있으면 ~
    }

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
