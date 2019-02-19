package com.example.dell.carz;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity  {

    private Button reg;
    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText phone;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    boolean check;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        check=false;
        c=this;

    }

    public void onClickReg(View v)
    {
        email= findViewById(R.id.editText);
        pass=findViewById(R.id.editText3);
        name=findViewById(R.id.editText4);
        phone=findViewById(R.id.editText2);
        final String e=email.getText().toString();
        final String p=pass.getText().toString();
        final String username=name.getText().toString();
        final String phonenum=phone.getText().toString();
        if(e.length()==0)
        {
            Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
        }
        if(p.length()==0)
        {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
        }

        if(username.length()==0)
        {
            Toast.makeText(this, "Please enter the username", Toast.LENGTH_SHORT).show();
        }
        if(phonenum.length()==0)
        {
            Toast.makeText(this, "Please enter the phoneNumber", Toast.LENGTH_SHORT).show();
        }
        if(e.length()!=0&&p.length()!=0&&username.length()!=0&&phonenum.length()!=0) {
            firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                            Toast.makeText(signUp.this, "User is Registered", Toast.LENGTH_SHORT).show();
                            User u = new User(username,e,p,phonenum);

                              /*   databaseReference.child("user").child(FirebaseAuth.getInstance().getUid()).child("name").setValue(username);
                                 databaseReference.child("user").child(FirebaseAuth.getInstance().getUid()).child("email").setValue(e);
                                 databaseReference.child("user").child(FirebaseAuth.getInstance().getUid()).child("password").setValue(p);
                                 databaseReference.child("user").child(FirebaseAuth.getInstance().getUid()).child("phoneNumber").setValue(phonenum);
*/
                              databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).push().setValue(u);
                              check=true;
                        Intent i = new Intent(c,Main2Activity.class);
                        startActivity(i);


                    }
                    else
                    {
                        Toast.makeText(signUp.this, "User is already registered "+ task.getException().toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }
}
