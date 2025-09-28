package com.prac.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button addbtn,highscore;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addbtn = findViewById(R.id.add);
        textView = findViewById(R.id.quiz);
        highscore =findViewById(R.id.highscores);

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscoreintent = new Intent(MainActivity.this,HighScore.class);
                startActivity(highscoreintent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the QuizActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, Addition.class);
                startActivity(intent);
            }
        });
    }


    public void subtractionQuiz(View view) {

        // Start the QuizActivity when the button is clicked
        Intent intent = new Intent(MainActivity.this, Subtraction.class);
        startActivity(intent);

    }

    public void multiplicationQuiz(View view) {
        // Start the QuizActivity when the button is clicked
        Intent intent = new Intent(MainActivity.this, Multiplication.class);
        startActivity(intent);
    }

    public void divisionQuiz(View view) {
        // Start the QuizActivity when the button is clicked
        Intent intent = new Intent(MainActivity.this, Division.class);
        startActivity(intent);
    }

    public void mixQuiz(View view) {
        // Start the QuizActivity when the button is clicked
        Intent intent = new Intent(MainActivity.this, Mix.class);
        startActivity(intent);
    }



}
