package com.fyp1.fyp1;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ScoringSystem extends AppCompatActivity {

    Button redhead, redbody, redleg, redtakedown;
    Button bluehead, bluebody, blueleg, bluetakedown;

    private TextView roundTime;
    private Button buttonStartPause, buttonReset, nextRound;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private int numberOfUsers;

    private int redheadCount, redbodyCount, redlegCount, redtakedownCount = 0;
    private int blueheadCount, bluebodyCount, bluelegCount, bluetakedownCount = 0;

    private TextView redStrikeScore, blueStrikeScore;

    private long totalTimeInMilliseconds, startTime = -0, elapsedTimeInMillis, time = 0, strikeTime;
    float elapsedTimeInSeconds;
    private int elapsedMinutes, elapsedSeconds, round = 1;

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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = firestore.collection("Users");
        usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    numberOfUsers = task.getResult().size();
                    Log.d("TAG", "Number of users: " + numberOfUsers);
                } else {
                    Log.d("TAG", "Error getting users: " + task.getException());
                }
            }
        });

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redheadCount =0;
                resetTimer();
            }
        });

        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                round++;
//                Intent intent = new Intent(ScoringSystem.this, ScoringSystem.class);
//                startActivity(intent);
                validateScores(uniqueUserIds);
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                elapsedTimeInMillis = startTime + timeLeftInMillis;
                elapsedTimeInSeconds = elapsedTimeInMillis / 1000.0f;
                elapsedMinutes = (int) (elapsedTimeInSeconds / 60);
                elapsedSeconds = (int) (elapsedTimeInSeconds % 60);
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
                Strikes strikes = new Strikes(uid, elapsedMinutes, elapsedSeconds, redheadCount, round);

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

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText("Start");
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeLeftInMillis = 300000;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        roundTime.setText(timeLeftFormatted);
    }

    private Set<String> uniqueUserIds = new HashSet<>();

    private void totalUsers() {
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
            }
        });
    }

    private void validateScores(Set<String> uniqueUserIds) {
        totalUsers();
        // Create a reference to the database location where the strikes are stored
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference strikesRef = db.collection("Strikes");

        // Loop through 5-second intervals of the timer
        for (int i = 300; i >= 0; i -= 5) {
            // Calculate the start and end times for the current 5-second interval
            long startTimer = i * 1000;
            long endTimer = (i + 5) * 1000;

            // Query the strikes for all users within the timer duration
            Query query = strikesRef.whereGreaterThanOrEqualTo("elapsedMinutes", startTimer / 1000 / 60)
                    .whereLessThanOrEqualTo("elapsedMinutes", endTimer / 1000 / 60);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Map<String, Integer> strikesCount = new HashMap<>();
                    int totalUsersCount = 0;

                    // Loop through the strikes for all users and group them by user ID
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userId = document.getString("uid");
                        // Increment the strikes counter for the current user
                        int strikes;
                        if (strikesCount.containsKey(userId)) {
                            strikes = strikesCount.get(userId);
                        } else {
                            strikes = 0;
                        }
                        strikesCount.put(userId, strikes + 1);
                        // Increment the total users count
                        totalUsersCount++;
                        // Add the user ID to the set of scoring users
                        //allScoringUserIds.add(userId);
                    }

                    // Calculate the number of scoring users in the interval
                    int scoringUsersCount = strikesCount.size();

                    // Calculate the number of unscoring users in the interval
                    int unscoringUsersCount = uniqueUserIds.size() - scoringUsersCount;

                    // Calculate the total number of users in the interval
                    int totalIntervalUsersCount = scoringUsersCount + unscoringUsersCount;

                    // Calculate the percentage of scoring users
                    float percentage = ((float) scoringUsersCount / (float) uniqueUserIds.size()) * 100;

                    // Loop through the user counters and validate the scores for each user
                    for (String uid : strikesCount.keySet()) {
                        int strikes = strikesCount.get(uid);

                        if (percentage <= 50) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
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
                                } else {
                            Toast.makeText(ScoringSystem.this, "Scores Validated for user " + uid, Toast.LENGTH_LONG).show();
                        }
                            }
                        }
            });
        }
    }
}