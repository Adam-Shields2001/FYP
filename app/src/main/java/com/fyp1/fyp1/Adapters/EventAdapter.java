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
import com.fyp1.fyp1.EventDetailActivity;
import com.fyp1.fyp1.R;
import com.fyp1.fyp1.models.EventsModel;
import com.fyp1.fyp1.models.FightersModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private Context context;
    private List<EventsModel> eventList;

    public EventAdapter(Context context, List<EventsModel> events) {
        this.context = context;
        eventList = events;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        EventsModel em = eventList.get(position);
        holder.shortName.setText(em.getShortName());
        holder.name.setText(em.getName());
        holder.season.setText(em.getSeason());
        holder.dateTime.setText(em.getDateTime());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("ShortName", em.getShortName());
                bundle.putString("Name", em.getName());
                bundle.putString("Season", em.getSeason());
                bundle.putString("DateTime", em.getDateTime());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{

        TextView shortName, name, season, dateTime;
        ConstraintLayout constraintLayout;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            shortName = itemView.findViewById(R.id.shortName_text);
            name = itemView.findViewById(R.id.name_text);
            season = itemView.findViewById(R.id.year_text);
            dateTime = itemView.findViewById(R.id.dateTime_text);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
