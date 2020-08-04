package com.example.stampinseoul2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThemeFestivalFragment extends Fragment {
    private RecyclerView themeRecyclerView;
    private LinearLayoutManager layoutManager;

    public static ThemeFestivalFragment newInstance() {
        ThemeFestivalFragment themeFestivalFragment = new ThemeFestivalFragment();
        return themeFestivalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);

        layoutManager = new LinearLayoutManager(getActivity());
        themeRecyclerView.setLayoutManager(layoutManager);
        ArrayList<ThemeData> list = new ArrayList<>();
        list.add(new ThemeData("1"));
        list.add(new ThemeData("2"));
        list.add(new ThemeData("3"));
        list.add(new ThemeData("4"));
        list.add(new ThemeData("5"));
        list.add(new ThemeData("6"));
        list.add(new ThemeData("7"));
        list.add(new ThemeData("8"));
        list.add(new ThemeData("9"));
        list.add(new ThemeData("10"));

        // 리사이클러뷰에 ThemeAdapter 객체 지정.
        ThemeAdapter adapter = new ThemeAdapter(list);
        themeRecyclerView.setAdapter(adapter);
        return rootView;
    }

}
