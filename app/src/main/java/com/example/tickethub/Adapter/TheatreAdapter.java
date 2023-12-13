package com.example.tickethub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.BookTicket;
import com.example.tickethub.MainActivity;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.TheatreModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class TheatreAdapter extends RecyclerView.Adapter<TheatreAdapter.ViewHolder> {


    private List<TheatreModel> theatreModels=new ArrayList<>();
    private Context mContext;
    String url;
    String movieName;

    public TheatreAdapter(Context mContext, List<TheatreModel> theatreModels,String url,String movieName) {
        this.theatreModels = theatreModels;
        this.mContext = mContext;
        this.url = url;
        this.movieName = movieName;
    }

    @NonNull
    @Override
    public TheatreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_theatre,viewGroup,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText(theatreModels.get(i).getName());

        viewHolder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", String.valueOf(theatreModels.get(i).getMovieId()));
                bundle.putString("theatreId", String.valueOf(theatreModels.get(i).getId()));
                bundle.putString("url", url);
                bundle.putString("movieName", movieName);
                mContext.startActivity(new Intent(mContext, BookTicket.class).putExtras(bundle));
                ((MainActivity)mContext).yourDesiredMethod();
            }
        });


    }

    @Override
    public int getItemCount() {
        return theatreModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        Button select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            select=itemView.findViewById(R.id.select);
        }
    }

}

