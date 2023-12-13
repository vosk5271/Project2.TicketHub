package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tickethub.Database.TheatreDatabase;
import com.example.tickethub.Model.TheatreModel;
import com.example.tickethub.databinding.ActivityAddTheatreBinding;

public class AddTheatre extends AppCompatActivity {

    ActivityAddTheatreBinding binding;
    String movieId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddTheatreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieId= getIntent().getStringExtra("movieId");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtName.getText().toString().equals("")){
                    Toast.makeText(AddTheatre.this, "Enter Theatre Name", Toast.LENGTH_SHORT).show();
                }else if(binding.edtAddress.getText().toString().equals("")){
                    Toast.makeText(AddTheatre.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }else {
                    getSavedTheatre();

                }

            }
        });
    }
    private void getSavedTheatre() {
        String name=binding.edtName.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                TheatreDatabase theatreDatabase=TheatreDatabase.getInstance(AddTheatre.this);
                int val = theatreDatabase.theatreDao().isDataExist(name,movieId);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==0){
                    addTheatre();
                }else {
                    Toast.makeText(AddTheatre.this, "Theatre already exixts!!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    private void addTheatre() {
        String name=binding.edtName.getText().toString();
        String address=binding.edtAddress.getText().toString();
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                TheatreModel model= new TheatreModel(name,address,movieId);
                TheatreDatabase theatreDatabase=TheatreDatabase.getInstance(AddTheatre.this);
                long val = theatreDatabase.theatreDao().insertTheatre(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){
                    Toast.makeText(AddTheatre.this, "Theatre not added", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(AddTheatre.this, "Theatre added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("type","Admin");
                    startActivity(new Intent(AddTheatre.this, MainActivity.class).putExtras(bundle));
                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();

    }
}