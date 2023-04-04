package com.fyp1.fyp1;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp1.fyp1.models.Fight;
import com.fyp1.fyp1.models.Strikes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ScoringSystem extends AppCompatActivity {

    private Button redhead, redbody, redleg, redtakedown, bluehead, bluebody, blueleg, bluetakedown;
    private Button buttonStartPause, buttonReset, nextRound;
    private CountDownTimer countDownTimer;
    private boolean timerRunning, roundStarted = false;
    ;
    private long timeLeftInMillis;
    private TextView fighter1TextView,  fighter2TextView, redStrikeScore, blueStrikeScore, roundTime;
    private int redheadCount, redbodyCount, redlegCount, redtakedownCount = 0, blueheadCount, bluebodyCount, bluelegCount, bluetakedownCount = 0;
    private long totalTimeInMilliseconds, startTime = -0, elapsedTimeInMillis, time = 0, strikeTime;
    float elapsedTimeInSeconds;
    private int elapsedMinutes, elapsedSeconds, round, roundCounter = 1, numberOfUsers;
    private String firstName1, lastName1, firstName2, lastName2, rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_system);

        redhead = (Button) findViewById(R.id.redheadStrikes);
        redbody = (Button) findViewById(R.id.redbodyStrikes);
        redleg = (Button) findViewById(R.id.redlegStrikes);
        redtakedown = (Button) findViewById(R.id.redTakedowns);

        bluehead = (Button) findViewById(R.id.blueheadStrikes);
        bluebody = (Button) findViewById(R.id.bluebodyStrikes);
        blueleg = (Button) findViewById(R.id.bluelegStrikes);
        bluetakedown = (Button) findViewById(R.id.blueTakedowns);

        redStrikeScore = (TextView) findViewById(R.id.redstrikesScore);
        blueStrikeScore = (TextView) findViewById(R.id.bluestrikesScore);

        roundTime = (TextView) findViewById(R.id.roundTime);
        buttonStartPause = (Button) findViewById(R.id.button_start_pause);
        buttonReset = (Button) findViewById(R.id.button_reset);
        nextRound = (Button) findViewById(R.id.button_nextRound);

        Fight fight = (Fight) getIntent().getSerializableExtra("fight");
        int round = fight.getRounds();

        fighter1TextView = findViewById(R.id.fighter1_name);
        fighter2TextView = findViewById(R.id.fighter2_name);
        TextView roundsTextView = findViewById(R.id.roundsTextView);

        Intent intent = getIntent();
        if (intent != null) {
            firstName1 = intent.getStringExtra("firstName1");
            lastName1 = intent.getStringExtra("lastName1");
            firstName2 = intent.getStringExtra("firstName2");
            lastName2 = intent.getStringExtra("lastName2");
            rounds = intent.getStringExtra("rounds");

            int numberOfRounds = fight.getRounds();

            if (numberOfRounds == 5) {
                roundsTextView.setText("3 rounds");
            } else {
                roundsTextView.setText("3 rounds");
            }


            fighter1TextView.setText(firstName1 + "\n" + lastName1);
            fighter2TextView.setText(firstName2 + "\n" + lastName2);

        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = firestore.collection("Users");
        usersCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                numberOfUsers = task.getResult().size();
                Log.d("TAG", "Number of users: " + numberOfUsers);
            } else {
                Log.d("TAG", "Error getting users: " + task.getException());
            }
        });

        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Stop the current timer
                countDownTimer.cancel();
                timerRunning = false;

                // Increment the round number
                final int nextRoundCounter = roundCounter + 1;

                Intent intent = new Intent(ScoringSystem.this, ScoringSystem.class);
                intent.putExtra("fight", fight);
                intent.putExtra("firstName1", firstName1);
                intent.putExtra("lastName1", lastName1);
                intent.putExtra("firstName2", firstName2);
                intent.putExtra("lastName2", lastName2);
                intent.putExtra("rounds", rounds);
                intent.putExtra("round", round);
                startActivity(intent);
                finish();
            }
        });

        startTimerAtTargetTime();

        updateCountDownText();
    }

    private void startTimerAtTargetTime() {
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 17);
        targetTime.set(Calendar.MINUTE, 18);
        targetTime.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();

        long remainingTimeInMillis = targetTime.getTimeInMillis() - now.getTimeInMillis();

        new Handler().postDelayed(() -> startTimer(), remainingTimeInMillis);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                elapsedTimeInMillis = startTime + timeLeftInMillis;
                elapsedTimeInSeconds = Math.round(elapsedTimeInMillis / 1000.0f);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartPause.setText("Start");
                buttonStartPause.setVisibility(View.INVISIBLE);
                buttonReset.setVisibility(View.VISIBLE);
                nextRound.setVisibility(View.VISIBLE);
//                Intent intent = new Intent(ScoringSystem.this, Results.class);
//                startActivity(intent);
            }
        }.start();

        redhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redheadCount++;

                // Set the red strike score to the current value of the redheadCount variable
                redStrikeScore.setText(String.valueOf(redheadCount));

                // Initialize the Firebase Firestore
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                // Create a new collection with the name "Strikes"
                CollectionReference strikesCollection = firestore.collection("Strikes");

                // Get the current user's uid
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                // Create a new document with a generated ID
                DocumentReference documentReference = strikesCollection.document();

                // Create a new Strikes object
                String fightersNames = firstName1 + " " + lastName1 + " vs " + firstName2 + " " + lastName2;
                Strikes strikes = new Strikes(elapsedTimeInSeconds, redheadCount, round, fightersNames, uid);

                // Add the Strikes object to the document
                documentReference.set(strikes)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        timerRunning = true;
        buttonStartPause.setText("Pause");
        buttonReset.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        roundTime.setText(timeLeftFormatted);
    }

    private void validateScores() {

        Set<String> uniqueUserIds = new HashSet<>();

        // Create a reference to the database location where the strikes are stored
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference strikesRef = db.collection("Strikes");

        // Query all the documents in the collection
        strikesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Loop through all the documents in the collection
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userId = document.getString("uid");
                        // Add the user ID to the set of unique user IDs
                        uniqueUserIds.add(userId);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

                // Loop through 5-second intervals of the timer
                for (int i = 300; i >= 0; i -= 5) {
                    // Calculate the start and end times for the current 5-second interval
                    long startTimer = i * 1000;
                    long endTimer = (i - 5) * 1000;

                    // Query the strikes for all users within the timer duration
                    Query query = strikesRef.whereLessThanOrEqualTo("elapsedTimeInSeconds", startTimer / 1000)
                            .whereGreaterThanOrEqualTo("elapsedTimeInSeconds", endTimer / 1000);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            // Loop through the strikes for all users and group them by user ID
                            Map<String, Integer> strikesCount = new HashMap<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String userId = document.getString("uid");
                                    // Increment the strikes counter for the current user
                                    int strikes = strikesCount.getOrDefault(userId, 0);
                                    strikesCount.put(userId, strikes + 1);
                                }


                            Log.d(TAG, "Number of documents returned by the query: " + task.getResult().size());
                            Log.d(TAG, "Number of strikes: " + strikesCount);

                            // Calculate the number of scoring users in the interval
                            int scoringUsersCount = 0;
                            for (String uid : uniqueUserIds) {
                                if (strikesCount.containsKey(uid)) {
                                    scoringUsersCount++;
                                }
                            }

                            // Calculate the percentage of scoring users
                            float percentage = ((float) scoringUsersCount / (float) uniqueUserIds.size()) * 100;

                            // Delete strikes if percentage is less than 50
                            if (percentage < 50) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String userId = document.getString("uid");
                                    int strikes = strikesCount.getOrDefault(userId, 0);
                                    if (strikes > 0) {
                                        document.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                                    }
                                }
                            } else {

                            }
                        }
                    });
                }
            }
        });
    }
}