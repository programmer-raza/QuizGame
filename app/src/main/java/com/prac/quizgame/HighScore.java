package com.prac.quizgame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HighScore extends AppCompatActivity {
    SharedPreferences prefs;

    private TextView scoreAdd,scoreSub,scoreMult,scoreDiv,scoreMix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_high_score);
        prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        scoreAdd = findViewById(R.id.scoreadd);
        scoreSub = findViewById(R.id.scoresub);
        scoreMult =findViewById(R.id.scoremult);
        scoreDiv = findViewById(R.id.scorediv);
        scoreMix = findViewById(R.id.scoremix);


        scoreAdd.setText(String.valueOf(prefs.getInt("HighScore_Addition", 0)));
        scoreSub.setText(String.valueOf(prefs.getInt("HighScore_Subtraction", 0)));
        scoreMult.setText(String.valueOf(prefs.getInt("HighScore_Multiplication", 0)));
        scoreDiv.setText(String.valueOf(prefs.getInt("HighScore_Division", 0)));
        scoreMix.setText(String.valueOf(prefs.getInt("HighScore_Mix", 0)));
    }
}