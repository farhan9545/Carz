package com.example.dell.carz;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.solver.widgets.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class saveAdsAsync extends AsyncTask<Void, Void, Void> {


    Context context;

    MyDatabase db;
    Ad ad;

    public saveAdsAsync(Context c, Ad ad) {
        context = c;

        db = MyDatabase.getAppDatabase(c);
        this.ad=ad;

    }


    @Override
    protected Void doInBackground(Void... v) {
        /*Ad ad[]=new Ad[Helpers.list.size()];
        for(int i=0;i<Helpers.list.size();i++)
        {
            ad[i]= Helpers.list.get(i);
        }*/
        db.AdDao().insertAll(ad);

        return null;
    }


}