package com.example.megha.movieplate.WatchlistFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.R;
import com.example.megha.movieplate.TVFormat.TVDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HIman$hu on 7/20/2016.
 */
public class TVShowsGridViewAdapter extends BaseAdapter implements View.OnClickListener {

    Context mContext;
    private OnItemClickListener onItemClickListener;

    private ArrayList<TVDetails> mTVList;
    private int numOfItems;

    public TVShowsGridViewAdapter(Context context, ArrayList<TVDetails> tvList) {
        mContext = context;
        mTVList = tvList;
        numOfItems = tvList.size();
    }

    public TVShowsGridViewAdapter(Context context, ArrayList<TVDetails> tvList, int numOfItems) {
        mContext = context;
        mTVList = tvList;
        this.numOfItems = numOfItems;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_entity_in_list, parent, false);
        itemView.setOnClickListener(this);
        TVDetails item = mTVList.get(position);

        ImageView poster = (ImageView) itemView.findViewById(R.id.poster_image_view);
        ImageView watchlist = (ImageView) itemView.findViewById(R.id.watchlist_image_view);
        TextView title = (TextView) itemView.findViewById(R.id.title_text_view);
        TextView releaseDate = (TextView) itemView.findViewById(R.id.release_date_text_view);

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w300/" + item.getPosterPath()).into(poster);
        watchlist.setVisibility(View.GONE);
        title.setText(item.getName());
        releaseDate.setText(item.getFirstAirDate());

        itemView.setTag(item);
        return itemView;
    }

    @Override
    public int getCount() {
        return numOfItems;
    }

    @Override
    public Object getItem(int position) {
        return mTVList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (TVDetails) v.getTag());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, TVDetails  tvDetails);
    }

}
