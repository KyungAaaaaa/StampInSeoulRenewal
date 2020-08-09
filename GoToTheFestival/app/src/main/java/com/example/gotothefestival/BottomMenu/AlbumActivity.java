package com.example.gotothefestival.BottomMenu;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.example.gotothefestival.UserDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumActivity extends Fragment implements View.OnTouchListener {
    private ArrayList<ThemeData> cameraList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AlbumAdapter albumAdapter;
    private View view;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_album, container, false);
        findViewByidFunc();
        //리사이클러뷰 불러오는 객체
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        UserDBHelper dbHelper = UserDBHelper.getInstance(getContext());
        albumAdapter = new AlbumAdapter(getContext(), R.layout.album_item, cameraList);
        recyclerView.setAdapter(albumAdapter);

        cameraList.removeAll(cameraList);

        cameraList = dbHelper.onSelectAlbumTBL(LoginActivity.userData);
        albumAdapter = new AlbumAdapter(getContext(), R.layout.album_item, cameraList);

        recyclerView.setAdapter(albumAdapter);

        albumAdapter.notifyDataSetChanged();


        dbHelper.onSelectAlbumTBL(LoginActivity.userData);
        return view;
    }

    private void findViewByidFunc() {
        recyclerView = view.findViewById(R.id.recyclerView);
    }


}
