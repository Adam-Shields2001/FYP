package com.fyp1.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp1.fyp1.models.PredictionsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PredictionDetails extends AppCompatActivity {

    private Spinner mFightSpinner;
    private Spinner mMethodSpinner;
    private Spinner mRoundSpinner;
    private Button mSubmitButton;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_details);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Get the current user's ID
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        // Initialize views
        mFightSpinner = findViewById(R.id.fight);
        mMethodSpinner = findViewById(R.id.method_of_victory);
        mRoundSpinner = findViewById(R.id.predicted_round);
        mSubmitButton = findViewById(R.id.submit_button);

        mMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String method = parent.getItemAtPosition(position).toString();
                if (method.equals("Decision")) {
                    // If decision is selected, hide the predicted round spinner
                    mRoundSpinner.setVisibility(View.GONE);
                } else {
                    // If another method is selected, show the predicted round spinner
                    mRoundSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Set click listener on submit button
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Query the Firestore database for predictions made by the current user
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Query query = db.collection("Predictions")
                        .whereEqualTo("userId", mCurrentUserId);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // If the query returns no results, allow the user to submit a new prediction
                            if (task.getResult().size() == 0) {
                                // Handle submit button click
                                String fighter = mFightSpinner.getSelectedItem().toString();
                                String method = mMethodSpinner.getSelectedItem().toString();
                                String round = mRoundSpinner.getSelectedItem().toString();

                                // Create a new prediction object with the user's ID and prediction details
                                PredictionsModel prediction = new PredictionsModel(mCurrentUserId, fighter, method, round);

                                // Add the prediction to your Firestore database
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Predictions").add(prediction)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(PredictionDetails.this, "Prediction added to database", Toast.LENGTH_SHORT).show();

                                                // Open the PredictionResults activity
                                                Intent intent = new Intent(PredictionDetails.this, PredictionResult.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(PredictionDetails.this, "Error adding prediction to database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // If the query returns results, inform the user that they have already made a prediction
                                Toast.makeText(PredictionDetails.this, "You have already made a prediction", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If the query fails, inform the user that there was an error
                            Toast.makeText(PredictionDetails.this, "Error retrieving predictions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
