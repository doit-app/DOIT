package com.example.doitappfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.doitappfin.login.GoogleLoginActivity;
import com.example.doitappfin.ui.WorkActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    ImageView imv;


    Animation fromtop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String data[]={"Microsoft","Oracle", "CCNA", "CCNP","CCNA Wireless", "CCNA Security","CCNP Security","CCIE", "ISTQB - BCS", "Intermidiate - BCS","BCS Certified Tester Advanced Level Test Manager (TM12)", "ISTQB - ISQI", "Agile Tester Extension Level", "ISTQB - ITB","Comptia A+","Comptia N+","Comptia S+","VMWare","Palo Alto","SAS","Pega Systems","Check Point","Juniper","EMC","Citrix", "ITIL Foundation - People Cert", "ITIL Intermediate - People Cert", "ITIL Practioner - People Cert", "Prince 2 Foundation - People Cert", "Prince 2 Practioner - People Cert", "Cobit 5 Foundation - People Cert", "Prince 2 Agile Foundation - People Cert", "Prince 2 Agile Practioner - People Cert", "Exin Agile Scrum Foundation", "Exin Agile Scrum Master", "Exin DevOps Master", "Exin DevOps Foundation", "DevOps Foundation - DevOps Institute", "Certified Scrum Master - GAQM.org", "Certified Scrum Master - Scrum Alliance", "Professional Scrum Master 1 - Scrum.org", "Google Cloud Associate Cloud Engineer", "Google Cloud Professional Cloud Architect", "AWS Associate - VUE", "AWS Professional - VUE", "Python", "Service Now", "Salesforce", "CEH V10", "ECSA V10", "TOGAF", "Blue Prism"};

        String prefix[]={"INR","$","$","$","$","$","$","$","£","_","$","INR","INR","INR","$","$","$","$","$","$","$","$","$","$","$","£", "£", "£", "£", "£", "£", "£", "£", "INR", "INR", "INR", "INR", "INR", "$", "INR", "$", "$", "$", "$", "$", "INR", "$", "$", "$", "$", "$", "$"};

        String price[]={"5,664.00","150.00","383.50","354.00","354.00","354.00","354.00","531.00","98.00","0","227.74","4,750.00","4,751.00","5,000.00","145.14","211.22","224.20","118.00","188.80","212.40","206.50","295.00","236.00","236.00","354.00", "189.00", "283.00", "389.00", "189.00", "389.00", "189.00", "189.00", "389.00", "15,887.52", "16,980.00", "15,887.52", "15,887.52", "24,000.00", "128.00", "24,997.00", "150.00", "125.00", "200.00", "177.00", "354.00", "4,361.28", "150.00", "200.00", "1,414.82", "1,414.82", "584.10", "70.00"};

        String lname[]={"_dummy_","ORACLE","CCNA","CCNP","CCNA wireless","CCNA Security","CCNP Security","CCIE","ISTQB","ISTQB FOUNDATION LEVEL","ISTQB","ISQI","Agile Tester","ISTQB","Comptia A+","Comptia N+","Comptia S+","Vmware","Paloalto","Sas","Pega","Checkpoint","Juniper","DELL EMC CP","CITRIX","ITIL Foundation","IITL INTER","IITL PRACTIONER","PRINCE2 Foundation","Prince-2-practitioner","cobit","PRINCE AGILE","PRINCE AGILE","EXIN FOUNDATION","EXIN MASTER","DEVOPS MASTER","DEVOPS FONDATION","Devops Institute","Gaqm","Scrum Csm","Scrum Psm","Google Certified Cloud Manager","Google Certified Cloud Architech","Aws Developer Associate","Aws Professional","Python","Service Now","Salesforce","Ceh","ECSA","Togaf","BLUEPRISM"};


//  + =  %2B
// space = %20
/*
        if(data.length==prefix.length && data.length==price.length && lname.length==data.length)
        {


            for (int i=0;i<data.length;i++)
            {
                String link="https://firebasestorage.googleapis.com/v0/b/doitappfin.appspot.com/o/certdata%2F_dummy_.png?alt=media&token=9c82cfdc-80c4-4215-a87b-b7406601b53f";


                HashMap<String,String> hashMap=new HashMap<>();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("MainData").child("CertificationData");
                hashMap.put("desc","description");

                lname[i].replace("+","%2B");
                lname[i].replace(" ","%20");
                link=link.replace("_dummy_",lname[i]);

                hashMap.put("image",link);
                hashMap.put("price",price[i]+" "+prefix[i]);

                hashMap.put("adddetail","notfixed");

                data[i]=data[i].replace("-","_m");
                data[i]=data[i].replace("+","_p");
                data[i]=data[i].replace(".","_d");

                hashMap.put("title",data[i]);

                databaseReference.child(data[i]).setValue(hashMap);

            }
        }
     /*   HashMap<String,String> hashMap1=new HashMap<>();
        DatabaseReference databaseRef =FirebaseDatabase.getInstance().getReference().child("MainData").child("CertificationList");


        for (int i=0;i<data.length;i++) {

            hashMap1.put("cert_id_"+i,data[i]);

        }


        databaseRef.setValue(hashMap1);

*/



        imv=findViewById(R.id.spl1);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromtop.setDuration(1500);
        imv.setAnimation(fromtop);
        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                GoogleSignInAccount alr= GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doitApp.PRIVATEDATA", Context.MODE_PRIVATE);



                String   Name=sp.getString("name",  "oo");

                if(Name.equals("h"))
                {
                    finish();
                    startActivity(new Intent(MainActivity.this, WorkActivity.class));

                    System.out.println(alr);

                }
                else
                {
                    finish();
                    startActivity(new Intent(MainActivity.this, GoogleLoginActivity.class));

                    //  finish();
                    //    startActivity(new Intent(GoogleLoginActivity.this, GoogleLoginActivity.class));

                }


            }
        }, SPLASH_TIME_OUT);
    }

}




