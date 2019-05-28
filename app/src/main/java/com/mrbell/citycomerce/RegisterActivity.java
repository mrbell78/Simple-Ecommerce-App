package com.mrbell.citycomerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccount_btn;
    private EditText InputName,InputPhoneNumber,InputPassword;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccount_btn=findViewById(R.id.register_createAccount_btn);
        InputName=findViewById(R.id.register_user_name_input);
        InputPhoneNumber=findViewById(R.id.register_phone_number_input);
        InputPassword=findViewById(R.id.register_password_input);
        loadingbar=new ProgressDialog(this);


        CreateAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
       String name= InputName.getText().toString();
       String phone =InputPhoneNumber.getText().toString();
       String password=InputPassword.getText().toString();

       if(TextUtils.isEmpty(name)){
           Toast.makeText(this, "Please Enter your name", Toast.LENGTH_SHORT).show();
       }
       if(TextUtils.isEmpty(phone)){
           Toast.makeText(this, "Please Enter your phone number", Toast.LENGTH_SHORT).show();
       }
       if(TextUtils.isEmpty(password)){
           Toast.makeText(this, "Please Enter your password", Toast.LENGTH_SHORT).show();
       }

       else{
           loadingbar.setTitle("Create Account");
           loadingbar.setMessage("Please wait, while we are checking your credential.");
           loadingbar.setCanceledOnTouchOutside(false);
           loadingbar.show();
           ValidatePhoneNumber(name,phone,password);
       }
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(phone).exists()){
                    HashMap<String,Object>userdatamap = new HashMap<>();
                    userdatamap.put("phone",phone);
                    userdatamap.put("password",password);
                    userdatamap.put("name",name);
                    RootRef.child("Users").child(phone).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingbar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network error please try again after sometime.....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(RegisterActivity.this, "This"+phone+"already exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try with another phone number", Toast.LENGTH_SHORT).show();

                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
