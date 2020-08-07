package com.example.gotothefestival.BottomMenu;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotothefestival.Login.LoginActivity;
import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
    static GoogleMap googleMaps;
    static GoogleMap googleMaps2;

    private FragmentManager fragmentManager;
    private MapView mapView;

    private View view1;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MapLocateAdapter mapLocateAdapter;

    private Animation fab_open, fab_close;
    private Boolean isfabOpen = false;

    private FloatingActionButton fab, fab1, fab2;
    private DrawerLayout drawerLayout;
    private ConstraintLayout drawer;

    private static final int FASTEST_UPDATE_INTERVAL_MS = 1000 * 30;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view1 = inflater.inflate(R.layout.activity_map_location, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        recyclerView = view1.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(view1.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        com.example.gotothefestival.UserDBHelper userDBHelper = com.example.gotothefestival.UserDBHelper.getInstance(getContext());
        themedatalist = userDBHelper.likePlaceLoad(LoginActivity.userData);
        mapLocateAdapter = new MapLocateAdapter(R.layout.map_item, themedatalist);

        recyclerView.setAdapter(mapLocateAdapter);


        mapView = view1.findViewById(R.id.fgGoogleMap);

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync((OnMapReadyCallback) this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        recyclerView.addOnItemTouchListener(new com.example.gotothefestival.RecyclerTouchListener(getContext(), recyclerView, new com.example.gotothefestival.ClickListener() {
            @Override
            public void onClick(View view, int position) {

//                LatLng latLng1 = new LatLng(37.662049, 127.022908);
//
//                MarkerOptions markerOptions1 = new MarkerOptions();
//                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.placeholder);
//                Bitmap b = bitmapdraw.getBitmap();
//                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
//                markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//                markerOptions1.position(latLng1);
//                markerOptions1.getIcon();
//                googleMaps2.addMarker(markerOptions1);
//                googleMaps2.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
//                googleMaps2.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));

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

        drawer.setOnTouchListener(this);
        drawerLayout.setDrawerListener(listener);

        return view1;


    }

    private void findViewByIdFunc() {
        fab = view1.findViewById(R.id.fab);
        fab1 = view1.findViewById(R.id.fab1);
        fab2 = view1.findViewById(R.id.fab2);
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
            case R.id.fab1:
                anmi();
                drawerLayout.openDrawer(drawer);
                break;
            case R.id.fab2:
                anmi();
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(view1.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
                }
            default:
                break;
        }
    }

    private void anmi() {
        if (isfabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);

            isfabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);

            fab1.setClickable(true);
            fab2.setClickable(true);

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

        googleMaps = googleMap;

        googleMaps2 = googleMap;

        googleMaps.clear();

        if (check.size() >= 1) {

            for (String x : check) {
                for (ThemeData y : themedatalist) {
                    if (x.equals(y.getTitle())) ;
                    {
                        //api에서 xy좌표를 반대로 제공.
                        LatLng latLng = new LatLng(y.getMapY(), y.getMapX());
                        Log.d("MapActivity", y.getMapY() + "/" + y.getMapX());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title(y.getTitle());
                        markerOptions.snippet(y.getAddr());
                        markerOptions.position(latLng);

                        googleMaps2.addMarker(markerOptions);
                        googleMaps2.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    }
                }
            }
        }

        if (win) {

            LatLng latLng = new LatLng(lat, lng);

            MarkerOptions markerOptions = new MarkerOptions();

            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.placeholder);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            markerOptions.position(latLng);
            markerOptions.getIcon();
            googleMaps.addMarker(markerOptions);
            googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

            win = false;
        }

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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
