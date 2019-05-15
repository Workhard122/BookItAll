package com.example.nasserissou.bookitall;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "RecyclerViewAdapter";

    private List<RestaurantModel> mRestaurents;
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, List<RestaurantModel> restaurants) {
        this.mContext = mContext;
        this.mRestaurents = restaurants;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mRestaurents.get(position).imageUrl)
                .into(holder.image);

        holder.imageName.setText(mRestaurents.get(position).name);

        StringBuffer stringBuffer = new StringBuffer();
        for( String s : mRestaurents.get(position).type )
        {
            stringBuffer.append(s);
        }
        holder.type.setText(stringBuffer);

        holder.description.setText("Description: " + mRestaurents.size());



        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on : " + mRestaurents.get(position));

                Toast.makeText(mContext, mRestaurents.get(position).name, Toast.LENGTH_SHORT).show();


            }
        });
        

    }

    @Override
    public int getItemCount() {
        return mRestaurents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView type;
        TextView description;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        };

    }


}
