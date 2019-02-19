package com.example.dell.carz;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dell.carz.Helpers.allAdView;
import static com.example.dell.carz.Helpers.allAds;

public class SearchableActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    AdAdapter mAdapter;

    GestureDetector gestureDetector;

    static ArrayList<AdView> list1= new ArrayList<>();
    static ArrayList<Ad> list_ads=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_ads.clear();
        list1.clear();
        setContentView(R.layout.activity_searchable);

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv1);

        mAdapter = new AdAdapter(list1, R.layout.ad_layout);
        // 4. set adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 5. set item animator to DefaultAnimator
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(this);
        recyclerView.setAdapter(mAdapter);






        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    // checker(rv1.getChildAdapterPosition(child));
                    //Toast.makeText(SearchableActivity.this, "abc", Toast.LENGTH_SHORT).show();
                    Integer x = recyclerView.getChildAdapterPosition(child);
                    int y=1;


                    Intent i = new Intent(SearchableActivity.this, detail.class);

                    i.putExtra("data", x);
                    i.putExtra("check",1);//on

                    startActivity(i);

                    return true;

                }
                else {
                    Toast.makeText(SearchableActivity.this, "null", Toast.LENGTH_SHORT).show();

                    return false;
                }

            }
        }

        );

        String query=getIntent().getStringExtra("query");
        //Toast.makeText(this,""+query,Toast.LENGTH_SHORT).show();

        for(int i=0;i<allAdView.size();i++)
        {
            if(allAdView.get(i).vehicleName.equalsIgnoreCase(query))
            {


                AdView adView=allAdView.get(i);
                ArrayList<AdView> copy = new ArrayList<>();
                for (AdView a : list1) {
                    copy.add(a);
                }

                boolean check = true;
                if (copy.size() > 0) {
                    for (AdView a : copy) {
                        if (a.vehicleName == adView.vehicleName && a.city == adView.city && a.mileage == adView.mileage && a.price == adView.price && a.descrip == adView.descrip)
                            check = false;
                    }

                    if (check) {
                        list1.add(allAdView.get(i));
                        list_ads.add(allAds.get(i));
                    }

                } else {
                    list1.add(allAdView.get(i));
                    list_ads.add(allAds.get(i));
                }


            }
        }
        if(list_ads.size()==0)
        {
            Toast.makeText(this, "No Ads found", Toast.LENGTH_SHORT).show();
        }



     //   Toast.makeText(this,"list1 size "+list1.size(),Toast.LENGTH_SHORT).show();



    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //list1.clear();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);

        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
