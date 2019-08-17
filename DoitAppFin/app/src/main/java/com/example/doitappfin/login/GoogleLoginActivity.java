package com.example.doitappfin.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitappfin.R;
import com.example.doitappfin.ui.MainWorkActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoogleLoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
public static final int RC_SIGN_IN=10;
    private FirebaseAuth mAuth;
private EditText Enum;
    String mail="";
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mAuth = FirebaseAuth.getInstance();
        TextView t=findViewById(R.id.textView6);
        login=findViewById(R.id.button2);
        Enum=findViewById(R.id.editText2);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(GoogleLoginActivity.this,MainWorkActivity.class));
            }
        });
        SignInButton b=findViewById(R.id.button);
// Configure Google Sign In
        GoogleSignInOptions gsop = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gsop);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {                                  //startActivity(new Intent(GoogleLoginActivity.this, MainWorkActivity.class));
            if(currentUser.getEmail()!=null)
                mail=currentUser.getEmail().replace(".","_");

            System.out.println(mail);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("LoginData");
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.hasChild(mail)) {

                    //    FirebaseDatabase.getInstance().getReference().child("LoginData").child(mail).setValue("empty");

                        Intent i=(new Intent(GoogleLoginActivity.this, Registration.class));
                        i.putExtra("mail",currentUser.getEmail());
                        startActivity(i);
                    }
                    else
                    {
                        finish();
                        Intent i=(new Intent(GoogleLoginActivity.this, MainWorkActivity.class));
                        startActivity(i);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






            //finish();
            //startActivity(new Intent(GoogleLoginActivity.this, MainWorkActivity.class));


        }
    }

    private void signIn() {
        System.out.println("su--log");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("cess--log");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                //  Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }



    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            System.out.println("sucess--log");
                           if(user!=null)
                           {
                              // finish();
                               //startActivity(new Intent(GoogleLoginActivity.this, MainWorkActivity.class));
if(acct.getEmail()!=null)
    mail=acct.getEmail().replace(".","_");

System.out.println(mail);
                               DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("LoginData");
                               rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot snapshot) {
                                       if (!snapshot.hasChild(mail)) {

                                        //   FirebaseDatabase.getInstance().getReference().child("LoginData").child(mail).setValue("empty");

                                           Intent i=(new Intent(GoogleLoginActivity.this, Registration.class));
                                           i.putExtra("mail",user.getEmail());
                                           startActivity(i);
                                       }
                                       else
                                       {
                                           finish();
                                           Intent i=(new Intent(GoogleLoginActivity.this, MainWorkActivity.class));
                                           startActivity(i);

                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });

                           }
                           else
                           {
                               Toast.makeText(GoogleLoginActivity.this, "heloo", Toast.LENGTH_SHORT).show();
                           }
                                                 } else {

                            System.out.println("fail--log");
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                 //           Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                       //     updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
