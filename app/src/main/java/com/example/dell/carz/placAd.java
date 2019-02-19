package com.example.dell.carz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class placAd extends AppCompatActivity {

    ImageView im;
    EditText editText10;
    EditText editText11;
    EditText editText14;
    EditText editText15;
    EditText editText16;
    EditText editText17;
    EditText editText18;
    Bitmap imageBitmap;
    TextView textView;
    Place p=null;
    Tracker mTracker;
    int PLACE_PICKER_REQUEST = 2;
    String resultSpeech = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plac_ad);
        //im=findViewById(R.id.imv);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        editText10=findViewById(R.id.editText10);
        editText11=findViewById(R.id.editText11);
        editText14=findViewById(R.id.editText14);
        editText15=findViewById(R.id.editText15);
        editText16=findViewById(R.id.editText16);
        editText17=findViewById(R.id.editText17);
        editText18=findViewById(R.id.editText18);
        imageBitmap=null;

    }


    public void onClick(View v)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + "PlaceAd");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
    .setAction("Share")
                .build());

    }

    public void onClickLocation(View v)
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onClickVoice(View v)
    {
        int REQUEST_CODE = 1;
        String DIALOG_TEXT = "Speech recognition demo";
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, DIALOG_TEXT);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        startActivityForResult(intent, 3);
    }



    public void onClickPost(View v)
    {
        //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        //databaseReference.child("ads").child(FirebaseAuth.getInstance().getUid()).child("name").setValue(user.getDisplayName());
        String vehicle_name=editText10.getText().toString();
        String reg_city=editText11.getText().toString();
        String mileage=editText14.getText().toString();
        String price=editText15.getText().toString();
        String description=editText16.getText().toString();
        String name=editText18.getText().toString();
        String phone=editText17.getText().toString();
        if(imageBitmap==null)
        {
            Toast.makeText(this, "Please capture an iamge first", Toast.LENGTH_SHORT).show();
        }
        else if(p==null)
        {
            Toast.makeText(this, "Please set location first", Toast.LENGTH_SHORT).show();
        }
        else {
            if (vehicle_name.length() != 0 && reg_city.length() != 0 && mileage.length() != 0 && price.length() != 0 && description.length() != 0 && name.length() != 0 && phone.length() != 0) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                String date = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(new Date());
                //ref.child("ads").child(FirebaseAuth.getInstance().getUid()).child("date").setValue(date);
                String image=encodeBitmapAndSaveToFirebase(imageBitmap);
                Ad ad= new Ad(vehicle_name,reg_city,mileage,price,description,name,phone,date,image,FirebaseAuth.getInstance().getUid(),p.getLatLng().longitude,p.getLatLng().latitude,p.getName().toString());
                ref.child("users").child(FirebaseAuth.getInstance().getUid()).child("ads").push().setValue(ad);
                ref.child("ads1").push().setValue(ad);
                Toast.makeText(this, "Ad Posted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,Main2Activity.class);
                startActivity(i);

            } else {
                Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> speech;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
           // mImageLabel.setImageBitmap(imageBitmap);
            
        }
        else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", place.getLatLng().latitude, place.getLatLng().longitude);
                String geoUri = "http://maps.google.com/maps?q=loc:" + place.getLatLng().latitude + "," +  place.getLatLng().longitude + " (" + place.getName() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
               //startActivity(intent);
                Toast.makeText(placAd.this, toastMsg, Toast.LENGTH_LONG).show();
                p=place;
            }
        }
        else if(requestCode==3)
        {
            if (resultCode == RESULT_OK) {
                 speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultSpeech = speech.get(0);
                    editText16.setText(resultSpeech);
                    //you can set resultSpeech to your EditText or TextView

            }

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
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }




}
