package com.assignment.alexs.twitterclient.ui;

/**
 * Created by alexschwartzman on 1/7/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.assignment.alexs.twitterclient.R;
import com.assignment.alexs.twitterclient.model.TWStatus;
import java.util.List;


public class TWAdapter extends RecyclerView.Adapter<TWAdapter.TWListHolder> {

    private List<TWStatus> tweets;

    public class TWListHolder extends RecyclerView.ViewHolder {
        private TextView txtTweet;
        public TWListHolder(View itemView) {
            super(itemView);
            txtTweet = itemView.findViewById(R.id.txtTweet);
        }
    }

    public TWAdapter(List<TWStatus> tweets) {
        this.tweets = tweets;
    }

    @Override
    public TWListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.tweet_row, parent, false);

        return new TWListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TWListHolder holder, int position) {
        TWStatus status  = tweets.get(position);
        holder.txtTweet.setText(status.getText());
    }

    public void updateItems(List<TWStatus> tweets) {
        this.tweets = tweets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

}



