package com.example.stampinseoul2;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MapLocateActivity extends Fragment implements View.OnTouchListener, View.OnClickListener, OnMapReadyCallback {

    static final String TAG = "MapLocateActivity";

    boolean win = false;

    double lat = 0.0;
    double lng = 0.0;

    LocationManager locationManager;
    PendingIntent pendingIntent;

    static ArrayList<ThemeData> themedatalist = new ArrayList<>();
    ArrayList<MarkerOptions> cctvlist = new ArrayList<MarkerOptions>();
    ArrayList<String> check = new ArrayList<>();
    static GoogleMap googleMap;
    static GoogleMap googleMap2;

    private FragmentManager fragmentManager;
    private MapView mapView;

    private View view1;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MapLocateAdapter mapLocateAdapter;

    private Animation fab_open, fab_close;
    private Boolean isfabOpen = false;

    private FloatingActionButton fab, fab1, fab2, fab3;
    private DrawerLayout drawerLayout;
    private ConstraintLayout drawer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view1 = inflater.inflate(R.layout.activity_map_location, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        recyclerView = view1.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(view1.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

//        listSetting();

        mapLocateAdapter = new MapLocateAdapter(R.layout.map_item, themedatalist);

        recyclerView.setAdapter(mapLocateAdapter);


        mapView = view1.findViewById(R.id.fgGoogleMap);

//        mapView.onCreate(savedInstanceState); 수정해야할부분 2020-08.04

        mapView.getMapAsync((OnMapReadyCallback) this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                boolean tag = true;
                if (check.size() == 0) {
                    check.add(themedatalist.get(position).getTitle());
                } else {
                    for (int i = 0; i < check.size(); i++) {
                        check.remove(i);
                        tag = false;
                        break;
                    }
                    if (tag)
                        check.add(themedatalist.get(position).getTitle());
                }
                mapView = view1.findViewById(R.id.fgGoogleMap);

                mapView.getMapAsync(MapLocateActivity.this);

                drawerLayout.closeDrawer(drawer);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        findViewByIdFunc();

        fab_open = AnimationUtils.loadAnimation(view1.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(view1.getContext(), R.anim.fab_close);

        drawerLayout = view1.findViewById(R.id.drawerLayout);
        drawer = view1.findViewById(R.id.drawer);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapInWeb.class);
                startActivity(intent);
            }
        });

        drawer.setOnTouchListener(this);
        drawerLayout.setDrawerListener(listener);
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    private void findViewByIdFunc() {
        fab = view1.findViewById(R.id.fab);
        fab1 = view1.findViewById(R.id.fab1);
        fab2 = view1.findViewById(R.id.fab2);
        fab3 = view1.findViewById(R.id.fab3);
    }

//    private void listSetting() {
//        themedatalist.removeAll(themedatalist);
//
//        MainActivity.db
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                anmi();
                break;
        }
    }

    private void anmi() {
        if (isfabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);

            isfabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);

            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);

            isfabOpen = true;
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();

            win = true;

            mapView = view1.findViewById(R.id.fgGoogleMap);

            mapView.getMapAsync(MapLocateActivity.this);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float sof) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
