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

import com.fyp1.fyp1.DetailActivity;
import com.fyp1.fyp1.R;
import com.fyp1.fyp1.models.FightModel;
import com.fyp1.fyp1.models.FightersModel;

import java.util.List;

public class FightAdapter extends RecyclerView.Adapter<FightAdapter.FightHolder> {

    private Context context;
    private List<FightModel> fightList;

    public FightAdapter(Context context, List<FightModel> fights) {
        this.context = context;
        fightList = fights;
    }

    @NonNull
    @Override
    public FightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prediction_list, parent, false);
        return new FightHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FightHolder holder, int position) {
        FightModel fm = fightList.get(position);
        holder.shortName.setText(fm.getShortName());
        holder.name.setText(fm.getName());
        holder.firstName.setText(fm.getFirstName());
        holder.lastName.setText(fm.getLastName());
        holder.wins.setText((int) fm.getWins());
        holder.losses.setText((int) fm.getLosses());
        holder.draws.setText((int) fm.getDraws());
        holder.noContests.setText((int) fm.getNoContests());
        holder.moneyline.setText((int) fm.getMoneyline());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("ShortName", fm.getShortName());
                bundle.putString("Name", fm.getName());
                bundle.putString("FirstName", fm.getFirstName());
                bundle.putString("LastName", fm.getLastName());
                bundle.putDouble("PreFightWins", fm.getWins());
                bundle.putDouble("PreFightLosses", fm.getLosses());
                bundle.putDouble("PreFightDraws", fm.getDraws());
                bundle.putDouble("PreFightNoContests", fm.getNoContests());
                bundle.putDouble("Moneyline", fm.getMoneyline());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fightList.size();
    }

    public class FightHolder extends RecyclerView.ViewHolder{

        TextView shortName, name, firstName, lastName, wins, losses, draws, noContests, moneyline;
        ConstraintLayout constraintLayout;

        public FightHolder(@NonNull View itemView) {
            super(itemView);

            shortName = itemView.findViewById(R.id.shortName_text);
            name = itemView.findViewById(R.id.name_text);
            firstName = itemView.findViewById(R.id.firstName_text);
            lastName = itemView.findViewById(R.id.lastName_text);
            wins = itemView.findViewById(R.id.wins_text);
            losses = itemView.findViewById(R.id.losses_text);
            draws = itemView.findViewById(R.id.draws_text);
            moneyline = itemView.findViewById(R.id.moneyline_text);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
