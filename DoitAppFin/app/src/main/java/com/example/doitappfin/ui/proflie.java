package com.example.doitappfin.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.doitappfin.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class proflie extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText name,dob,phone,city,yr,degree,school,face,email;
    Button b1,b2;
    SharedPreferences sharedpreferences;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Dob = "dobKey";
    public static final String City = "cityKey";
    public static final String Email = "emailKey";
    public static final String Year= "yearKey";
    public static final String Face = "faceKey";
    public static final String Degree = "DegreeKey";
    public static final String School = "schoolKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proflie);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        name=(EditText)findViewById(R.id.editTextName);
        dob=(EditText)findViewById(R.id.editDOB);
        phone=(EditText)findViewById(R.id.editphone);
        city=(EditText)findViewById(R.id.editcity);
        yr=(EditText)findViewById(R.id.editclass);
        degree=(EditText)findViewById(R.id.editboard);
        face=(EditText)findViewById(R.id.editface);
        school=(EditText)findViewById(R.id.editschool);
        email=(EditText)findViewById(R.id.editachievements);
        b1=(Button)findViewById(R.id.buttonSave);
        b2 =(Button)findViewById(R.id.buttonsignout);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n  = name.getText().toString();
                String ph  = phone.getText().toString();
                String date  = dob.getText().toString();
                String cit  = city.getText().toString();
                String clas  = yr.getText().toString();
                String deg  = degree.getText().toString();
                String dep  = face.getText().toString();
                String sch  = school.getText().toString();
                String em  = email.getText().toString();



                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Phone, ph);
                editor.putString(Email, em);
                editor.putString(Dob,date);
                editor.putString(City,cit);
                editor.putString(Year,clas);
                editor.putString(Degree,deg);
                editor.putString(Face,dep);
                editor.putString(School,sch);


                editor.commit();
                Toast.makeText(getApplicationContext(),"Thanks",Toast.LENGTH_LONG).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });
    }

    private void signout() {
        // Firebase sign out
        if(mAuth!=null){
            mAuth.signOut();
            Toast.makeText(this, "SIgned out" , Toast.LENGTH_SHORT).show();
        }

        // Google sign out

//        mGoogleSignInClient.signOut().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });
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
        getMenuInflater().inflate(R.menu.proflie, menu);
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
