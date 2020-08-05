package com.example.stampinseoul2;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.usermgmt.LoginButton;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private LoginButton btn_kakao_login;
    private SessionCallback sessionCallback;
    public Long userId=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);

        btn_kakao_login = findViewById(R.id.btn_kakao_login);

        //++++++++++++++ 여기부터 카카오 +++++++++++++++

        sessionCallback = new SessionCallback(); //세션 초기화
        Session.getCurrentSession().addCallback(sessionCallback); //현재 세션에 콜백넣음
        Session.getCurrentSession().checkAndImplicitOpen(); // 자동으로 로그인됨.

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_kakao_login.performClick();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            


        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {

        }
    }

}

