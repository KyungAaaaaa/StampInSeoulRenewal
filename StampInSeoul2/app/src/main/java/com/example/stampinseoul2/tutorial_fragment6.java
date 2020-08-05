package com.example.stampinseoul2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class tutorial_fragment6 extends Fragment {

    private TextView textView;
    private View view;

    public static tutorial_fragment6 newInstance() {
        tutorial_fragment6 fragment6 = new tutorial_fragment6();
        return fragment6;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tutorial_frag6,container,false);
        textView = view.findViewById(R.id.textView);

        return view;
    }
}
