package com.example.gotothefestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.gotothefestival.Login.LoginActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "r5SONxjKf67vRjWSB5VkCHjhlvpWtAAcXV8IEJumquZL3SfuS9eazbphf2%2BSprq0iO6PVT1MVcC70enAwCeLOA%3D%3D";
    public static final String APP_NAME = "Apptest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHashKey();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    private void getHashKey() {
        PackageInfo packageInfo = null;

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            Log.e("KeyHash", "keyHash,:null");


            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
                }
            }
        }

    }
}
