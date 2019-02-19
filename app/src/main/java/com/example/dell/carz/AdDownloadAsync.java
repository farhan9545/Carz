package com.example.dell.carz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.ArrayList;

public class AdDownloadAsync extends AsyncTask<Void, Void, Void> {


    Context context;
    ArrayList<AdView> ads;
    ArrayList<Ad> list;
    MyDatabase db;

    public AdDownloadAsync(ArrayList<AdView> items,Context c ) {

        context = c;
        ads=items;
        db= MyDatabase.getAppDatabase(c);
        list = new ArrayList<>();
    }


    @Override
    protected Void doInBackground(Void... v) {
        list= (ArrayList)db.AdDao().getAll();
        for(int i=0;i<list.size();i++)
        {
            Ad ad = list.get(i);
            Bitmap image = null;
            try {
                image = decodeFromFirebaseBase64(ad.image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ads.add(new AdView(ad.vehicleName, ad.city, ad.mileage, ad.price, ad.descrip, ad.name, ad.phone, ad.date, image,ad.longitude,ad.latitude,ad.mapName,ad.user));
        }
        return null;
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException
    {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
