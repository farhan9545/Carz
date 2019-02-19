package com.example.dell.carz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.dell.carz.SearchableActivity.list1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link My_ads.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link My_ads#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_ads extends Fragment implements RecyclerView.OnItemTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseReference mDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static ArrayList<AdView> items = new ArrayList<>();
    ArrayList<Ad> ads = new ArrayList<>();
    AdAdapter mAdapter;

    GestureDetector gestureDetector;
    private OnFragmentInteractionListener mListener;

    public My_ads() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment My_ads.
     */
    // TODO: Rename and change types and number of parameters
    public static My_ads newInstance(String param1, String param2) {
        My_ads fragment = new My_ads();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        items.clear();

        ads.clear();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //items.clear();
        //ads.clear();
        //list1.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_my_ads, container, false);
        final View rootView = inflater.inflate(R.layout.fragment_my_ads, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rcv);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        AdDownloadAsync adDownloadAsync = new AdDownloadAsync(items, getActivity());
        if ((Helpers.isInternetAvailable() || Helpers.isNetworkConnected(getActivity()))) {

            mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).child("ads").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                    Ad ad = dataSnapshot.getValue(Ad.class);
                    Bitmap image = null;
                    try {
                        image = decodeFromFirebaseBase64(ad.image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AdView adView = new AdView(ad.vehicleName, ad.city, ad.mileage, ad.price, ad.descrip, ad.name, ad.phone, ad.date, image, ad.longitude, ad.latitude, ad.mapName,ad.user);
                    //items.add(new AdView(ad.vehicleName, ad.city, ad.mileage, ad.price, ad.descrip, ad.name, ad.phone, ad.date, image,ad.longitude,ad.latitude,ad.mapName));

                    ArrayList<AdView> copy = new ArrayList<>();

                    for (AdView a : items) {
                        copy.add(a);
                    }

                    boolean check = true;
                    if (copy.size() > 0) {
                        for (AdView a : copy) {
                            if (a.vehicleName == adView.vehicleName && a.city == adView.city && a.mileage == adView.mileage && a.price == adView.price && a.descrip == adView.descrip)
                                check = false;
                        }

                        if (check) {
                            items.add(adView);
                            mAdapter.notifyDataSetChanged();
                        }

                    } else {
                        items.add(adView);
                        mAdapter.notifyDataSetChanged();
                    }


                    //mAdapter.notifyDataSetChanged();
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
        } else {

            adDownloadAsync.execute();

        }

        gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    // checker(rv1.getChildAdapterPosition(child));
                    Integer x = recyclerView.getChildAdapterPosition(child);

                    Intent i = new Intent(getActivity(), detail.class);

                    i.putExtra("data", x);
                    i.putExtra("check",0);//off

                    startActivity(i);


                }
                return true;


            }
        }

        );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter = new AdAdapter(items, R.layout.ad_layout);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(this);
        return rootView;

        //return null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
