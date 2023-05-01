package com.efrei.bballcoachcompanion;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.efrei.bballcoachcompanion.databinding.CreateMatchActivityBinding;

public class CreateMatchActivity extends AppCompat {

    private CreateMatchActivityBinding viewBinding;

    private DBHandler dbHandler;//Utilise le dbhandler pour faire les ajouts ou recupérer les données avec les methodes associés
    private EditText eq1, eq2,score, bestScorer, pointScored, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        viewBinding = CreateMatchActivityBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        eq1 = viewBinding.team1;
        eq2 = viewBinding.team2;
        score = viewBinding.score;
        bestScorer = viewBinding.bestScorer;
        pointScored = viewBinding.pointsScored;
        date = viewBinding.date;

        dbHandler = new DBHandler(CreateMatchActivity.this);
    }

    public void saveData(View view){
        String eq1S = eq1.getText().toString();
        String eq2S = eq2.getText().toString();
        String scoreS = score.getText().toString();
        String bScorerS = bestScorer.getText().toString();
        int pScoredS = parseInt(pointScored.getText().toString());
        String dateS = date.getText().toString();

        if (eq1S.isEmpty() && eq2S.isEmpty() && scoreS.isEmpty() && bScorerS.isEmpty()  && dateS.isEmpty()) {
            Toast.makeText(CreateMatchActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHandler.addNewMatch(eq1S, eq2S,scoreS,bScorerS,pScoredS,dateS);
        Toast.makeText(CreateMatchActivity.this, R.string.CourseValidation, Toast.LENGTH_SHORT).show();
        eq1.setText("");
        eq2.setText("");
        score.setText("");
        bestScorer.setText("");
        pointScored.setText("");
        date.setText("");

        Intent intent = new Intent(this, GetMatchActivity.class);
        startActivity(intent);

    }


}
