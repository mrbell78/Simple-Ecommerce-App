package com.mrbell.citycomerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrbell.citycomerce.Model.Users;
import com.mrbell.citycomerce.Prevalent.Prevalent;

import org.w3c.dom.Text;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private Button joinNowButton,loginButton;
    private ProgressDialog loadingBar;
    private String parentDbName=new LoginActivity().parentDbName;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton=findViewById(R.id.main_join_btn);
        loginButton=findViewById(R.id.main_login_btn);
        loadingBar=new ProgressDialog(this);
        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        String Userphonekey = Paper.book().read(Prevalent.Userphonekey);
        String Userpasswordkey= Paper.book().read(Prevalent.Userpasswordkey);
        if(Userphonekey!="" && Userpasswordkey!=""){
            if(!TextUtils.isEmpty(Userphonekey) && !TextUtils.isEmpty(Userpasswordkey)){
                AllowAccess(Userphonekey,Userpasswordkey);

                loadingBar.setTitle("Keep me logedin");
                loadingBar.setMessage("Please wait,while we are checking the credential");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: parentDbname =="+parentDbName);

                    Users userdata= dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if(userdata.getPhone().equals(phone)){
                        if( userdata.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "u signed in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "password or network error", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                    else{
                        Paper.book().destroy();
                        Toast.makeText(MainActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
