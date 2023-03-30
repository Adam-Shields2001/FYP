package com.fyp1.fyp1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp1.fyp1.R;
import com.fyp1.fyp1.models.Fight;

import java.util.List;

public class UFCEventAdapter extends RecyclerView.Adapter<UFCEventAdapter.ViewHolder> {

    private Context context;
    private List<Fight> fightsList;
    private OnItemClickListener listener;

    public UFCEventAdapter(Context context, List<Fight> fights) {
        this.context = context;
        fightsList = fights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ufc_event_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fight fight = fightsList.get(position);

        holder.fighter1TextView.setText(fight.getFirstName() + "\n" + fight.getLastName());
        holder.fighter2TextView.setText(fight.getOpponentFirstName() + "\n" + fight.getOpponentLastName());
        holder.fighter1RecordTextView.setText(fight.getPreFightWins() + "-" + fight.getPreFightLosses() + " (UFC)");
        holder.fighter2RecordTextView.setText(fight.getOpponentPreFightWins() + "-" + fight.getOpponentPreFightLosses() + " (UFC)");
        holder.fighter1OddsTextView.setText(fight.getMoneyLine());
        holder.fighter2OddsTextView.setText(fight.getOpponentMoneyLine());
    }


    @Override
    public int getItemCount() {
        return fightsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Fight fight);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView fighter1TextView;
        public TextView fighter2TextView;
        public TextView fighter1RecordTextView;
        public TextView fighter2RecordTextView;
        public TextView fighter1OddsTextView;
        public TextView fighter2OddsTextView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            fighter1TextView = itemView.findViewById(R.id.fighter1TextView);
            fighter2TextView = itemView.findViewById(R.id.fighter2TextView);
            fighter1RecordTextView = itemView.findViewById(R.id.fighter1RecordTextView);
            fighter2RecordTextView = itemView.findViewById(R.id.fighter2RecordTextView);
            fighter1OddsTextView = itemView.findViewById(R.id.fighter1OddsTextView);
            fighter2OddsTextView = itemView.findViewById(R.id.fighter2OddsTextView);
            linearLayout = itemView.findViewById(R.id.main_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(fightsList.get(position));
                }
            }
        }
    }
}







