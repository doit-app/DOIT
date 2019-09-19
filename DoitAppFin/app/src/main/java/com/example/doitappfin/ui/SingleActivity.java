package com.example.doitappfin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doitappfin.R;
import com.example.doitappfin.utils.ProfileData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private TextView theading,twithou,twith,tinr,ttax,tdesc;
    private ImageView imgfin;
    private Button btproceed;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct;
    String mail="",num="",oiscomp="",sid="";

    String shead="",swith="",swithot="",stax="",sinr="0",sdesc="",simage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        theading=findViewById(R.id.textView9);
        tdesc=findViewById(R.id.textView10);
        twithou=findViewById(R.id.withoutTa);
        twith=findViewById(R.id.withTa);
        tinr=findViewById(R.id.inr);
        ttax=findViewById(R.id.tax);
        imgfin=findViewById(R.id.imageView3);
        btproceed=findViewById(R.id.button3);

        if(mAuth.getCurrentUser() == null){
            mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        }
        acct = GoogleSignIn.getLastSignedInAccount(this);

        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }

getData();
        btproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swith!=null && iscopmlete())
                startPayment(sinr);
            }
        });
        System.out.println("vv "+sid);

    }

    private void getData() {

        if(acct!= null) {
            System.out.println("hr");
            mail=acct.getEmail().replace(".","_");
            FirebaseDatabase.getInstance().getReference().child("ProfileData").child(mail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ProfileData obj=dataSnapshot.getValue(ProfileData.class);

                    oiscomp=(obj.getIscomplete());

                    System.out.println("in single acc"+oiscomp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            System.out.println("mr");

            SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doitAppfin.PRIVATEDATA", Context.MODE_PRIVATE);

            num=sp.getString("number","");
            // System.out.println(omail+" "+onum);

            if(num.length()==10)
            {


                FirebaseDatabase.getInstance().getReference().child("LoginData").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d1:dataSnapshot.getChildren())
                        {
                            if((d1.getValue().toString()).equals(num))
                            {
                                // System.out.println(d1.getValue().toString()+" "+onum);
                                mail=d1.getKey().toString().trim();

                                FirebaseDatabase.getInstance().getReference().child("ProfileData").child(mail).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        ProfileData obj=dataSnapshot.getValue(ProfileData.class);

                                        oiscomp=(obj.getIscomplete());

                                        System.out.println("in single ph"+oiscomp);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        }

    }

    private boolean iscopmlete() {

        if(oiscomp.equals("NO"))
{
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Please complete your profile and proceed ");
    builder.setPositiveButton("my profile", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            finish();

            startActivity(new Intent(SingleActivity.this,proflie.class));
        }
    });
    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });

    Dialog dialog = builder.create();
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
    return false;
}
else if(oiscomp.equals("YES"))
    return true;
else
    return false;


    }

    private void startPayment( String amount) {

Double amt=Double.parseDouble(amount);
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.logodoit);
        final Activity activity = this;
        try{ JSONObject options = new JSONObject();
            options.put("description","#DOIT_PAYMENT");
            options.put("Currency","INR");
            options.put("amount",amt*100);
            options.put("payment_capture", true);
            checkout.open(activity,options);
        }catch (JSONException e){
            e.printStackTrace();
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

        Intent i=getIntent();
        shead=i.getStringExtra("title");
        sdesc=i.getStringExtra("desc");
        simage=i.getStringExtra("image");
        sid=i.getStringExtra("id");
        String tot[]=i.getStringExtra("price").split("-");


        if(tot.length==2) {
            swithot = tot[0];
            swith = tot[1];
            twithou.setText(swithot);
            twith.setText(swith);



            if(swith.contains("$")){
                String s=swith.substring(0,swith.indexOf("$"));

                if(s.contains(".")){
                    s=s.substring(0,s.indexOf("."));
                    double p=Integer.parseInt(s)*72;
                    sinr=p+"";

                    tinr.setText(p+" Rs");
                }else{
                    double p=Integer.parseInt(s)*72;
sinr=p+"";
                    tinr.setText(p+" Rs");
                }


            }else
            if(swith.contains("Rs")){

sinr=swith.replace("Rs"," ").trim();
                tinr.setText(swith);
            }else {
                tinr.setText(swith);
            }


        }


        System.out.println(swith+" "+i.getStringExtra("price")+"  "+swithot);

        theading.setText(shead);
        tdesc.setText(sdesc);
        if(simage!=null)
        Glide.with(this)
                .load(simage).fitCenter().override(1000,1000).into(imgfin);


    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        String paymentId = paymentData.getPaymentId();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}
