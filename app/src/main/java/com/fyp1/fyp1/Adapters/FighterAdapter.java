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
import com.fyp1.fyp1.models.FightersModel;
import com.fyp1.fyp1.R;

import java.util.List;

public class FighterAdapter extends RecyclerView.Adapter<FighterAdapter.FighterHolder> {

    private Context context;
    private List<FightersModel> fighterList;

    public FighterAdapter(Context context, List<FightersModel> fighters) {
        this.context = context;
        fighterList = fighters;
    }

    public void setFilteredList(List<FightersModel> filteredList){
        this.fighterList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FighterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fighter_list, parent, false);
        return new FighterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FighterHolder holder, int position) {
        FightersModel fm = fighterList.get(position);
        holder.firstName.setText(fm.getFirstName());
        holder.lastName.setText(fm.getLastName());
        holder.nickname.setText(fm.getNickname());
        holder.wins.setText(fm.getWins().toString());
        holder.losses.setText(fm.getLosses().toString());
        holder.draws.setText(fm.getDraws().toString());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("FirstName", fm.getFirstName());
                bundle.putString("LastName", fm.getLastName());
                bundle.putString("Nickname", fm.getNickname());
                bundle.putDouble("Wins", fm.getWins());
                bundle.putDouble("Losses", fm.getLosses());
                bundle.putDouble("Draws", fm.getDraws());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fighterList.size();
    }

    public class FighterHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, nickname, wins, losses, draws;
        ConstraintLayout constraintLayout;

        public FighterHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName_text);
            lastName = itemView.findViewById(R.id.lastName_text);
            nickname = itemView.findViewById(R.id.nickname_text);
            wins = itemView.findViewById(R.id.wins_text);
            losses = itemView.findViewById(R.id.losses_text);
            draws = itemView.findViewById(R.id.draws_text);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
