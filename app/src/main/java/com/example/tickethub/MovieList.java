package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tickethub.Adapter.BookingAdapter;
import com.example.tickethub.Adapter.MovieAdapter;
import com.example.tickethub.Adapter.UserAdapter;
import com.example.tickethub.Database.MovieDatabase;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.databinding.ActivityMainBinding;
import com.example.tickethub.databinding.ActivityMovieListBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity {

    ActivityMovieListBinding binding;

    MovieAdapter movieAdapter;
    List<MovieModel> movieModels= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.rvMovielist.setHasFixedSize(true);
        binding.rvMovielist.setLayoutManager(new LinearLayoutManager(this));

        fetchMovie();

    }

    private void fetchMovie() {
        class GetSavedTasks extends AsyncTask<Void, Void, List<MovieModel>> {
            @Override
            protected List<MovieModel> doInBackground(Void... voids) {
                MovieDatabase movieDatabase=MovieDatabase.getInstance(MovieList.this);
                movieModels = movieDatabase.movieDao().getMovieList();
                return movieModels;
            }

            @Override
            protected void onPostExecute(List<MovieModel> val) {
                super.onPostExecute(val);
                if(movieModels.size()!=0){

                    movieAdapter= new MovieAdapter(MovieList.this,movieModels);
                    binding.rvMovielist.setAdapter(movieAdapter);
                    binding.noTxt.setVisibility(View.GONE);
                    binding.rvMovielist.setVisibility(View.VISIBLE);
                } else {
                    //   Toast.makeText(MovieList.this, "No Movie Found", Toast.LENGTH_SHORT).show();
                    binding.noTxt.setVisibility(View.VISIBLE);
                    binding.rvMovielist.setVisibility(View.GONE);
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();

//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                MovieDatabase movieDatabase=MovieDatabase.getInstance(MovieList.this);
//
//                if (movieDatabase.movieDao().getMovieList()!= null) {
//                    movieModels=movieDatabase.movieDao().getMovieList();
//
//
//                }else {
//                    binding.noTxt.setVisibility(View.VISIBLE);
//                    binding.rvMovielist.setVisibility(View.GONE);
//                }
//
//
//            }
//        });


    }
}