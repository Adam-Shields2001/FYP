package com.fyp1.fyp1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp1.fyp1.PredictionDetails;
import com.fyp1.fyp1.R;
import com.fyp1.fyp1.models.PredictionModel;

import java.util.List;

public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.PredictionHolder> {

    private Context context;
    private List<PredictionModel> list;

    public PredictionAdapter(Context context, List<PredictionModel> fighters) {
        this.context = context;
        list = fighters;
    }

    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prediction_list, parent, false);
        return new PredictionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder holder, int position) {
        PredictionModel pm = list.get(position);
        holder.name.setText(pm.getName());
        holder.firstName.setText(pm.getFirstName());
        holder.lastName.setText(pm.getLastName());
        holder.wins.setText(pm.getWins());
        holder.losses.setText(pm.getLosses());
        holder.moneyline.setText(pm.getMoneyline());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PredictionDetails.class);

                Bundle bundle = new Bundle();
                bundle.putString("Name", pm.getName());
                bundle.putString("FirstName", pm.getFirstName());
                bundle.putString("LastName", pm.getLastName());
                bundle.putString("Wins", pm.getWins());
                bundle.putString("Losses", pm.getLosses());
                bundle.putString("Moneyline", pm.getMoneyline());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PredictionHolder extends RecyclerView.ViewHolder{

        TextView name, firstName, lastName, wins, losses, moneyline;
        ConstraintLayout constraintLayout;

        public PredictionHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            firstName = itemView.findViewById(R.id.firstName_text);
            lastName = itemView.findViewById(R.id.lastName_text);
            wins = itemView.findViewById(R.id.wins_text);
            losses = itemView.findViewById(R.id.losses_text);
            moneyline = itemView.findViewById(R.id.moneyline_text);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
