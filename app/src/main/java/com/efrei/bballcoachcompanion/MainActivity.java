package com.efrei.bballcoachcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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