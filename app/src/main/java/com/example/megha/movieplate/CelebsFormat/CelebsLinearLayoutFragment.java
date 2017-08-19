package com.example.megha.movieplate.CelebsFormat;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.megha.movieplate.Constants;
import com.example.megha.movieplate.R;
import com.squareup.picasso.Picasso;

/**
 * Created by HIman$hu on 4/2/2016.
 */
public class CelebsLinearLayoutFragment extends Fragment {

    ImageView imageView[];
    TextView nameTextView[], moviesTextView[];
    Intent intent;
    View view;
    Bundle b;

    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_celebs_linear_layout_fragments, container, false);
        b = getArguments();
        imageView = new ImageView[10];
        nameTextView = new TextView[10];
        moviesTextView = new TextView[10];
        final Celebs celebs = (Celebs) b.getSerializable(Constants.CELEBS_TO_LINEAR_LAYOUT_FRAGMENT);

        initViews();

        intent = new Intent();
        intent.setClass(getActivity(), CelebrityDetailsActivity.class);
        String key = (String) b.getSerializable(Constants.API_KEY);
        intent.putExtra(Constants.CELEBS_URL_API_KEY, key);

        if (b != null) {
            for(int i=0; i<10; i++){
                Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w300/" + celebs.celebsList.get(i).getProfile_path()).into(imageView[i]);
                nameTextView[i].setText(celebs.celebsList.get(i).getName());
                moviesTextView[i].setText(celebs.celebsList.get(i).moviesList.get(0).getOriginal_title() + "," + celebs.celebsList.get(i).moviesList.get(1).getOriginal_title() + "," +
                        celebs.celebsList.get(i).moviesList.get(2).getOriginal_title());
                id = celebs.celebsList.get(i).getId();
                moviesTextView[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("Profile_ID", id);
                        startActivity(intent);
                    }
                });
            }
        }

        return view;
    }

    private void initViews() {

        imageView[0] = (ImageView) view.findViewById(R.id.imageView1);
        imageView[1] = (ImageView) view.findViewById(R.id.imageView2);
        imageView[2] = (ImageView) view.findViewById(R.id.imageView3);
        imageView[3] = (ImageView) view.findViewById(R.id.imageView4);
        imageView[4] = (ImageView) view.findViewById(R.id.imageView5);
        imageView[5] = (ImageView) view.findViewById(R.id.imageView6);
        imageView[6] = (ImageView) view.findViewById(R.id.imageView7);
        imageView[7] = (ImageView) view.findViewById(R.id.imageView8);
        imageView[8] = (ImageView) view.findViewById(R.id.imageView9);
        imageView[9] = (ImageView) view.findViewById(R.id.imageView10);

        nameTextView[0] = (TextView) view.findViewById(R.id.NameTextView1);
        nameTextView[1] = (TextView) view.findViewById(R.id.NameTextView2);
        nameTextView[2] = (TextView) view.findViewById(R.id.NameTextView3);
        nameTextView[3] = (TextView) view.findViewById(R.id.NameTextView4);
        nameTextView[4] = (TextView) view.findViewById(R.id.NameTextView5);
        nameTextView[5] = (TextView) view.findViewById(R.id.NameTextView6);
        nameTextView[6] = (TextView) view.findViewById(R.id.NameTextView7);
        nameTextView[7] = (TextView) view.findViewById(R.id.NameTextView8);
        nameTextView[8] = (TextView) view.findViewById(R.id.NameTextView9);
        nameTextView[9] = (TextView) view.findViewById(R.id.NameTextView10);

        moviesTextView[0] = (TextView) view.findViewById(R.id.MoviesListTextView1);
        moviesTextView[1] = (TextView) view.findViewById(R.id.MoviesListTextView2);
        moviesTextView[2] = (TextView) view.findViewById(R.id.MoviesListTextView3);
        moviesTextView[3] = (TextView) view.findViewById(R.id.MoviesListTextView4);
        moviesTextView[4] = (TextView) view.findViewById(R.id.MoviesListTextView5);
        moviesTextView[5] = (TextView) view.findViewById(R.id.MoviesListTextView6);
        moviesTextView[6] = (TextView) view.findViewById(R.id.MoviesListTextView7);
        moviesTextView[7] = (TextView) view.findViewById(R.id.MoviesListTextView8);
        moviesTextView[8] = (TextView) view.findViewById(R.id.MoviesListTextView9);
        moviesTextView[9] = (TextView) view.findViewById(R.id.MoviesListTextView10);

    }
}
