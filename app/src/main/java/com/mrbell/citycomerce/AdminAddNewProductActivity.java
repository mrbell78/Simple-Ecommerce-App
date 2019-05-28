package com.mrbell.citycomerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CatagoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CatagoryName=getIntent().getExtras().get("catagory").toString();
        Toast.makeText(this, CatagoryName+" is clicked", Toast.LENGTH_SHORT).show();
    }
}
