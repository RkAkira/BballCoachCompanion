package com.efrei.bballcoachcompanion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.efrei.bballcoachcompanion.databinding.GetMatchActivityBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMatchActivity extends Activity {

    private GetMatchActivityBinding viewBinding;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = GetMatchActivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

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