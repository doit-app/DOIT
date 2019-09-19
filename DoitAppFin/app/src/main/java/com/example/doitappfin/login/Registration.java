package com.example.doitappfin.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doitappfin.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    EditText Ename,Ephone,Email;
    Button submit;

    String Sname,Sphone,Semail;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    GoogleSignInClient mGoogleInCilients;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mGoogleInCilients = GoogleSignIn.getClient(this,gso);

        Ename=findViewById(R.id.entername);
        Ephone=findViewById(R.id.enterphone);
        Email=findViewById(R.id.entermail);
        submit=findViewById(R.id.submit);

        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connectedToNetwork()){
                    getdata();

                    if(notempty())
                    {

                        Toast.makeText(Registration.this,"success",Toast.LENGTH_SHORT).show();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = database.getReference().child("ProfileData").child(Semail.replace(".","_"));

DatabaseReference dbpay=database.getReference().child("Payment").child(Semail.replace(".","_"));
                        HashMap<String,String> hashMap=new HashMap<>();


                        hashMap.put("name",Sname);
                        hashMap.put("email",Semail);
                        hashMap.put("number",Sphone);
                        hashMap.put("date","NO");
                        hashMap.put("city","NO");
                        hashMap.put("addr","NO");
                        hashMap.put("sex","NO");
                        hashMap.put("image","https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
                        hashMap.put("iscomplete","NO");
                        mRef.setValue(hashMap);

                        dbpay.child("Training").setValue("empty");
                        dbpay.child("Certification").setValue("empty");

                        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doitAppfin.PRIVATEDATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("number", Sphone);
                        editor.apply();

                        FirebaseDatabase.getInstance().getReference().child("LoginData").child(Semail.replace(".","_")).setValue(Sphone);
                        Intent i = new Intent(Registration.this, otp.class);
                        i.putExtra("number",Sphone);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }

                }else{ NoInternetAlertDialog();
                }



            }
        });


    }

    private boolean notempty() {

      //  Toast.makeText(Registration.this,Sname+","+Semail+","+Sphone,Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(Semail) )
            return false;
        if(!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
            Email.setError("Enter a valid mail");
            return false;}

        if(TextUtils.isEmpty(Sname) || Sname.length()<3) {
          Ename.setError("Enter a valid name");
            return false;
        } if(TextUtils.isEmpty(Sphone) || Sphone.length()!=10) {
         Ephone.setError("Enter a valid mobile number");
            return false;
        }


        return true;
    }

    private void getdata() {

        Sname=Ename.getText().toString();
        Sphone=Ephone.getText().toString();
        Semail=Email.getText().toString();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
        String number=getIntent().getExtras().getString("number");
        String mail=getIntent().getExtras().getString("mail");
        Ephone.setText(number);
        Email.setText(mail);
    }
}
