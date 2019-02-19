package com.example.dell.carz;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.dell.carz.My_ads.items;

public class detail extends AppCompatActivity {
    int pos;
    private ImageButton imageButton;
    AdView adView;
    Ad a;
    TextView textView22;
    TextView textView24;
    TextView textView25;
    TextView textView26;
    TextView textView27;
    TextView textView28;
    TextView textView29;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        int x=getIntent().getIntExtra("data",0);
        pos=x;

        ImageView imageView=findViewById(R.id.imageView2);
        textView22=findViewById(R.id.textView22);
        textView24=findViewById(R.id.textView24);
        textView25=findViewById(R.id.textView25);
        textView26=findViewById(R.id.textView26);
        textView27=findViewById(R.id.textView27);
        textView28=findViewById(R.id.textView28);
        textView29=findViewById(R.id.textView29);

        imageButton=findViewById(R.id.imageButton4);
        int check=getIntent().getIntExtra("check",1);
        if(check==0)
        {
            imageButton.setVisibility(View.INVISIBLE);
            adView=items.get(x);
        }
        else if(check==2)
        {
            imageButton.setVisibility(View.INVISIBLE);
            adView=Favorites.list2.get(x);
        }
        else
        {
            adView=SearchableActivity.list1.get(x);
            a=SearchableActivity.list_ads.get(x);
        }
        imageView.setImageBitmap(adView.getPic());
        textView22.setText(adView.getVehicleName());
        textView24.setText(adView.getCity());
        textView25.setText(adView.getMileage());
        textView26.setText(adView.getPrice());
        textView27.setText(adView.getDescrip());
        textView28.setText(adView.getNamee());
        textView29.setText(adView.getPhonee());

    }
    public void onClick(View v)
    {
        //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", items.get(pos).latitude, items.get(pos).longitude);
        String geoUri = "http://maps.google.com/maps?q=loc:" + adView.latitude + "," +  adView.longitude + " (" + adView.mapName + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        startActivity(intent);

    }


    public void onClickFav(View v)
    {
        if(FirebaseAuth.getInstance().getUid().equals(adView.user))
        {
            Toast.makeText(this, "User cannot add his own ad to favorites", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            String date = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(new Date());
            //ref.child("ads").child(FirebaseAuth.getInstance().getUid()).child("date").setValue(date);
            String image=encodeBitmapAndSaveToFirebase(adView.pic);
            //Ad ad= new Ad(vehicle_name,reg_city,mileage,price,description,name,phone,date,image, FirebaseAuth.getInstance().getUid(),p.getLatLng().longitude,p.getLatLng().latitude,p.getName().toString());
            ref.child("users").child(FirebaseAuth.getInstance().getUid()).child("favorites").push().setValue(a);
            //ref.child("ads1").push().setValue(ad);
            Toast.makeText(this, "Ad added to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
       /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ads").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageUrl");
        ref.setValue(imageEncoded);
        /*try {
            Bitmap b = decodeFromFirebaseBase64(imageEncoded);
            im.setImageBitmap(b);

        }
        catch (IOException e) {
            e.printStackTrace();
        }*/

        return imageEncoded;

    }

}
