package com.example.gotothefestival.BottomMenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gotothefestival.Login.LoginActivity;

import com.bumptech.glide.Glide;
import com.example.gotothefestival.Model.ThemeData2;
import com.example.gotothefestival.Model.User;
import com.example.gotothefestival.R;
import com.example.gotothefestival.UserDBHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.CustomViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<ThemeData2.Item> list;

    public AlbumAdapter(Context context, int layout, ArrayList<ThemeData2.Item> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    //내가 커스텀한 view를 보여주기위한 함수
    @NonNull
    @Override
    public AlbumAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    //내가 커스텀한 view에 세팅해주는 함수
    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.CustomViewHolder customViewHolder, int position) {
        customViewHolder.txtPola.setText(list.get(position).getContent_pola());

        customViewHolder.txtTitle.setText(list.get(position).getContent_title());
        customViewHolder.txtContent.setText(list.get(position).getContents());
        customViewHolder.txtID.setText(list.get(position).getTitle());

        customViewHolder.itemView.setTag(position);

        if (list.get(position).getPicture() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getPicture());
            customViewHolder.imgReview.setImageBitmap(bitmap);

        } else {
            customViewHolder.imgReview.setImageResource(R.drawable.a_dialog_design);
        }

        if (list.get(position).getFirstimage() != null) {

            Glide.with(customViewHolder.itemView.getContext()).load(list.get(position).getFirstimage()).override(500, 300).into(customViewHolder.imgChoice);

        } else {
            customViewHolder.imgChoice.setImageResource(R.drawable.a_dialog_design);
        }

    }

    @Override
    public int getItemCount() {
        return (list != null) ? (list.size()) : (0);// 리스트에 값이 들어있으면 카운트를 해준다.
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgReview;
        public CircleImageView imgChoice;

        public TextView txtPola;
        public TextView txtTitle;
        public TextView txtContent;
        public TextView txtID;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgReview = itemView.findViewById(R.id.imgReview);
            imgChoice = itemView.findViewById(R.id.imgChoice);

            txtPola = itemView.findViewById(R.id.txtPola);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtID = itemView.findViewById(R.id.txtID);


//            imgReview.setOnLongClickListener(view -> {
//                    UserDBHelper dbHelper = UserDBHelper.getInstance(context);
//                    Snackbar snackbar = Snackbar.make(view, "기록을 삭제하시겠습니까?", Snackbar.LENGTH_LONG); //스낵바 우측 텍스트 띄우고 터치 했을때 이벤트 설정
//                    snackbar.setAction("확인", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dbHelper.deleteAlbumData(LoginActivity.userData, list.get(getAdapterPosition()));
//                            snackbar.dismiss();
//                        }
//                    });
//
//
//                return true;
//            });
        }

    }

    //
    private int exiforToDe(int exifOrientation) {

        switch (exifOrientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:

                return 90;

            case ExifInterface.ORIENTATION_ROTATE_180:

                return 180;

            case ExifInterface.ORIENTATION_ROTATE_270:

                return 270;

        }

        return 0;

    }

    private Bitmap rotate(Bitmap bitmap, int exifDegres) {

        Matrix matrix = new Matrix();

        matrix.postRotate(exifDegres);

        Bitmap teepre = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return teepre;

    }

}

