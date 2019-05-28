package com.mrbell.citycomerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrbell.citycomerce.Model.Users;
import com.mrbell.citycomerce.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {
    private EditText InputNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private CheckBox checkBoxRememberMe;
    private TextView AdminLink,NotAdminLink;
    public String parentDbName="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton=findViewById(R.id.login_btn);
        InputNumber=findViewById(R.id.login_phone_number_input);
        InputPassword=findViewById(R.id.login_password_input);
        checkBoxRememberMe=findViewById(R.id.remember_me_chkb);
        loadingBar=new ProgressDialog(this);
        AdminLink=findViewById(R.id.admin_panel_link);
        NotAdminLink=findViewById(R.id.no_admin_panel_link);

        Paper.init(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }

    private void LoginUser() {
        String phone=InputNumber.getText().toString();
        String password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter Phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,while we are checking the credential");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if(checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.Userphonekey,phone);
            Paper.book().write(Prevalent.Userpasswordkey,password);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){
                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
                            if(parentDbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Login Successful as Admin", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),AdminCatagoryActivity.class);
                                startActivity(intent);
                            }else if(parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }
                        }

                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Account with this "+phone+"does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
