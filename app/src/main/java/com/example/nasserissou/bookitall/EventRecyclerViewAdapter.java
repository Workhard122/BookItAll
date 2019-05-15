package com.example.nasserissou.bookitall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "Event_RecyclerViewAdapter";

    private List<EventModel> mEvents;
    private Context mContext;

    public  EventRecyclerViewAdapter(Context context, List<EventModel> Events){
        this.mContext = context;
        this.mEvents = Events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewAdapter.ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mEvents.get(position).ImageUrl)
                .into(holder.image);

        holder.name.setText(mEvents.get(position).name);

        holder.location.setText(mEvents.get(position).Location);

        holder.date.setText(mEvents.get(position).Date);


    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;
        TextView location;
        // TextView description;
        TextView date;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            name = itemView.findViewById(R.id.event_name);
            location = itemView.findViewById(R.id.event_location);
            date = itemView.findViewById(R.id.event_date);
            //description = itemView.findViewById(R.id.eve);
            parentLayout = itemView.findViewById(R.id.event_parentlayout);
        }

        ;
    }

}//end of class
