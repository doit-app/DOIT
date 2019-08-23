package com.example.doitappfin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    }


    System.out.println(swith+" "+i.getStringExtra("price")+"  "+swithot);

    theading.setText(shead);
    tdesc.setText(sdesc);
        Glide.with(this)
                .load(simage).fitCenter().override(1000,1000).into(imgfin);
    }


}
