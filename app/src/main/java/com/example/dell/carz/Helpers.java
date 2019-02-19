package com.example.dell.carz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Helpers {
    //static  Context context;
    public static ArrayList<Ad> list;
    public static ArrayList<Ad> allAds=new ArrayList<>();
    public static ArrayList<AdView> allAdView=new ArrayList<>();
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public static void getAds(Context c)

    {
        final Context context=c;
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        //list = new ArrayList<>();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).child("ads").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Ad ad = dataSnapshot.getValue(Ad.class);
                saveAdsAsync saveAdsAsync=new saveAdsAsync(context,ad);
                saveAdsAsync.execute();
                //list.add(ad);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }



    public static void allAd(Context c)

    {
        final Context context=c;
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        //list = new ArrayList<>();
        mDatabase.child("ads1").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Ad ad = dataSnapshot.getValue(Ad.class);
                allAds.add(ad);
                Bitmap image = null;
                try {
                    image = decodeFromFirebaseBase64(ad.image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                AdView adView = new AdView(ad.vehicleName, ad.city, ad.mileage, ad.price, ad.descrip, ad.name, ad.phone, ad.date, image, ad.longitude, ad.latitude, ad.mapName,ad.user);

                allAdView.add(adView);
                //allAds.add(ad);
                //list.add(ad);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    /*public static void saveAds(Context context){
        getAds();
        final AdDao mydb=MyDatabase.getAppDatabase(context).AdDao();
        saveAdsAsync save = new saveAdsAsync(context);
        save.execute();



    }*/

}