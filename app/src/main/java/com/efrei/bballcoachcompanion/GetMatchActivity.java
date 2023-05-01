package com.efrei.bballcoachcompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efrei.bballcoachcompanion.Modal.RencontreModal;
import com.efrei.bballcoachcompanion.databinding.GetMatchActivityBinding;
import java.util.ArrayList;

public class GetMatchActivity extends AppCompat {

    private DBHandler dbHandler;//Utilise le dbhandler pour faire les ajouts ou recupérer les données avec les methodes associés


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        setContentView(R.layout.get_match_activity);
        dbHandler = new DBHandler(GetMatchActivity.this);
        ArrayList<RencontreModal> rencontreModalArrayList = dbHandler.readRencontre();
        LinearLayout idRVContainer = findViewById(R.id.idRVRencontre);

        if (rencontreModalArrayList.isEmpty()) {
            TextView tvNoData = new TextView(this);
            tvNoData.setText(R.string.NoMatch);
            idRVContainer.addView(tvNoData);
        } else {
            for (RencontreModal rencontreModal : rencontreModalArrayList) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_layout, idRVContainer, false);
                TextView tv_Match = view.findViewById(R.id.tv_Match);
                tv_Match.setText(tv_Match.getText() + " " + rencontreModal.getEquipe1().toLowerCase() + " v " + rencontreModal.getEquipe2().toLowerCase());
                TextView tv_date = view.findViewById(R.id.tv_Date);
                tv_date.setText(rencontreModal.getDate());
                TextView tv_score = view.findViewById(R.id.tv_score);
                tv_score.setText(tv_score.getText() + " " + rencontreModal.getScore());
                TextView tv_best_scoreur = view.findViewById(R.id.tv_best_scoreur);
                tv_best_scoreur.setText(tv_best_scoreur.getText() + " " + rencontreModal.getBestScoreur() + " with " + rencontreModal.getPts_mis() + " points");

                idRVContainer.addView(view);
            }
        }
        //cela ne fonctionne pas avec la dernière version de dépandance mysql
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bbcoachcompanion?" +"user=root&password=root");
                    String sql = "SELECT * FROM rencontre";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        String first = resultSet.getString("col1");
                        String second = resultSet.getString("col2");

                        Log.d("DB", first);
                        Log.d("DB", second);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }}).start();*/
    }

    public void CreateMatch(View view) {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        startActivity(intent);
    }
}