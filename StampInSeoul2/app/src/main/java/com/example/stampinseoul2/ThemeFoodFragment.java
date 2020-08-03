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

public class ThemeFoodFragment extends Fragment {
    private RecyclerView themeRecyclerView;

    public static ThemeFoodFragment newInstance() {
        ThemeFoodFragment themeFoodFragment = new ThemeFoodFragment();
        return themeFoodFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);

        rootView.setBackgroundColor(Color.GRAY);
        return rootView;
    }

}
