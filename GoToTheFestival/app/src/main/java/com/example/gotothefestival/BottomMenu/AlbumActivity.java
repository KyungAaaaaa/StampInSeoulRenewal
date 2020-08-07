package com.example.gotothefestival.BottomMenu;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlbumActivity extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private ArrayList<ThemeData> cameraList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AlbumAdapter albumAdapter;

    private View view;


    // == 플로팅 버튼, 드로어

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;

    private FloatingActionButton fab, fab1, fab2;
    private DrawerLayout drawerLayout;
    private ConstraintLayout drawer;

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
        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        albumAdapter = new AlbumAdapter(R.layout.album_item, cameraList);

        recyclerView.setAdapter(albumAdapter);

        cameraList.removeAll(cameraList);


        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);


        fab_open = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_close);

        drawerLayout = view.findViewById(R.id.drawerLayout);
        drawer = view.findViewById(R.id.drawer);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


//        drawer.setOnTouchListener(this);
        drawerLayout.setDrawerListener(listener);

        albumAdapter = new AlbumAdapter(R.layout.album_item, cameraList);

        recyclerView.setAdapter(albumAdapter);
        albumAdapter.notifyDataSetChanged();

//        MainActivity.db = MainActivity.userdbHelper.getWritableDatabase();
//
//        String searchComplete = "SELECT * FROM STAMP_" + LoginActivity.userId + " WHERE complete=1;";
//
//        Cursor cursorComplete = MainActivity.db.rawQuery(searchComplete, null);
//        while (cursorComplete.moveToNext()) {
//            cameraList.add(new ThemeData(cursorComplete.getString(1), cursorComplete.getString(5),
//                    cursorComplete.getString(6), cursorComplete.getString(7), cursorComplete.getString(8), cursorComplete.getString(9),
//                    cursorComplete.getInt(10)));
//        }
        return view;
    }

    private void findViewByidFunc() {

    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        // 슬라이딩을 시작 했을때 이벤트 발생
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {

        }

        // 메뉴가 열었을때 이벤트 발생
        @Override
        public void onDrawerOpened(@NonNull View view) {

        }

        // 메뉴를 닫았을때 이벤트 발생
        @Override
        public void onDrawerClosed(@NonNull View view) {

        }

        // 메뉴 바가 상태가 바뀌었을때 이벤트 발생
        @Override
        public void onDrawerStateChanged(int i) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fab:

                anim();

                break;

            case R.id.fab1:

                anim();
                drawerLayout.openDrawer(drawer);


                break;

            // == 미션 포기 버튼

            case R.id.fab2:

                anim();

                View viewDialog = View.inflate(view.getContext(), R.layout.dialog_search_message, null);

                Button btnGiveUp = viewDialog.findViewById(R.id.btnGiveUp);
                Button btnExit = viewDialog.findViewById(R.id.btnExit);

                TextView txt_Detail_title = viewDialog.findViewById(R.id.txt_Detail_title);
                TextView txtWarning = viewDialog.findViewById(R.id.txtWarning);

                final Dialog noSearchDlg = new Dialog(view.getContext());

                noSearchDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                txt_Detail_title.setText("주의");
                txtWarning.setText("정말 미션을 포기 하시겠어요?");

                noSearchDlg.setContentView(viewDialog);
                noSearchDlg.show();

                //DB할때 쓸꺼
              /* btnGiveUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cameraList.removeAll(cameraList);

                        MainActivity.db = MainActivity.dbHelper.getWritableDatabase();
                        MainActivity.db.execSQL("DROP TABLE IF EXISTS STAMP_" + LoginActivity.userId + ";");
                        MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS STAMP_" + LoginActivity.userId  + "("
                                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + "title TEXT, "
                                + "addr TEXT, "
                                + "mapX REAL, "
                                + "mapY REAL, "
                                + "firstImage TEXT, "
                                + "picture TEXT, "
                                + "content_pola TEXT, "
                                + "content_title TEXT, "
                                + "contents TEXT, "
                                + "complete INTEGER);");

                        Intent intent = new Intent(v.getContext(), ThemeActivity.class);
                        startActivity(intent);

                        getActivity().finish();

                    }
                });*/
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noSearchDlg.dismiss();
                    }
                });
            default:
                break;
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
}
