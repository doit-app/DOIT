package com.example.doitappfin.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitappfin.R;
import com.example.doitappfin.utils.MyRecyclerViewAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MyRecyclerViewAdapter.ItemClickListener {
    private GoogleMap mMap;

    private MyRecyclerViewAdapter adapter1;
  
    ArrayList<Float> dist;

    ArrayList<String> allar, allat;

    int a = 0;
    private TextView ttitle, tdesc;
    ArrayList<String> centers;
    private RecyclerView recyclerView;
    Geocoder geocoder;
    List<Address> addresses;
    private double lat = 0.00;
    private FusedLocationProviderClient fusedLocationClient;

    private double lon = 0.00;
    private final int REQUEST_LOCATION_PERMISSION = 1;
HashMap<Integer, Marker> haMap;
    String allarea = "", alllatlong = "";

    private String stitle = "", sdec = "";
    LocationManager locationManager;
Location locat;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestLocationPermission();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent i = getIntent();
haMap=new HashMap<>();

        allar = new ArrayList<>();
        allat = new ArrayList<>();
        ttitle = findViewById(R.id.titl);
        tdesc = findViewById(R.id.decs);

        stitle = i.getStringExtra("title");
        sdec = i.getStringExtra("desc");
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//System.out.println(stitle+" "+sdec);
        ttitle.setText(stitle);
        tdesc.setText(sdec);
        dist = new ArrayList<Float>();
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        centers = new ArrayList<>();
        if(i.getStringExtra("from").equals("main"))
        {
            FirebaseDatabase.getInstance().getReference().child("PopularData").child("Training").child(stitle).child("Centers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    centers.clear();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                        //     System.out.println(d1.getKey());
                        String s = d1.getKey();
                        s = s.replace("_a", "&");
                        s = s.replace("_m", "-");
                        centers.add(s);

                    }

                    for (int j = 0; j < centers.size(); j++) {

                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("latlong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue() + "";
                                String bal[] = s.split("-");
                                for (int m = 0; m < bal.length; m++)
                                    allat.add(bal[m].trim());
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
        if(i.getStringExtra("from").equals("train"))
        {

            FirebaseDatabase.getInstance().getReference().child("MainData").child("TrainingData").child(stitle).child("Centers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    centers.clear();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                        //     System.out.println(d1.getKey());
                        String s = d1.getKey();
                        s = s.replace("_a", "&");
                        s = s.replace("_m", "-");
                        centers.add(s);

                    }

                    for (int j = 0; j < centers.size(); j++) {

                        FirebaseDatabase.getInstance().getReference().child("LocationData").child(centers.get(j)).child("latlong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue() + "";
                                String bal[] = s.split("-");
                                for (int m = 0; m < bal.length; m++)
                                    allat.add(bal[m].trim());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void datafetched() {

        System.out.println("\n-\n-\n");
        System.out.println("blaaaaaaa " + allar.size());
        System.out.println(allat);



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
            }        adapter1.UpdateItemsList(allar, dist);


            Location la=new Location("t"+j);
                
                la.setLatitude(at);
                la.setLongitude(on);
            if(locat!=null) {
                float B = locat.distanceTo(la) / 1000;
                String aa = B + "";
                aa = aa.substring(0, 4);
                dist.add(B);
            }

            System.out.println(String.join(",", bro) + " " + allar.get(j));

            Marker m =mMap.addMarker((new MarkerOptions().position(new LatLng(at, on))).title(allar.get(j))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            if(m!=null)
            haMap.put(j,m);


        }

        for (int i = 0; i < dist.size(); i++) {
            for (int j = 0; j < dist.size() - i - 1; j++) {
                if (dist.get(j)>(dist.get(j + 1))) {
                    Float temp = dist.get(j);
                    dist.set(j, dist.get(j + 1));
                    dist.set(j + 1, temp);


                    String temp1 = allar.get(j);
                    allar.set(j, allar.get(j + 1));
                    allar.set(j + 1, temp1);

                    Marker m = haMap.get(j);
                    haMap.put(j, haMap.get(j + 1));
                    haMap.put(j + 1, m);


                }

                adapter1.UpdateItemsList(allar, dist);

            }

            adapter1.UpdateItemsList(allar, dist);

            //    adapter1.UpdateItemsList(allar, dist);

        }


        adapter1 = new MyRecyclerViewAdapter(MapsActivity.this, allar, dist);
        adapter1.setClickListener(MapsActivity.this);

        recyclerView.setAdapter(adapter1);
        //adapter1.UpdateItemsList(addr, dist);
        //recyclerView.setAdapter(adapter1);
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

            return;
        }


        geocoder = new Geocoder(this, Locale.getDefault());
       adapter1 = new MyRecyclerViewAdapter(MapsActivity.this, allar, dist);
        adapter1.setClickListener(MapsActivity.this);
        recyclerView.setNestedScrollingEnabled(true
        );
        recyclerView.setAdapter(adapter1);
        adapter1.UpdateItemsList(allar, dist);
        //    System.out.println(addr.size()+" "+addr);
        //  System.out.println(dist.size()+" "+dist);

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
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for Activity#requestPermissions for more details.
                                return;
                            }
                            mMap.setMyLocationEnabled(true);
                  // Creates a CameraPosition from the builder

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


//        System.out.println(location+" " +lat+" "+lon+" ");

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

    @Override
    public void onItemClick(View view, int position) {
        Marker m= haMap.get(position);
m.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(),15));
        Toast.makeText(MapsActivity.this, "hello"+position, Toast.LENGTH_SHORT).show();


    }


}