package com.example.gotothefestival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapInWeb extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_in_web);

        webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();

        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true);
        webView.loadUrl("Http://www.google.com");

    }
}

