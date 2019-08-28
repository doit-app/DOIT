package com.example.doitappfin.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doitappfin.R;

public class SingleActivity extends AppCompatActivity {

    private TextView theading,twithou,twith,tinr,ttax,tdesc;
    private ImageView imgfin;
    private Button btproceed;

    String shead="",swith="",swithot="",stax="",sinr="",sdesc="",simage="";
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
        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }

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

                    tinr.setText(p+" Rs");
                }else{
                    double p=Integer.parseInt(s)*72;

                    tinr.setText(p+" Rs");
                }


            }else
            if(swith.contains("Rs")){


                tinr.setText(swith);
            }else {
                tinr.setText(swith);
            }


        }


        System.out.println(swith+" "+i.getStringExtra("price")+"  "+swithot);

        theading.setText(shead);
        tdesc.setText(sdesc);
        Glide.with(this)
                .load(simage).fitCenter().override(1000,1000).into(imgfin);


    }


}
