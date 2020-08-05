package com.example.stampinseoul2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class tutorial_fragment5 extends Fragment {

    private TextView textView;
    private View view;

    public static tutorial_fragment5 newInstance() {
        tutorial_fragment5 fragment5 = new tutorial_fragment5();

        return fragment5;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tutorial_frag5,container,false);

        textView = view.findViewById(R.id.textView);
        return view;
    }
}
