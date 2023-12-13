package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tickethub.Database.MovieDatabase;
import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.UserModel;
import com.example.tickethub.databinding.ActivityAddMovieBinding;

public class AddMovie extends AppCompatActivity {

    ActivityAddMovieBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtName.getText().toString().equals("")){
                    Toast.makeText(AddMovie.this, "Enter Movie Name", Toast.LENGTH_SHORT).show();
                }else if(binding.edtGenre.getText().toString().equals("")){
                    Toast.makeText(AddMovie.this, "Enter Genre", Toast.LENGTH_SHORT).show();
                }else if(binding.edtTime.getText().toString().equals("")){
                    Toast.makeText(AddMovie.this, "Enter Movie Duration", Toast.LENGTH_SHORT).show();
                }else if (binding.edtDate.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Release Date", Toast.LENGTH_SHORT).show();
                }else if (binding.edtPoster.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Poster URL", Toast.LENGTH_SHORT).show();
                }else {
                    getSavedMovie();

                }
            }
        });
    }
    private void getSavedMovie() {
        String name=binding.edtName.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                MovieDatabase movieDatabase=MovieDatabase.getInstance(AddMovie.this);
                int val = movieDatabase.movieDao().isDataExist(name);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==0){
                    addMovie();
                }else {
                    Toast.makeText(AddMovie.this, "Movie already exixts!!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    private void addMovie() {
        String name=binding.edtName.getText().toString();
        String genre=binding.edtGenre.getText().toString();
        String duration=binding.edtTime.getText().toString();
        String date=binding.edtDate.getText().toString();
        String url=binding.edtPoster.getText().toString();
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                MovieModel model= new MovieModel(name,genre,duration,date,url,binding.twod.isChecked(),binding.threed.isChecked());
                MovieDatabase movieDatabase=MovieDatabase.getInstance(AddMovie.this);
                long val = movieDatabase.movieDao().insertMovie(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
            if (val==0){
                Toast.makeText(AddMovie.this, "Movie not added", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(AddMovie.this, "Movie added", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("type","Admin");
                startActivity(new Intent(AddMovie.this, MainActivity.class).putExtras(bundle));
            }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//
//                String done="N";
//
//
//                if(done.equals("Y")){
//
//                }else if(done.equals("E")){
//                    Toast.makeText(AddMovie.this, "Movie already exixts!!", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(AddMovie.this, "Movie not added", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//
//        });
    }
}