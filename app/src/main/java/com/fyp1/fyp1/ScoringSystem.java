package com.fyp1.fyp1;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fyp1.fyp1.models.BlueStrikes;
import com.fyp1.fyp1.models.Fight;
import com.fyp1.fyp1.models.RedStrikes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ScoringSystem extends AppCompatActivity {

    private Button redhead, bluehead;
    private Button nextRound, displayResultsButton;
    private CountDownTimer countDownTimer;
    private boolean timerRunning, roundStarted = false;
    private long timeLeftInMillis;
    private TextView fighter1TextView,  fighter2TextView, redStrikeScore, blueStrikeScore, roundTime, roundNumberTextView;
    private int redStrike, blueStrike;
    private long startTime = -0, elapsedTimeInMillis, time = 0;
    float elapsedTimeInSeconds;
    private int round, roundCounter = 1, numberOfUsers;
    private String firstName1, lastName1, firstName2, lastName2, rounds;
    int redValidatedStrikes = 0, blueValidatedStrikes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_system);

        redhead = (Button) findViewById(R.id.redheadStrikes);
        bluehead = (Button) findViewById(R.id.blueheadStrikes);

        redStrikeScore = (TextView) findViewById(R.id.redstrikesScore);
        blueStrikeScore = (TextView) findViewById(R.id.bluestrikesScore);

        roundTime = (TextView) findViewById(R.id.roundTime);
        nextRound = (Button) findViewById(R.id.button_nextRound);

        Fight fight = (Fight) getIntent().getSerializableExtra("fight");
        round = fight.getRounds();

        fighter1TextView = findViewById(R.id.fighter1_name);
        fighter2TextView = findViewById(R.id.fighter2_name);
        roundNumberTextView = findViewById(R.id.roundNumberTextView);
        displayResultsButton = findViewById(R.id.button_displayResults);

        displayResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoringSystem.this, Result.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            firstName1 = intent.getStringExtra("firstName1");
            lastName1 = intent.getStringExtra("lastName1");
            firstName2 = intent.getStringExtra("firstName2");
            lastName2 = intent.getStringExtra("lastName2");
            rounds = intent.getStringExtra("rounds");
            roundCounter = intent.getIntExtra("roundCounter", 1);
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
                roundStarted = false;
                updateCountDownText();

                String fightersNames = firstName1 + " " + lastName1 + " vs " + firstName2 + " " + lastName2;
                validateScores(fightersNames, roundCounter);

                // Call the getValidatedStrikesCount method with the current round and fighter names, and a listener
                getValidatedStrikesCount(roundCounter, fightersNames, new OnStrikesCountLoadedListener() {
                    @Override
                    public void onStrikesCountLoaded(int redStrikesCount, int blueStrikesCount) {
                        Log.d(TAG, "Red Strikes Count: " + redStrikesCount);
                        Log.d(TAG, "Blue Strikes Count: " + blueStrikesCount);
                        addStrikeCountsToFights(roundCounter, fightersNames, redStrikesCount, blueStrikesCount);
                    }
                });

                // Increment the round number
                roundCounter++;

                // Check if the roundCounter has reached the maximum value of 3
                if (roundCounter > 3) {
                    // If the maximum value has been reached, finish the activity
                    nextRound.setVisibility(View.GONE);
                    displayResultsButton.setVisibility(View.VISIBLE);
                } else {
                    // If the maximum value has not been reached, update the round number text view and start a new round
                    roundNumberTextView.setText("Round " + roundCounter + "/3");
                    startTimer();
                    nextRound.setVisibility(View.GONE);
                }
            }
        });

        startTimerAtTargetTime();
    }

    private void startTimerAtTargetTime() {
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 9);
        targetTime.set(Calendar.MINUTE, 28);
        targetTime.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();

        long remainingTimeInMillis = targetTime.getTimeInMillis() - now.getTimeInMillis();

        new Handler().postDelayed(() -> startTimer(), remainingTimeInMillis);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(5000, 1000) {
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
                nextRound.setVisibility(View.VISIBLE);
            }
        }.start();

        redhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redStrike++;

                // Set the blue strike score to the current value of the blueStrike variable
                redStrikeScore.setText(String.valueOf(redStrike));

                // Initialize the Firebase Firestore and create new collection called "Strikes"
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                CollectionReference strikesCollection = firestore.collection("Strikes");

                // Get the current user's uid and create new document
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DocumentReference documentReference = strikesCollection.document();

                // Create a new BlueStrikes object
                String fightersNames = firstName1 + " " + lastName1 + " vs " + firstName2 + " " + lastName2;
                RedStrikes redStrikes = new RedStrikes(elapsedTimeInSeconds, roundCounter, fightersNames, uid, redStrike);

                // Add the BlueStrikes object to the document
                documentReference.set(redStrikes)
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

        bluehead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blueStrike++;

                // Set the blue strike score to the current value of the blueStrike variable
                blueStrikeScore.setText(String.valueOf(blueStrike));

                // Initialize the Firebase Firestore and create new collection called "Strikes"
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                CollectionReference strikesCollection = firestore.collection("Strikes");

                // Get the current user's uid and create new document
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DocumentReference documentReference = strikesCollection.document();

                // Create a new BlueStrikes object
                String fightersNames = firstName1 + " " + lastName1 + " vs " + firstName2 + " " + lastName2;
                BlueStrikes blueStrikes = new BlueStrikes(elapsedTimeInSeconds, roundCounter, fightersNames, uid, blueStrike);

                // Add the BlueStrikes object to the document
                documentReference.set(blueStrikes)
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
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        roundTime.setText(timeLeftFormatted);
    }

    private void validateScores(String fightersNames, int roundCounter) {

        // update round variable
        this.roundCounter = roundCounter;
        Set<String> uniqueUserIds = new HashSet<>();

        // Create a reference to the database location where the strikes are stored
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference strikesRef = db.collection("Strikes");
        CollectionReference validatedRef = db.collection("Validated");

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

                    // Reset the validated strikes counters for the current round
                    redValidatedStrikes = 0;
                    blueValidatedStrikes = 0;

                    Log.d(TAG, "Fighters: " + fightersNames);

                    // Query the strikes for all users within the timer duration
                    Query query = strikesRef.whereEqualTo("fighterNames", fightersNames).whereEqualTo("round", roundCounter).whereLessThanOrEqualTo("elapsedTimeInSeconds", startTimer / 1000)
                            .whereGreaterThanOrEqualTo("elapsedTimeInSeconds", endTimer / 1000);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            // Loop through the strikes for all users and group them by user ID
                            Map<String, Integer> strikesCount = new HashMap<>();
                            int redStrikesCount = 0;
                            int blueStrikesCount = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userId = document.getString("uid");
                                boolean isRedStrike = document.contains("redStrike");
                                boolean isBlueStrike = document.contains("blueStrike");
                                // Increment the strikes counter for the current user
                                int strikes = strikesCount.containsKey(userId) ? strikesCount.get(userId) : 0;
                                strikesCount.put(userId, strikes + 1);
                                if (isRedStrike) {
                                    redStrikesCount++;
                                }
                                if (isBlueStrike) {
                                    blueStrikesCount++;
                                }
                            }

                            // Count the number of scoring users for the current interval
                            int intervalScoringUsersCount = 0;
                            for (String uid : uniqueUserIds) {
                                if (strikesCount.containsKey(uid)) {
                                    intervalScoringUsersCount++;
                                }
                            }

                            // Calculate the percentage of scoring users
                            float percentage = ((float) intervalScoringUsersCount / (float) uniqueUserIds.size()) * 100;
                            float red = ((float) redStrikesCount / (float) uniqueUserIds.size()) * 100;
                            float blue = ((float) blueStrikesCount / (float) uniqueUserIds.size()) * 100;

                            int totalUsers = uniqueUserIds.size();

                            // Add the total strikes for each user to the "Fights" collection
                            if (percentage > 50 && red > 50) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boolean isRedStrike = document.contains("redStrike");
                                    if (isRedStrike) {
                                        Map<String, Object> data = document.getData();
                                        validatedRef.document(document.getId()).set(data);
                                    }
                                }
                            } if (percentage > 50 && blue > 50) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boolean isBlueStrike = document.contains("blueStrike");
                                    if (isBlueStrike) {
                                        Map<String, Object> data = document.getData();
                                        validatedRef.document(document.getId()).set(data);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void getValidatedStrikesCount(int round, String fightersNames, OnStrikesCountLoadedListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference validatedCollection = firestore.collection("Validated");

        Task<QuerySnapshot> task = validatedCollection
                .whereEqualTo("round", round)
                .whereEqualTo("fighterNames", fightersNames)
                .get();

        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int redStrikesCount = 0;
                    int blueStrikesCount = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.contains("redStrike")) {
                            redStrikesCount++;
                        } else if (document.contains("blueStrike")) {
                            blueStrikesCount++;
                        }
                    }
                    listener.onStrikesCountLoaded(redStrikesCount, blueStrikesCount);
                } else {
                    Log.d(TAG, "Error getting validated strikes: ", task.getException());
                }
            }
        });
    }


    public interface OnStrikesCountLoadedListener {
        void onStrikesCountLoaded(int redStrikesCount, int blueStrikesCount);
    }

    private void addStrikeCountsToFights(int round, String fightersNames, int redStrikesCount, int blueStrikesCount) {
        // Initialize the Firebase Firestore and create a new collection called "Fights"
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference fightsCollection = firestore.collection("Fights");

        // Create a new document for the current fight
        DocumentReference df = fightsCollection.document(fightersNames);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    // Set the initial values for the document
                    Map<String, Object> data = new HashMap<>();
                    data.put("Red - Round 1", 0);
                    data.put("Blue - Round 1", 0);
                    data.put("Red - Round 2", 0);
                    data.put("Blue - Round 2", 0);
                    data.put("Red - Round 3", 0);
                    data.put("Blue - Round 3", 0);
                    df.set(data);
                }
            }
        });


        // Update the red and blue strike counts for the current round in the "Fights" collection
        switch (round) {
            case 2:
                df.update("Red - Round 1", redStrikesCount);
                df.update("Blue - Round 1", blueStrikesCount);
                break;
            case 3:
                df.update("Red - Round 2", redStrikesCount);
                df.update("Blue - Round 2", blueStrikesCount);
                break;
            case 4:
                df.update("Red - Round 3", redStrikesCount);
                df.update("Blue - Round 3", blueStrikesCount);
                break;
        }
    }

}