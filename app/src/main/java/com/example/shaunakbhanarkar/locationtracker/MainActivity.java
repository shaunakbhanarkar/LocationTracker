package com.example.shaunakbhanarkar.locationtracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;

public class MainActivity extends AppCompatActivity  {

    Button deletebutton;
    SharedPreferences sharedPref;
    TextView contactdisplay, locationdisplay;
    private static final int SMS_PERMISSION_CODE = 0;
    static final int var = 0;
    LocationManager locationManager;
    String latitude, longitude;
    Location loc;

    public static Context contextOfApplication;


    static TextView statusdisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},var);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //provider = locationManager.getBestProvider(new Criteria(), false);
        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        latitude = String.valueOf(loc.getLatitude());
        longitude = String.valueOf(loc.getLongitude());

        statusdisplay = (TextView) findViewById(R.id.statusdisplay);
        deletebutton = (Button) findViewById(R.id.deletebutton);
        contactdisplay = (TextView) findViewById(R.id.contactdisplay);
        locationdisplay = (TextView) findViewById(R.id.locationdisplay);
        locationdisplay.setText("Current Location:"+"\n"+"Latitude: "+latitude+"\n"+"Longitude: "+longitude);

        Context applicationContext = MainActivity.getContextOfApplication();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);

        if(sharedPref.getString("name",null)==(null) && sharedPref.getString("number",null)==(null))
        {
            contactdisplay.setText("No Trusted Contact Saved");
        }
        else {
            contactdisplay.setText("Trusted Contact:" + "\n" + "Name: " + sharedPref.getString("name", null) + "\n" + "Number: " + sharedPref.getString("number", null));
        }

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contactdisplay.getText().equals("No Trusted Contact Saved"))
                {
                    Toast.makeText(getBaseContext(),"Add Trusted Contact first",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.apply();
                    contactdisplay.setText("No Trusted Contact Saved");
                    Toast.makeText(getBaseContext(), "Trusted Contact Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }



    private void requestReadAndSendSmsPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},
                SMS_PERMISSION_CODE);
    }



    public static Context getContextOfApplication(){
        return contextOfApplication;
    }


    public void open1(View view){
        Intent intent = new Intent (this, Main2Activity.class);
        startActivity(intent);
    }
}
