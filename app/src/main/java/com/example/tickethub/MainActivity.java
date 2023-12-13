package com.example.tickethub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tickethub.Adapter.CastAdapter;
import com.example.tickethub.Adapter.LatestAdapter;


import com.example.tickethub.Adapter.TheatreAdapter;
import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.MovieDatabase;
import com.example.tickethub.Database.TheatreDatabase;
import com.example.tickethub.Model.CastModel;

import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.ScreenModel;
import com.example.tickethub.Model.SearchModel;
import com.example.tickethub.Model.TheatreModel;
import com.example.tickethub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private List<MovieModel> top_movieModel = new ArrayList<MovieModel>();
    private List<MovieModel> searchModels = new ArrayList<MovieModel>();
    LatestAdapter movieAdapter;
    AlertDialog alertDialog;
    public String type="";
    Constant constant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        constant= new Constant(this);

        type=getIntent().getStringExtra("type");
        //type="User";
      //  type="Admin";
        if (type.equals("Admin")){
            binding.linearAdmin.setVisibility(View.VISIBLE);
            binding.userLinear.setVisibility(View.GONE);
            binding.adminName.setText("Welcome "+constant.getName());
        }else{
            binding.linearAdmin.setVisibility(View.GONE);
            binding.userLinear.setVisibility(View.VISIBLE);
            binding.userName.setText("Welcome "+constant.getName());
        }

        binding.addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddMovie.class));
            }
        });
        binding.viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserList.class));
            }
        });
        binding.movieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MovieList.class));
            }
        });
        binding.viewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putString("type","Admin");
                startActivity(new Intent(MainActivity.this,BookingList.class).putExtras(bundle));
            }
        });
        binding.viewUserBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putString("type","User");
                startActivity(new Intent(MainActivity.this,BookingList.class).putExtras(bundle));
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Admin")){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }else {
                    constant.remove();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });
        binding.rvLatest.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,3);

        binding.rvLatest.setLayoutManager(gridLayoutManager);


        fetchMovie();



        binding.editSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MovieModel movieModel= (MovieModel) adapterView.getItemAtPosition(i);

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_movie_details, null, false);
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

                name.setText(movieModel.getName());
                if(movieModel.getTwo_d()==true && movieModel.getThree_d()==true){
                    type.setText("2D & 3D");
                }else if(movieModel.getTwo_d()==false && movieModel.getThree_d()==true){
                    type.setText("3D");
                }else if(movieModel.getTwo_d()==true && movieModel.getThree_d()==false){
                    type.setText("2D");
                }else {
                    type.setText("All");
                }

                genre.setText(movieModel.getGenre());

                duration.setText(movieModel.getDuration());

                release_date.setText(movieModel.getDate());

                rv_cast.setHasFixedSize(true);
                rv_cast.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                class GetSavedCast extends AsyncTask<Void, Void, List<CastModel>> {
                    @Override
                    protected List<CastModel> doInBackground(Void... voids) {
                        CastDatabase castDatabase=CastDatabase.getInstance(MainActivity.this);
                        List<CastModel> model = castDatabase.castDao().getCastList(String.valueOf(movieModel.getId()));
                        return model;
                    }

                    @Override
                    protected void onPostExecute(List<CastModel> val) {
                        super.onPostExecute(val);
                        if (val.size()==0){
                            rv_cast.setVisibility(View.GONE);
                            no_cast.setVisibility(View.VISIBLE);
                        }else {
                            CastAdapter adapter= new CastAdapter(MainActivity.this,val);
                            rv_cast.setAdapter(adapter);
                            rv_cast.setVisibility(View.VISIBLE);
                            no_cast.setVisibility(View.GONE);
                        }
                    }
                }

                GetSavedCast savedCast = new GetSavedCast();
                savedCast.execute();

                rv_theatre.setHasFixedSize(true);
                rv_theatre.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                class GetSavedTheatre extends AsyncTask<Void, Void, List<TheatreModel>> {
                    @Override
                    protected List<TheatreModel> doInBackground(Void... voids) {
                        TheatreDatabase theatreDatabase=TheatreDatabase.getInstance(MainActivity.this);
                        List<TheatreModel> model = theatreDatabase.theatreDao().getMovieTheatreList(String.valueOf(movieModel.getId()));
                        return model;
                    }

                    @Override
                    protected void onPostExecute(List<TheatreModel> val) {
                        super.onPostExecute(val);
                        if (val.size()==0){
                            rv_theatre.setVisibility(View.GONE);
                            no_theatre.setVisibility(View.VISIBLE);
                        }else {
                            TheatreAdapter adapter= new TheatreAdapter(MainActivity.this,val,movieModel.getPoster(),movieModel.getName());
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

                Glide.with(MainActivity.this)
                        .asBitmap()
                        .load(movieModel.getPoster())
                        .into(img);


                builder.setView(dialogView);
                alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }
    private void fetchMovie() {
        class GetSavedTasks extends AsyncTask<Void, Void, List<MovieModel>> {
            @Override
            protected List<MovieModel> doInBackground(Void... voids) {
                MovieDatabase movieDatabase=MovieDatabase.getInstance(MainActivity.this);
                top_movieModel = movieDatabase.movieDao().getMovieList();
                return top_movieModel;
            }

            @Override
            protected void onPostExecute(List<MovieModel> val) {
                super.onPostExecute(val);
                if(top_movieModel.size()!=0){

                    movieAdapter= new LatestAdapter(MainActivity.this,top_movieModel);
                    binding.rvLatest.setAdapter(movieAdapter);

                    ArrayAdapter<MovieModel> adapter = new ArrayAdapter<MovieModel>(getApplicationContext(),
                             android.R.layout.simple_dropdown_item_1line, top_movieModel);
                    binding.editSearch.setAdapter(adapter);
                    binding.noTxt.setVisibility(View.GONE);
                    binding.linAllMovie.setVisibility(View.VISIBLE);
                } else {
                    //   Toast.makeText(MainActivity.this, "No Movie Found", Toast.LENGTH_SHORT).show();
                    binding.noTxt.setVisibility(View.VISIBLE);
                    binding.linAllMovie.setVisibility(View.GONE);
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();




    }

    public void yourDesiredMethod() {
       finish();
    }
}