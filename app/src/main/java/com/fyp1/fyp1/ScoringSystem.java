package com.fyp1.fyp1;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ScoringSystem extends AppCompatActivity {

    Button redhead, redbody, redleg, redtakedown;
    Button bluehead, bluebody, blueleg, bluetakedown;

    private TextView roundTime;
    private Button buttonStartPause, buttonReset;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private int numberOfUsers;

    private int redheadCount, redbodyCount, redlegCount, redtakedownCount = 0;
    private int blueheadCount, bluebodyCount, bluelegCount, bluetakedownCount = 0;

    private int totalUsers = 3;

    private TextView redStrikeScore, blueStrikeScore;

    private long totalTimeInMilliseconds;
    private long startTime = -0;
    private long elapsedTimeInMillis;
    private long time = 0;
    private long strikeTime;
    float elapsedTimeInSeconds;
    private int elapsedMinutes, elapsedSeconds;

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
                resetTimer();
            }
        });
        updateCountDownText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
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
                if (timerRunning == false) {
                    validateScoresForAllUsers();
                }
//                Intent intent = new Intent(ScoringSystem.this, Results.class);
//                startActivity(intent);
            }
        }.start();

        redhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redheadCount++;

                // Initialize the Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Get the current user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Create a reference to the database location where you want to write data
                DatabaseReference reference = database.getReference("Strikes").child(user.getUid());

                reference.child("head_strikes").push().setValue(new Strikes(elapsedMinutes, elapsedSeconds, redheadCount));
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

//    private void writeToDatabase(int time, int redheadCount) {
//        // Initialize the Firebase Realtime Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        // Get the current user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        // Create a reference to the database location where you want to write data
//        DatabaseReference reference = database.getReference("Strikes").child(user.getUid());
//
//        //Get current value of head strikes
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //Set new value of head strikes
//                reference.child("head_strikes").push().setValue(new Strikes(time, redheadCount));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // handle error
//            }
//        });
//    }

    private void validateScoresForAllUsers() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String uid = userSnapshot.getKey();
                    validateScores(uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void validateScores(String uid) {

        // Create a reference to the database location where the strikes are stored
        DatabaseReference strikesRef = FirebaseDatabase.getInstance().getReference("Strikes").child(uid).child("head_strikes");

        // Loop through 5-second intervals of the timer
        for (int i = 0; i < 300; i += 5) {
            // Calculate the start and end times for the current 5-second interval
            long startTimer = i * 1000;
            long endTimer = (i + 5) * 1000;

            // Query the strikes for the current user within the timer duration
            strikesRef.orderByChild("elapsedMinutes").startAt(startTimer / 1000 / 60).endAt(endTimer / 1000 / 60).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    int totalStrikes = (int) snapshot.getChildrenCount();

                    // Initialize counters for valid and invalid strikes
                    int validStrikes = 0;
                    int invalidStrikes = 0;

                    for (DataSnapshot strikesSnapshot : snapshot.getChildren()) {
                        Strikes strike = strikesSnapshot.getValue(Strikes.class);

                        float strikeDuration = strike.getElapsedMinutes() * 60 * 1000 + strike.getElapsedSeconds() * 1000;
                        // Check if the strike occurred within the current 5-second interval
                        if (strikeDuration >= startTimer && strikeDuration <= endTimer) {
                            validStrikes++;
                        } else {
                            invalidStrikes++;
                        }
                    }

                    if (validStrikes < invalidStrikes) {
                        float percentage = validStrikes / totalStrikes * 100;
                        // Delete the strikes from the database for the current 5-second interval
                        Query query = strikesRef.orderByChild("elapsedMinutes").startAt(startTimer / 1000 / 60).endAt(endTimer / 1000 / 60);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (percentage > 50) {
                                    Toast.makeText(ScoringSystem.this, "Scores Validated for user " + uid, Toast.LENGTH_LONG).show();
                                } else {
                                    for (DataSnapshot strikesSnapshot : snapshot.getChildren()) {
                                        strikesSnapshot.getRef().removeValue();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                            }
                        });
                    }
                    // Reset the counters for the next 5-second interval
                    validStrikes = 0;
                    invalidStrikes = 0;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }
}