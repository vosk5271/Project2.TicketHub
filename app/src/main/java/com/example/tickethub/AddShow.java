package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tickethub.Database.MovieDatabase;
import com.example.tickethub.Database.ShowDatabase;
import com.example.tickethub.Database.TheatreDatabase;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.Model.TheatreModel;
import com.example.tickethub.databinding.ActivityAddShowBinding;

import java.util.List;

public class AddShow extends AppCompatActivity {

    ActivityAddShowBinding binding;
    String movieId="";
    String theatreId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieId= getIntent().getStringExtra("movieId");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtTime.getText().toString().equals("")){
                    Toast.makeText(AddShow.this, "Enter Showtime", Toast.LENGTH_SHORT).show();
                }else if(binding.edtDuration.getText().toString().equals("")){
                    Toast.makeText(AddShow.this, "Enter Movie Duration", Toast.LENGTH_SHORT).show();
                }else {
                    getSavedShow();

                }

            }
        });

        getTheatreList();

        binding.theatreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.theatreName.showDropDown();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.theatreName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TheatreModel model= (TheatreModel) adapterView.getItemAtPosition(i);
                theatreId= String.valueOf(model.getId());
            }
        });
    }

    private void getTheatreList() {

        class GetSavedTasks extends AsyncTask<Void, Void, List<TheatreModel>> {
            @Override
            protected List<TheatreModel> doInBackground(Void... voids) {
                TheatreDatabase theatreDatabase=TheatreDatabase.getInstance(AddShow.this);
                List<TheatreModel> theatreModels = theatreDatabase.theatreDao().getMovieTheatreList(movieId);
                return theatreModels;
            }

            @Override
            protected void onPostExecute(List<TheatreModel> val) {
                super.onPostExecute(val);
                if (val.size()!=0){
                    ArrayAdapter<TheatreModel> adapter = new ArrayAdapter<TheatreModel>(getApplicationContext(),
                            android.R.layout.simple_dropdown_item_1line, val);
                    binding.theatreName.setAdapter(adapter);

                }else {
                    binding.theatreName.setText("No theatre added!!");
                    binding.theatreName.setEnabled(false);
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }

    private void getSavedShow() {
        String time=binding.edtTime.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                ShowDatabase showDatabase=ShowDatabase.getInstance(AddShow.this);
                int val = showDatabase.showDao().isDataExist(time,movieId,theatreId);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==0){
                    addShow();
                }else {
                    Toast.makeText(AddShow.this, "Show already exixts!!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    private void addShow() {
        String time=binding.edtTime.getText().toString();
        String duration=binding.edtDuration.getText().toString();
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                ShowModel model= new ShowModel(time,duration,movieId,theatreId,40,40);
                ShowDatabase showDatabase=ShowDatabase.getInstance(AddShow.this);
                long val = showDatabase.showDao().insertShow(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){
                    Toast.makeText(AddShow.this, "Show not added", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(AddShow.this, "Show added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("type","Admin");
                    startActivity(new Intent(AddShow.this, MainActivity.class).putExtras(bundle));
                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();

    }

}