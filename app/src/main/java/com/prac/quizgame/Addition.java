package com.prac.quizgame;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Addition extends AppCompatActivity {
    TextView questionTextView, answerview,Timertext,lifeLine,ScoreTxt;
    Button backButton, option1, option2, option3, option4;
    ArrayList<Button> buttons;
    int num1, num2, correctAnswer,score =0;
    Random random;
    int TotallifeLine = 5;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        questionTextView = findViewById(R.id.question);

        ScoreTxt = findViewById(R.id.Score);
        lifeLine = findViewById(R.id.lifeline);
        Timertext = findViewById(R.id.timertxt);
        answerview = findViewById(R.id.answercheck);
        backButton = findViewById(R.id.backButton); // Initialize the back button
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        buttons = new ArrayList<>();
        random = new Random();

        lifeLine.setText("Life Lines: "+TotallifeLine);
        generateQuestion();
        startTimer();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQuitDialog();
            }
        });
    }
    private void startTimer() {
        countDownTimer =  new CountDownTimer(timeLeftInMillis, 1000) { // 10 seconds, tick every second
            @Override
            public void onTick(long millisUntilFinished) {
                Timertext.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                Timertext.setText("Time's up!");
                GameOverDilog();
            }
        }.start();

    }
    private void generateQuestion() {
        num1 = random.nextInt(50); // Change the range as needed
        num2 = random.nextInt(50);
        correctAnswer = num1 + num2;
        assignCorretAnswer(Integer.toString(correctAnswer));

        String question = num1 + " + " + num2 + " = ?";
        questionTextView.setText(question);
    }

    private void checkAnswer(Button optionbutton) {
        int userAnswer = Integer.parseInt(optionbutton.getText().toString());

            if (userAnswer == correctAnswer) {
                answerview.setText("Correct!");
                score++;
                ScoreTxt.setText("Score: "+score);
            } else {
                answerview.setText("Wrong! The correct answer is " + correctAnswer);
                TotallifeLine--;
                lifeLine.setText("Life Lines: "+TotallifeLine);

                if(TotallifeLine==0){
                    countDownTimer.cancel();
                    GameOverDilog();
                    return;
                }
            }
            generateQuestion();
            resetTimer();
    }

    public void assignCorretAnswer(String correctAns) {
        buttons.add(option1);
        buttons.add(option2);
        buttons.add(option3);
        buttons.add(option4);

        // Generate a random number, either 1 or 2
        int randomcorrectoption = random.nextInt(4) + 1;

        switch (randomcorrectoption) {
            case 1:
                option1.setText(correctAns);
                break;
            case 2:
                option2.setText(correctAns);
                break;
            case 3:
                option3.setText(correctAns);
                break;
            case 4:
                option4.setText(correctAns);
                break;
        }

        for (int i = 0; i < 4; i++) {
            int randomWrongOption =0;
            do {
                randomWrongOption= random.nextInt(50);
            }
            while(randomWrongOption == correctAnswer);
            if (i != randomcorrectoption - 1) { // Adjusted the condition
                buttons.get(i).setText(String.valueOf(randomWrongOption));
            }
        }



    }


    public void option1AnswerCheck(View view) {
       checkAnswer(option1);
    }
    public void option2AnswerCheck(View view) {
      checkAnswer(option2);
    }
    public void option3AnswerCheck(View view) {
      checkAnswer(option3);
    }
    public void option4AnswerCheck(View view) {
      checkAnswer(option4);
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the current timer
        }
        timeLeftInMillis = 15000;
        startTimer(); // Restart the timer
    }
    public void GameOverDilog(){
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Ensure timer is canceled before showing dialog
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addition.this);
        alertDialog.setTitle("Game Over")
                .setMessage("You Score: "+score)
                .setCancelable(false) // Prevent closing the dialog without action
                .setPositiveButton("Ok", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("retry",(dialog, which) -> {
                    resetGame();
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        onQuitDialog();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel timer when the activity is destroyed
        }
    }
    public void onQuitDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addition.this);
        alertDialog.setTitle("Quit Game")
                .setMessage("Are you Sure you want to Quit ")
                .setPositiveButton("Yes", (dialog, which) -> {
                            if (countDownTimer != null) {
                                countDownTimer.cancel(); // Ensure timer is canceled before showing dialog
                            }
                        finish();
    })
            .setNegativeButton("No",(dialog, which) -> {
    })
            .show();
}
    private void resetGame() {
        // Reset score and life lines
        score = 0;
        TotallifeLine = 5;
        lifeLine.setText("Life Lines: " + TotallifeLine);
        ScoreTxt.setText("Score: " + score);

        // Generate a new question
        generateQuestion();

        // Reset the timer and start again
        resetTimer();
    }


}
