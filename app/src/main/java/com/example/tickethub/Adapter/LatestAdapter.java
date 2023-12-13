package com.example.tickethub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tickethub.BookTicket;
import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.TheatreDatabase;
import com.example.tickethub.MainActivity;
import com.example.tickethub.Model.CastModel;

import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.ScreenModel;
import com.example.tickethub.Model.TheatreModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    List<MovieModel> latestModels= new ArrayList<>();
    private Context mContext;
    public AlertDialog alertDialog;

    public LatestAdapter(Context mContext,List<MovieModel> latestModels) {
        this.latestModels = latestModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_page,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(latestModels.get(i).getPoster())
                .into(viewHolder.image);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_movie_details, null, false);
                ImageView img = dialogView.findViewById(R.id.img);
                TextView name = dialogView.findViewById(R.id.name);
                TextView type = dialogView.findViewById(R.id.type);
                TextView genre = dialogView.findViewById(R.id.genre);
                TextView release_date = dialogView.findViewById(R.id.release_date);
                TextView duration = dialogView.findViewById(R.id.duration);
                Button theatre = dialogView.findViewById(R.id.theatre);
                RecyclerView rv_cast = dialogView.findViewById(R.id.rv_cast);
                LinearLayout lin_theatre=dialogView.findViewById(R.id.lin_theatre);
                TextView no_cast=dialogView.findViewById(R.id.no_cast);
                TextView no_theatre=dialogView.findViewById(R.id.no_theatre);
                RecyclerView rv_theatre = dialogView.findViewById(R.id.rv_theatre);



                name.setText(latestModels.get(i).getName());
                if(latestModels.get(i).getTwo_d()==true && latestModels.get(i).getThree_d()==true){
                    type.setText("2D & 3D");
                }else if(latestModels.get(i).getTwo_d()==false && latestModels.get(i).getThree_d()==true){
                    type.setText("3D");
                }else if(latestModels.get(i).getTwo_d()==true && latestModels.get(i).getThree_d()==false){
                    type.setText("2D");
                }else {
                    type.setText("All");
                }
                genre.setText(latestModels.get(i).getGenre());

                duration.setText(latestModels.get(i).getDuration());

                release_date.setText(latestModels.get(i).getDate());
                rv_cast.setHasFixedSize(true);
                rv_cast.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

                rv_theatre.setHasFixedSize(true);
                rv_theatre.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

                class GetSavedCast extends AsyncTask<Void, Void, List<CastModel>> {
                    @Override
                    protected List<CastModel> doInBackground(Void... voids) {
                        CastDatabase castDatabase=CastDatabase.getInstance(mContext);
                        List<CastModel> model = castDatabase.castDao().getCastList(String.valueOf(latestModels.get(i).getId()));
                        return model;
                    }

                    @Override
                    protected void onPostExecute(List<CastModel> val) {
                        super.onPostExecute(val);
                        if (val.size()==0){
                            rv_cast.setVisibility(View.GONE);
                            no_cast.setVisibility(View.VISIBLE);
                        }else {
                            CastAdapter adapter= new CastAdapter(mContext,val);
                            rv_cast.setAdapter(adapter);
                            rv_cast.setVisibility(View.VISIBLE);
                            no_cast.setVisibility(View.GONE);
                        }
                    }
                }

                GetSavedCast savedCast = new GetSavedCast();
                savedCast.execute();

                rv_theatre.setHasFixedSize(true);
                rv_theatre.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

                class GetSavedTheatre extends AsyncTask<Void, Void, List<TheatreModel>> {
                    @Override
                    protected List<TheatreModel> doInBackground(Void... voids) {
                        TheatreDatabase theatreDatabase=TheatreDatabase.getInstance(mContext);
                        List<TheatreModel> model = theatreDatabase.theatreDao().getMovieTheatreList(String.valueOf(latestModels.get(i).getId()));
                        return model;
                    }

                    @Override
                    protected void onPostExecute(List<TheatreModel> val) {
                        super.onPostExecute(val);
                        if (val.size()==0){
                            rv_theatre.setVisibility(View.GONE);
                            no_theatre.setVisibility(View.VISIBLE);
                        }else {
                            TheatreAdapter adapter= new TheatreAdapter(mContext,val,latestModels.get(i).getPoster(),
                                    latestModels.get(i).getName());
                            rv_theatre.setAdapter(adapter);
                            rv_theatre.setVisibility(View.VISIBLE);
                            no_theatre.setVisibility(View.GONE);
                        }
                    }
                }

                GetSavedTheatre savedTheatre = new GetSavedTheatre();
                savedTheatre.execute();

                theatre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lin_theatre.setVisibility(View.VISIBLE);
                    }
                });

                Glide.with(mContext)
                        .asBitmap()
                        .load(latestModels.get(i).getPoster())
                        .into(img);


                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return latestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.img);
        }
    }


}
