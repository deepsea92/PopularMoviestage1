package com.sharma.deepak.popularmoviestage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharma.deepak.popularmoviestage1.R;
import com.sharma.deepak.popularmoviestage1.bean.Trailer;

import java.util.ArrayList;

/**
 * Created by deepak on 11-06-2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolderPattern> {
    private ArrayList<Trailer> trailerArrayList;
    private Context context;
    private String imagePath;
    TrailerItemClickInterface trailerInterface;

    public TrailerAdapter(ArrayList<Trailer> trailerArrayList, Context context, TrailerItemClickInterface trailerInterface, String imagePath) {
        this.context = context;
        this.trailerArrayList = trailerArrayList;
        this.trailerInterface = trailerInterface;
        this.imagePath = imagePath;
    }

    @Override
    public TrailerHolderPattern onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerHolderPattern(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerHolderPattern holder, int position) {
        Trailer trailer = trailerArrayList.get(position);
        //  holder.trailerType.setText(trailer.getType());
        holder.trailerName.setText(trailer.getName());
        Log.e("type", trailer.getType());
        Log.e("name", trailer.getName());
        Glide
                .with(context)
                .load(imagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.movie_default)
                .into(holder.trailerImage);
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    class TrailerHolderPattern extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView trailerImage;
        TextView trailerType, trailerName;

        public TrailerHolderPattern(View itemView) {
            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.iv_trailer_item);
            //trailerType = (TextView) itemView.findViewById(R.id.tv_trailer_type);
            trailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            trailerInterface.trailerClick(position);
        }
    }


    public interface TrailerItemClickInterface {
        public void trailerClick(int position);
    }
}


