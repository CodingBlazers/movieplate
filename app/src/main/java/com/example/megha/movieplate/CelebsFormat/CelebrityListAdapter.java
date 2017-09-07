package com.example.megha.movieplate.CelebsFormat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by megha on 07/09/17.
 */

public class CelebrityListAdapter extends RecyclerView.Adapter<CelebrityListAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<CelebrityResults> mCelebsList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public CelebrityListAdapter(Context context, ArrayList<CelebrityResults> celebsList) {
        mContext = context;
        mCelebsList = celebsList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_celeb_in_list, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        CelebrityResults item = mCelebsList.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w300/" + item.getProfilePath()).into(holder.poster);
        holder.celebName.setText(item.getName());
        holder.movies.setText(item.getMovies());
        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return mCelebsList.size();
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (CelebrityResults) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView poster;
        public TextView celebName, movies;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.celeb_poster);
            celebName = (TextView) itemView.findViewById(R.id.name_text_view);
            movies = (TextView) itemView.findViewById(R.id.movies_text_view);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, CelebrityResults celebDetails);

    }
}
