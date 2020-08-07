package com.example.stampinseoul2.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stampinseoul2.R;

public class ThemeActiFragment extends Fragment {
    private RecyclerView themeRecyclerView;

    public static ThemeActiFragment newInstance() {
        ThemeActiFragment themeActiFragment = new ThemeActiFragment();
        return themeActiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        themeRecyclerView = rootView.findViewById(R.id.themeRecyclerView);


        return rootView;
    }

}
