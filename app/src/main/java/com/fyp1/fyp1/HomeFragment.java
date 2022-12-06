package com.fyp1.fyp1;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class HomeFragment extends Fragment {

    Activity context;
    TextView showValue;
    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    public void onStart() {
        super.onStart();
        showValue = (TextView) context.findViewById(R.id.counterValue);
    }

    public void countIN (View view) {
        counter++;
        showValue.setText(Integer.toString(counter));
    }
}