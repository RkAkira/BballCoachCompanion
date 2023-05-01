package com.efrei.bballcoachcompanion;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

public class MainActivity extends AppCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        setContentView(R.layout.activity_main);

        Button language = findViewById(R.id.LanguageButton);
        LanguageManager lang = new LanguageManager(this);
        language.setOnClickListener(view -> {
            if(Locale.getDefault().getLanguage() == "en"){
                lang.updateResource("fr");
                recreate();
            }
            else{
                lang.updateResource("en");
                recreate();
            }
        });
    }

    public void locateMatches(View view) {
        Intent intent = new Intent(this, LocateMatchActivity.class);
        startActivity(intent);
    }

    public void pictureMatches(View view) {
        Intent intent = new Intent(this, PictureMatchActivity.class);
        startActivity(intent);
    }

    public void getMatches(View view) {
        Intent intent = new Intent(this, GetMatchActivity.class);
        startActivity(intent);
    }
}