package com.efrei.bballcoachcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        Intent intent = new Intent(this, pictureMatchActivity.class);
        startActivity(intent);
    }

    public void getMatches(View view) {
        Intent intent = new Intent(this, getMatchActivity.class);
        startActivity(intent);
    }
}