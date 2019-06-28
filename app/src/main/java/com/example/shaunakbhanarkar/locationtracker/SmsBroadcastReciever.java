package com.example.shaunakbhanarkar.locationtracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;




class SmsBroadcastReceiver extends BroadcastReceiver{

    public static final String SMS_BUNDLE = "pdus";
    public String address = "";
    SmsManager smsManager = SmsManager.getDefault();
    public String smsBody = "";
    SharedPreferences sharedPref;
    String trusted ="";

    static final int var = 0;
    LocationManager locationManager;
    String latitude, longitude;
    Location loc;


    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }

            Context applicationContext = MainActivity.getContextOfApplication();

            sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);



            // = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
            trusted = sharedPref.getString("number",null);

            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},var);
            }

            locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
            //provider = locationManager.getBestProvider(new Criteria(), false);
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            latitude = String.valueOf(loc.getLatitude());
            longitude = String.valueOf(loc.getLongitude());


            if (trusted.equals(address)) {

                if (smsBody.contains("location") || smsBody.contains("Location") || smsBody.contains("LOCATION")) {
                    Toast.makeText(context, "Location sent to trusted contact", Toast.LENGTH_SHORT).show();
                    sendmessage();
                    MainActivity.statusdisplay.setText("SMS Status" + "\n" +"Location was sent to Trusted Contact");
                    return;
                }

            }

            MainActivity.statusdisplay.setText("SMS Status" + "\n" +"Trusted Contact/Keyword not detected"); }



        }




    public void sendmessage()
    {

        smsManager.sendTextMessage(address, null, "Latitude: "+latitude+"\n"+"Longitude: "+longitude, null, null);

    }

}

