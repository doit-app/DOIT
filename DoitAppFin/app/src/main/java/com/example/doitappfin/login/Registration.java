package com.example.doitappfin.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


        String number=getIntent().getExtras().getString("number");
        String mail=getIntent().getExtras().getString("mail");
        Ephone.setText(number);
        Email.setText(mail);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                if(notempty())
                {

                    Toast.makeText(Registration.this,"success",Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mRef = database.getReference().child("ProfileData").child(Semail.replace(".","_"));


                    HashMap<String,String> hashMap=new HashMap<>();

                    hashMap.put("name",Sname);
                    hashMap.put("email",Semail);
                    hashMap.put("phone",Sphone);
                    hashMap.put("dob","");
                    hashMap.put("city","");
                    hashMap.put("addr","");
                    hashMap.put("sex","");
                    mRef.setValue(hashMap);

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

            }
        });


    }

    private boolean notempty() {

        Toast.makeText(Registration.this,Sname+","+Semail+","+Sphone,Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(Semail) )
            return false;
        if(!Patterns.EMAIL_ADDRESS.matcher(Semail).matches())
            return false;

        if(TextUtils.isEmpty(Sname) || Sname.length()<3)
            return false;
        if(TextUtils.isEmpty(Sphone) || Sphone.length()!=10)
            return false;



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
}
