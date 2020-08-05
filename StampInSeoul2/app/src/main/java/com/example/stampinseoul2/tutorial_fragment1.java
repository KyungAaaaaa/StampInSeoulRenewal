package com.example.stampinseoul2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

    public class tutorial_fragment1 extends Fragment {
        private TextView textView;
        private View view;
        private Button btnStart;


        //페이저를 통해  슬라이딩 탭을 눌러 프레그들이 변경되려면 현재 프레그를 저장해줘야한다.
        public static tutorial_fragment1 newInstance() {
            tutorial_fragment1 fragment1 = new tutorial_fragment1();

            return fragment1;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            //mainactivity에 있는 setContentView와 같음.
            view = inflater.inflate(R.layout.tutorial_frag1, container, false);

            textView = view.findViewById(R.id.textView);
            ImageView marker = view.findViewById(R.id.marker);
            //GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(marker);
            //Glide.with(this).load(R.drawable.markeranimaition).into(gifImage);

            return view;

        }
    }
