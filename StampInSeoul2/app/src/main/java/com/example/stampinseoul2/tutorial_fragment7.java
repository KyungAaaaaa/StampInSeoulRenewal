package com.example.stampinseoul2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class tutorial_fragment7 extends Fragment {
    private TextView textView;
    private View view;
    private Button btnStart;

    public static tutorial_fragment7 newInstacne() {
        tutorial_fragment7 fragment7 = new tutorial_fragment7();
        return fragment7;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tutorial_frag7,container,false);

        textView = view.findViewById(R.id.textView);
        btnStart = view.findViewById(R.id.btnStart);
/*
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);

                startActivity(intent);

                getActivity().finish();
            }
        });*/

        return view;

    }
}
