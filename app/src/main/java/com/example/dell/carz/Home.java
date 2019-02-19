package com.example.dell.carz;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Home extends Fragment  implements View.OnClickListener,SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
    Context c;
    Button b;
    private OnFragmentInteractionListener mListener;
    TextToSpeech tts;
    ListView list;
    ListViewAdapter adapter;
    android.widget.SearchView editsearch;

    String[] carNameList;
    ArrayList<Car_name> arraylist = new ArrayList<Car_name>();


    public Home() {
        // Required empty public constructor
    }

    public void setC(Context c) {
        this.c = c;
    }



    public void onClick(View v)
    {
        Intent i = new Intent(getActivity(),placAd.class);
        tts=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        tts.speak("Place an ad", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });







    /*TextToSpeech tts = new TextToSpeech(getActivity(), this);
        tts.setLanguage(Locale.US);
        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null);*/
        startActivity(i);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        b=view.findViewById(R.id.button2);
        b.setOnClickListener(this);



        carNameList = new String[]{"City","Civic","cultux","mehran","swift","accord","rx-8","A3 Sedan","R8","alto"};

        // Locate the ListView in listview_main.xml
        list = (ListView)view.findViewById(R.id.listview);




        for (int i = 0; i < carNameList.length; i++) {
            Car_name car_name = new Car_name(carNameList[i]);
            // Binds all strings into an array
            arraylist.add(car_name);
        }
        list.setVisibility(View.INVISIBLE);
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getActivity(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch =  view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener((android.widget.SearchView.OnQueryTextListener) this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                //Toast.makeText(getActivity(), ListViewAdapter.Car_nameList.get(position).getCarname(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),SearchableActivity.class);
                i.putExtra("query",ListViewAdapter.Car_nameList.get(position).getCarname());
                startActivity(i);

            }

        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(),SearchableActivity.class);
        i.putExtra("query",query);
        startActivity(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        list.setVisibility(View.VISIBLE);
        String text = newText;

        if(text.length()==0)
        {
            list.setVisibility(View.INVISIBLE);
        }
        adapter.filter(text);
        return false;
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
