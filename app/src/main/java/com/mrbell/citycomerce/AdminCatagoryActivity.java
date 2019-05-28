package com.mrbell.citycomerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCatagoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView tshirt,sportsTshirt,femaleDress,sweaters;
    private ImageView glass,hatcaps,pursebag,shoes;
    private ImageView headphone,laptops,watches,mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_catagory);

        initialization();
        Onclickevent();
    }

    private void Onclickevent() {
        tshirt.setOnClickListener(this);
        sportsTshirt.setOnClickListener(this);
        femaleDress.setOnClickListener(this);
        sweaters.setOnClickListener(this);
        glass.setOnClickListener(this);
        hatcaps.setOnClickListener(this);
        pursebag.setOnClickListener(this);
        shoes.setOnClickListener(this);
        headphone.setOnClickListener(this);
        laptops.setOnClickListener(this);
        watches.setOnClickListener(this);
        mobiles.setOnClickListener(this);
    }

    private void initialization() {
        tshirt=findViewById(R.id.t_shirts);
        sportsTshirt=findViewById(R.id.sports);
        femaleDress=findViewById(R.id.female_dresses);
        sweaters=findViewById(R.id.sweters);
        glass=findViewById(R.id.glasses);
        hatcaps=findViewById(R.id.hats);
        pursebag=findViewById(R.id.purse_bagh);
        shoes=findViewById(R.id.shoes);
        headphone=findViewById(R.id.headphone);
        laptops=findViewById(R.id.laptops);
        watches=findViewById(R.id.watches);
        mobiles=findViewById(R.id.mobiles);
    }

    @Override
    public void onClick(View view) {

        int id =view.getId();

        switch (id){
            case R.id.t_shirts:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","Tshirts"));
                break;
            case R.id.sports:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","SportsTshirt"));
                break;
            case R.id.female_dresses:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","femaleDresses"));
                break;
            case R.id.sweters:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","sweters"));
                break;
            case R.id.glasses:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","glass"));
                break;
            case R.id.hats:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","hats"));
                break;
            case R.id.purse_bagh:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","pursebag"));
                break;
            case R.id.shoes:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","shoes"));
                break;
            case R.id.headphone:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","headphone"));
                break;
            case R.id.laptops:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","laptops"));
                break;
            case R.id.watches:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","watches"));
                break;
            case R.id.mobiles:
                startActivity(new Intent(getApplicationContext(),AdminAddNewProductActivity.class).putExtra("catagory","mobiles"));
                break;
        }

    }
}
