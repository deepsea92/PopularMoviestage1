package com.sharma.deepak.popularmoviestage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharma.deepak.popularmoviestage1.R;
import com.sharma.deepak.popularmoviestage1.bean.Reviews;

import java.util.ArrayList;

/**
 * Created by deepak on 11-06-2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolderPattern> {
    private ArrayList<Reviews> reviewArrayList;
    private Context context;


    public ReviewAdapter(ArrayList<Reviews> reviewArrayList, Context context) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public ReviewHolderPattern onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewHolderPattern(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewHolderPattern holder, int position) {
        Reviews reviews = reviewArrayList.get(position);
        holder.authorName.setText(reviews.getAuthor());
        holder.reviewContent.setText(" \" " + reviews.getContent() + " \" ");


    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    class ReviewHolderPattern extends RecyclerView.ViewHolder {
        TextView authorName, reviewContent;

        public ReviewHolderPattern(View itemView) {
            super(itemView);

            authorName = (TextView) itemView.findViewById(R.id.tv_author);
            reviewContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }


}
