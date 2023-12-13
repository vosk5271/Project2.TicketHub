package com.example.tickethub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tickethub.AddCast;
import com.example.tickethub.AddShow;
import com.example.tickethub.AddTheatre;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<MovieModel> movieModels=new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context mContext, List<MovieModel> movieModels) {
        this.movieModels = movieModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_movie,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText(movieModels.get(i).getName());
        viewHolder.genre.setText("Genre : "+movieModels.get(i).getGenre());
        viewHolder.time.setText("Duration : "+movieModels.get(i).getDuration());
        viewHolder.date.setText("Release Date : "+movieModels.get(i).getDate());


            Glide.with(mContext)
                    .load(movieModels.get(i).getPoster())
                    .into(viewHolder.img);

        viewHolder.add_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", String.valueOf(movieModels.get(i).getId()));
                mContext.startActivity(new Intent(mContext, AddShow.class).putExtras(bundle));
            }
        });
        viewHolder.add_cast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", String.valueOf(movieModels.get(i).getId()));
                mContext.startActivity(new Intent(mContext, AddCast.class).putExtras(bundle));
            }
        });
        viewHolder.add_theatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", String.valueOf(movieModels.get(i).getId()));
                mContext.startActivity(new Intent(mContext, AddTheatre.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,genre,time,date;
        Button add_show,add_cast,add_theatre;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.movie);
            genre=itemView.findViewById(R.id.genre);
            time=itemView.findViewById(R.id.time);
            date=itemView.findViewById(R.id.date);
            add_show= itemView.findViewById(R.id.shows);
            add_cast= itemView.findViewById(R.id.cast);
            add_theatre= itemView.findViewById(R.id.add_theatre);
            img= itemView.findViewById(R.id.img);

        }
    }

}

