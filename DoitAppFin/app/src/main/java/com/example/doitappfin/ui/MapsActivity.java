package com.example.doitappfin.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitappfin.R;
import com.example.doitappfin.utils.MyRecyclerViewAdapter;
import com.example.doitappfin.utils.RadioListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener,MyRecyclerViewAdapter.ItemClickListener {
    private GoogleMap mMap;

    private MyRecyclerViewAdapter adapter1;
  
    ArrayList<Float> dist;
    private Button proceed;

    ArrayList<String> allar, allat;
    ArrayList<String> allarea;
    int a = 0;
    String p="",l="",li="",d="",image="";
    private TextView ttitle, tdesc;
    ArrayList<String> centers;
    private RecyclerView recyclerView;
    Geocoder geocoder;
    List<Address> addresses;
    HashMap<String,String> price,latmap;
    private double lat = 0.00;
    private FusedLocationProviderClient fusedLocationClient;
    private double lon = 0.00;
    private final int REQUEST_LOCATION_PERMISSION = 1;
HashMap<Integer, Marker> haMap;

    private String stitle = "", sdec = "";
    LocationManager locationManager;
Location locat;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestLocationPermission();
proceed=findViewById(R.id.button4);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MapsActivity.this,PaymentActivity.class);
                intent.putExtra("price",p);
                intent.putExtra("domain",d);
                intent.putExtra("locationdoit",li);
                intent.putExtra("area",l);
                intent.putExtra("image",image);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void datafetched() {
Random rand=new Random();

        for (int j = 0; j < allat.size(); j++) {

            String bro[] = allat.get(j).split("_");
            double at = 0, on = 0;
            if(bro.length == 2) {
                if(bro[0]!=null)
                bro[0] = bro[0].replace("\"", "");
                if(bro[1]!=null)
                bro[1] = bro[1].replace("\"", "");
                at = Double.parseDouble(bro[0].replace("\"", ""));
                on = Double.parseDouble(bro[1].replace("\"", ""));


            }
            else {
                System.out.println(allat.get(j)+"---"+locat.getLatitude()+"---"+at+"---"+allat.size()+"--");
            }


            Location la=new Location("t"+j);
                
                la.setLatitude(at);
                la.setLongitude(on);

            if(locat!=null) {
                float B = locat.distanceTo(la) / 1000;
                String aa = B + "";
                aa = aa.substring(0, 4);
                dist.add(B);

            }


            Marker m =mMap.addMarker((new MarkerOptions().position(new LatLng(at, on))).title(allar.get(j))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            if(m!=null)
            haMap.put(j,m);


        }
 allarea=new ArrayList<>();
        try {



        for (int j = 0; j < allat.size(); j++) {

if(allar.size()>0 && dist.size()>0)
            allarea.add(allar.get(j)+" DOIT-0"+ (int)(50+j*2 +dist.get(j)));
        }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e);
        }


        for (int i = 0; i < dist.size(); i++) {
            for (int j = 0; j < dist.size() - i - 1; j++) {
                if (dist.get(j)>(dist.get(j + 1))) {
                    Float temp = dist.get(j);
                    dist.set(j, dist.get(j + 1));
                    dist.set(j + 1, temp);


                    String temp1 = allarea.get(j);
                    allarea.set(j, allarea.get(j + 1));
                    allarea.set(j + 1, temp1);

                    Marker m = haMap.get(j);
                    haMap.put(j, haMap.get(j + 1));
                    haMap.put(j + 1, m);

                    String temp2 = allat.get(j);
                    allat.set(j, allat.get(j + 1));
                    allat.set(j + 1, temp2);


                }
                adapter1.UpdateItemsList(allarea, dist);


            }



        }


        adapter1 = new MyRecyclerViewAdapter(MapsActivity.this, allarea, dist);
        adapter1.setClickListener(this);

        recyclerView.setAdapter(adapter1);

        if(locat!=null) {
            LatLng syd = new LatLng(locat.getLatitude(), locat.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(syd)      // Sets the center of the map to Mountain View
                    .zoom(13f)                   // Sets the zoom
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            /*
             */
        }
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
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney,Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     *  installed Google Play services and returned to the app.
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //  TODO: Consider calling
            //   requestPermissions
            //


            return;
        }


        geocoder = new Geocoder(this, Locale.getDefault());
       adapter1 = new MyRecyclerViewAdapter(MapsActivity.this, allar, dist);
      adapter1.setClickListener(this);



        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            LatLng syd = new LatLng(location.getLatitude(), location.getLongitude());
                            locat=location;
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(syd)      // Sets the center of the map to Mountain View
                                    .zoom(13f)                   // Sets the zoom
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            float f = 8;

                         //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(syd, f));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(syd));
                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    Activity#requestPermissions

                                return;
                            }
                            mMap.setMyLocationEnabled(true);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));






                        }
                    }





                });

        mMap.getUiSettings().setMapToolbarEnabled(false);



    }


    @SuppressWarnings("deprecation")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(Location location) {


//

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions

            return;
        }
        mMap.setMyLocationEnabled(true);


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
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    public boolean connectedToNetwork() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {

            return activeNetworkInfo.isConnected();
        }

        return false;

    }


    public void NoInternetAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are not connected to the internet. ");
        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(connectedToNetwork()){
                    volley();
                }else{ NoInternetAlertDialog(); }
            }
        });
        builder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent openSettings = new Intent();
                openSettings.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                openSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(openSettings);
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void volley() {

        Intent i = getIntent();
        haMap=new HashMap<>();

        allar = new ArrayList<>();
        allat = new ArrayList<>();
        ttitle = findViewById(R.id.titl);
        tdesc = findViewById(R.id.decs);

        stitle = i.getStringExtra("title");
        sdec = i.getStringExtra("desc");
        image=i.getStringExtra("image");
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//System.out.println(stitle+" "+sdec);
        ttitle.setText(stitle);
        tdesc.setText(sdec);
        dist = new ArrayList<Float>();
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        centers = new ArrayList<>();
        price=new HashMap<>();
        latmap=new HashMap<>();


        if(i.getStringExtra("from").equals("main"))
        {
            FirebaseDatabase.getInstance().getReference().child("PopularData").child("Training").child(stitle).child("Centers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    centers.clear();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                           //  System.out.println(d1.getKey()+"--"+d1.getValue());
                        String s = d1.getKey();
                        s = s.replace("_a", "&");
                        s = s.replace("_m", "-");
                        centers.add(s);
                        price.put(s,d1.getValue().toString());
                    }
                    for (int j = 0; j < centers.size(); j++) {
                        final int finalJ1 = j;
                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("latlong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()!=null) {
                                    String s = dataSnapshot.getValue() + "";
                                    String bal[] = s.split("-");
                                    for (int m = 0; m < bal.length; m++) {

                                        allat.add(bal[m].trim());
                                        latmap.put(bal[m].trim(), centers.get(finalJ1));
                                    }//    System.out.println(allat);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final int finalJ = j;
                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("area").addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue() + "";
                                String bal[] = s.split("-");
                                for (int m = 0; m < bal.length; m++)
                                {

                                    allar.add(bal[m].trim());

                                }

                                  System.out.println("alla"+allar);


                                if (finalJ == centers.size() - 1)
                                    datafetched();

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if(i.getStringExtra("from").equals("train"))
        {

            FirebaseDatabase.getInstance().getReference().child("MainData").child("TrainingData").child(stitle).child("Centers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
price.clear();
                    centers.clear();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                        //     System.out.println(d1.getKey());
                        String s = d1.getKey();
                        s = s.replace("_a", "&");
                        s = s.replace("_m", "-");
                        centers.add(s);
                        price.put(s,d1.getValue().toString());

                    }

                    for (int j = 0; j < centers.size(); j++) {

                        final int finalJ1 = j;
                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("latlong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue() + "";
                                String bal[] = s.split("-");
                                for (int m = 0; m < bal.length; m++) {
                                    allat.add(bal[m].trim());
                                    latmap.put(bal[m].trim(), centers.get(finalJ1));
                                }
                                //    System.out.println(allat);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final int finalJ = j;
                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("area").addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue() + "";
                                String bal[] = s.split("-");
                                for (int m = 0; m < bal.length; m++)
                                    allar.add(bal[m].trim());


                                //   System.out.println(allar);


                                if (finalJ == centers.size() - 1)
                                    datafetched();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

    @Override
    public void onItemClick(View view, int position) {
        Marker m= haMap.get(position);
        m.showInfoWindow();
        System.out.println(m.getPosition().latitude);
        System.out.println(m.getPosition().longitude);


p=price.get(latmap.get(allat.get(position)));
d=stitle;
l=allarea.get(position);
li=latmap.get(allat.get(position));

        System.out.println(price.get(latmap.get(allat.get(position))));
        System.out.println(stitle);
        System.out.println(latmap.get(allat.get(position)));
        System.out.println(allarea.get(position));

         proceed.setVisibility(View.VISIBLE);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(),15));


    }
}