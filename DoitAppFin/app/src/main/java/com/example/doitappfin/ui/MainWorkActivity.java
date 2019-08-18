package com.example.doitappfin.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.doitappfin.R;
import com.example.doitappfin.login.GoogleLoginActivity;
import com.example.doitappfin.utils.MyCustomPagerAdapter;
import com.example.doitappfin.utils.RecyclerViewAdapter;
import com.example.doitappfin.utils.UserDetails;
import com.example.doitappfin.utils.certModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainWorkActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout indicator;
    private ViewPager viewPager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GoogleSignInClient mGoogleInCilients;
    ImageView photoTV;
    TextView heademail;
    RecyclerView recyclerView,recyclerView1;
    RelativeLayout relativeLayout;
    GoogleSignInAccount acct;
    RecyclerViewAdapter recyclerViewAdapterTrain,recyclerViewAdapterCert;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
private ArrayList<certModel> cert,train;

    RecyclerView.LayoutManager recylerViewLayoutManager;
    int images[] = {R.drawable.apple, R.drawable.blue, R.drawable.mango, R.drawable.orange};
    MyCustomPagerAdapter myCustomPagerAdapter;
    String[]  fruits={"apple","grapes","mango","orange"};
    String[] subjects = {"ANDROID","PHP","BLOGGER","WORDPRESS","JOOMLA","ASP.NET","JAVA","C++","MATHS","HINDI","ENGLISH"};
    private int[] myImageList = new int[]{R.drawable.apple, R.drawable.mango,R.drawable.straw, R.drawable.pineapple,R.drawable.orange,R.drawable.blue,R.drawable.water};
    private String[] myImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry","Watermelon"};

    private TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_work);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGoogleInCilients = GoogleSignIn.getClient(this,gso);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        photoTV = (ImageView) header.findViewById(R.id.imageView123);
        heademail = header.findViewById(R.id.textView123);



                cert=new ArrayList<certModel>();
                train=new ArrayList<certModel>();



                acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!= null) {
            UserDetails.ProfilePhoto = acct.getPhotoUrl();
            UserDetails.name = acct.getDisplayName();
            Glide.with(this).load(acct.getPhotoUrl()).into(photoTV);
            heademail.setText(acct.getDisplayName());
        }
        tv1=findViewById(R.id.t1);
        tv2=findViewById(R.id.t2);

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainWorkActivity.this,TrainCertActivity.class);
                i.putExtra("val","Certification");
                startActivity(i);
            }
        });


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainWorkActivity.this,TrainCertActivity.class);
                i.putExtra("val","Training");
                startActivity(i);

            }
        });



        indicator = (TabLayout) findViewById(R.id.indicator);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        indicator.setupWithViewPager(viewPager, true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);


        FirebaseDatabase.getInstance().getReference().child("PopularData").child("Training").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                train.clear();

                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    certModel obj=d1.getValue(certModel.class);
                    train.add(obj);
                }
                recyclerViewAdapterTrain = new RecyclerViewAdapter(MainWorkActivity.this, train);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("PopularData").child("Certification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cert.clear();

                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    certModel obj=d1.getValue(certModel.class);
                    cert.add(obj);

                    System.out.println(obj);
                }
                recyclerViewAdapterCert = new RecyclerViewAdapter(MainWorkActivity.this, cert);
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainWorkActivity.SliderTimer(), 6000, 7000);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_work, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startActivity(new Intent(getApplicationContext(),proflie.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doitAppfin.PRIVATEDATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("number","___");
            editor.apply();

            if(mAuth!=null){

                mAuth.signOut();

                mGoogleInCilients.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });



                Toast.makeText(this, "SIgned out" , Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, GoogleLoginActivity.class));
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainWorkActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < images.length -1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void setAdapter() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        recyclerView1.setAdapter(recyclerViewAdapterCert);

        recyclerView.setAdapter(recyclerViewAdapterTrain);


        recyclerViewAdapterCert.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, certModel model) {

            }
        });

        recyclerViewAdapterTrain.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, certModel model) {

            }
        });


    }

}
