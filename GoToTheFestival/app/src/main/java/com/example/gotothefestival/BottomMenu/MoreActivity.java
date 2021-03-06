package com.example.gotothefestival.BottomMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.R;
import com.google.android.material.snackbar.Snackbar;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MoreActivity extends Fragment {

    private Button btnLogout;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_more, container, false);

        findViewfunc();
        //로그아웃 버튼 이베트 등록
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogout();
                Snackbar.make(getView(), "로그아웃 되었습니다.", Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }
    //유저정보를 받아서 종료시키는 함수
    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void findViewfunc() {
        btnLogout = view.findViewById(R.id.btnLogout);
    }
}


