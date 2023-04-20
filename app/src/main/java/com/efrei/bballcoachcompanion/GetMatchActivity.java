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
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bbcoachcompanion";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

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

    }






    public void connectAndFetchData() {
        Connection connection = null;
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.jdbc.Driver");

            // Établir la connexion à la base de données
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Créer la requête SQL
            String sql = "SELECT * FROM rencontre ";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et afficher les valeurs des colonnes
            while (resultSet.next()) {
                String column1 = resultSet.getString("<COLUMN1_NAME>");
                String column2 = resultSet.getString("<COLUMN2_NAME>");

                // Faites quelque chose avec les valeurs récupérées, par exemple les afficher dans les logs
                Log.d("DB", "Column1: " + column1);
                Log.d("DB", "Column2: " + column2);


            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer la connexion à la base de données
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}