package com.efrei.bballcoachcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void locateMatches(){
        Intent intent = new Intent(this, LocateMatchActivity.class);
        startActivity(intent);
    }
    public void pictureMatches(){
        Intent intent = new Intent(this, pictureMatchActivity.class);
        startActivity(intent);
    }
    public void getMatches(){
        Intent intent = new Intent(this, getMatchActivity.class);
        startActivity(intent);
    }
}