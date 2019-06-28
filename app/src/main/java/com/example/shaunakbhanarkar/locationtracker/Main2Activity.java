package com.example.shaunakbhanarkar.locationtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    Button savebutton;
    EditText editText1;
    EditText editText2;
    SharedPreferences sharedPref;
    String name1;
    String number1;
    final Editable[] name = new Editable[1];
    final Editable[] number = new Editable[1];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        savebutton = (Button) findViewById(R.id.savebutton);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        Context applicationContext = MainActivity.getContextOfApplication();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name[0] = editText1.getText();
                number[0] = editText2.getText();
                name1 = name[0].toString();
                number1 = number[0].toString();
                if (name1.length() > 0 && number1.length() == 13 && number1.charAt(0) == '+' && number1.charAt(1) == '9' && number1.charAt(2) == '1') {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.apply();
                    editor.putString("name", name1);
                    editor.putString("number", number1);
                    editor.apply();
                    Toast.makeText(getBaseContext(),"Trusted Contact Saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(),"Invalid Contact Details",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }




}
