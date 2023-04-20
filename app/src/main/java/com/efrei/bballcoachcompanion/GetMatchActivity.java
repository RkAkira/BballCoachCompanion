package com.efrei.bballcoachcompanion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efrei.bballcoachcompanion.Modal.RencontreModal;
import com.efrei.bballcoachcompanion.databinding.GetMatchActivityBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetMatchActivity extends Activity {

    private GetMatchActivityBinding viewBinding;
    private RencontreRVAdapter rencontreRVAdapter;
    private RecyclerView rencontreRv;
    private DBHandler dbHandler;//Utilise le dbhandler pour faire les ajouts ou recupérer les données avec les methodes associés


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = GetMatchActivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        dbHandler = new DBHandler(GetMatchActivity.this);
        ArrayList<RencontreModal> rencontreModalArrayList = dbHandler.readRencontre();
        rencontreRVAdapter = new RencontreRVAdapter(rencontreModalArrayList, GetMatchActivity.this);
        rencontreRv = findViewById(R.id.idRVRencontre);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GetMatchActivity.this, RecyclerView.VERTICAL, false);
        rencontreRv.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        rencontreRv.setAdapter(rencontreRVAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.0.2/bbcoachcompanion" + "user=root&password=root");

                    String sql = "SELECT * FROM RENCONTRE";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while(resultSet.next()){
                        String first = resultSet.getString("id_match");
                        String second = resultSet.getString("equipe_1");
                        String third = resultSet.getString("equipe_2");

                        Log.d("zaetataet", first);
                        Log.d("DB", second);
                        Log.d("DB", third);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }}).start();
    }
}