package com.prac.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Addition extends AppCompatActivity {
    TextView questionTextView, answerview;
    EditText answerEditText;
    Button submitButton, backButton;

    int num1, num2, correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        questionTextView = findViewById(R.id.question);
        answerEditText = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        answerview = findViewById(R.id.answercheck);
        backButton = findViewById(R.id.backButton); // Initialize the back button

        generateQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event for the back button
                navigateToMainActivity();
            }
        });
    }

    private void generateQuestion() {
        Random random = new Random();
        num1 = random.nextInt(50); // Change the range as needed
        num2 = random.nextInt(50);
        correctAnswer = num1 + num2;

        String question = num1 + " + " + num2 + " = ?";
        questionTextView.setText(question);
    }

    private void checkAnswer() {
        String userAnswerStr = answerEditText.getText().toString().trim();

        if (!userAnswerStr.isEmpty()) {
            int userAnswer = Integer.parseInt(userAnswerStr);

            if (userAnswer == correctAnswer) {
                answerview.setText("Correct!");
            } else {
                answerview.setText("Wrong! The correct answer is " + correctAnswer);
            }

            // Generate a new question after checking the answer
            generateQuestion();
            // Clear the answer field for the next question
            answerEditText.setText("");
        } else {
            showToast("Please enter an answer.");
        }
    }

    private void navigateToMainActivity() {
        // Create an Intent to start the main activity
        Intent intent = new Intent(this, MainActivity.class);

        // Start the activity
        startActivity(intent);

        // Finish the current activity
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
