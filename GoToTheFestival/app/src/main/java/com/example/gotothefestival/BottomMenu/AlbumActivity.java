package com.example.gotothefestival.BottomMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.ClickListener;
import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.example.gotothefestival.RecyclerTouchListener;
import com.example.gotothefestival.UserDBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AlbumActivity extends Fragment implements View.OnTouchListener {
    private ArrayList<ThemeData.Item> cameraList = new ArrayList<>();
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
//        albumAdapter = new AlbumAdapter(getContext(), R.layout.album_item, cameraList);
//        recyclerView.setAdapter(albumAdapter);

        cameraList.removeAll(cameraList);

        cameraList = dbHelper.onSelectAlbumTBL(LoginActivity.userData);
        albumAdapter = new AlbumAdapter(getContext(), R.layout.album_item, cameraList);

        recyclerView.setAdapter(albumAdapter);

        albumAdapter.notifyDataSetChanged();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

                    //UserDBHelper dbHelper = UserDBHelper.getInstance(getContext());
                    Snackbar snackbar = Snackbar.make(view, "기록을 삭제하시겠습니까?", Snackbar.LENGTH_LONG); //스낵바 우측 텍스트 띄우고 터치 했을때 이벤트 설정
                    snackbar.setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbHelper.deleteAlbumData(LoginActivity.userData, cameraList.get(position));
                            snackbar.dismiss();
                            cameraList.removeAll(cameraList);

                            cameraList = dbHelper.onSelectAlbumTBL(LoginActivity.userData);
                            albumAdapter = new AlbumAdapter(getContext(), R.layout.album_item, cameraList);
                            recyclerView.setAdapter(albumAdapter);
                        }
                    });
                    snackbar.show();

            }
        }));

        dbHelper.onSelectAlbumTBL(LoginActivity.userData);
        return view;
    }

    private void findViewByidFunc() {
        recyclerView = view.findViewById(R.id.recyclerView);
    }


}
