package com.example.doitapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitapp.R;
import com.example.doitapp.utils.MyRecyclerViewAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MyRecyclerViewAdapter.ItemClickListener {
    private GoogleMap mMap;
    double[] latarr={13.131102,13.132951, 13.124145, 13.114964, 13.083284,  13.083458,13.0909973 };
    double[] lonarr={ 80.151254,80.147683, 80.140874, 80.157521,80.140780 ,80.106270, 80.20895 };
    private MyRecyclerViewAdapter adapter1;
    List<String> addr;
    List<String> dist;

    private RecyclerView recyclerView;
    Geocoder geocoder;
    List<Address> addresses;
    private double lat = 0.00, lon = 0.00;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    LocationManager locationManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestLocationPermission();
        addr=new ArrayList<String>();
        dist=new ArrayList<String>();
        recyclerView=findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        Criteria criteria = new Criteria();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        lat=location.getLatitude();
        lon=location.getLongitude();

        System.out.println(location+"-" +lat+" "+lon+" ");

        LatLng syd = new LatLng(location.getLatitude(), location.getLongitude());
        float f=8;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(syd,f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(syd));
        mMap.setMyLocationEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(syd)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                // Sets the orientation of the camera to east
                                  // SstartPoint.distanceTo(endPoint);ets the tilt of the camera to  30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        String s="";



        geocoder = new Geocoder(this, Locale.getDefault());

        for (int i=0;i<latarr.length;i++)
{
    Location loccheck=new Location("t"+i);
    loccheck.setLatitude(latarr[i]);
    loccheck.setLongitude(lonarr[i]);
    if(location.distanceTo(loccheck)<5000)
    {
        try {
            addresses = geocoder.getFromLocation(loccheck.getLatitude(), loccheck.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);
        s=s+(int)location.distanceTo(loccheck)+" Meter  \n"+address+"\n \n";
        mMap.addMarker(new MarkerOptions().position(new LatLng(loccheck.getLatitude(),loccheck.getLongitude())));

        addr.add(address);
        dist.add((int)location.distanceTo(loccheck)+" Meters");

    }


}    adapter1 = new MyRecyclerViewAdapter(MapsActivity.this, addr,dist);
        adapter1.setClickListener(MapsActivity.this);
        recyclerView.setNestedScrollingEnabled(true
        );
        recyclerView.setAdapter(adapter1);
        adapter1.UpdateItemsList(addr,dist);
System.out.println(addr.size()+" "+addr);
        System.out.println(dist.size()+" "+dist);
    }

    @Override
    public void onLocationChanged(Location location) {

System.out.println(location+" " +lat+" "+lon+" ");
        lat=location.getLatitude();
        lon=location.getLongitude();
        LatLng sydney = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, ""+addr.get(position), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MapsActivity.this,PaymentActivity.class));
    }
}
