package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.ShowDatabase;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.databinding.ActivityAddCastBinding;

public class AddCast extends AppCompatActivity {

    ActivityAddCastBinding binding;
    String movieId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddCastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        movieId= getIntent().getStringExtra("movieId");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.addCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtName.getText().toString().equals("")){
                    Toast.makeText(AddCast.this, "Enter Cast Name", Toast.LENGTH_SHORT).show();
                }else if(binding.edtRole.getText().toString().equals("")){
                    Toast.makeText(AddCast.this, "Enter Role", Toast.LENGTH_SHORT).show();
                }else {
                    getSavedCast();

                }

            }
        });

    }
    private void getSavedCast() {
        String name=binding.edtName.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                CastDatabase castDatabase=CastDatabase.getInstance(AddCast.this);
                int val = castDatabase.castDao().isDataExist(name,movieId);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==0){
                    addCast();
                }else {
                    Toast.makeText(AddCast.this, "Cast already exixts!!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    private void addCast() {
        String name=binding.edtName.getText().toString();
        String role=binding.edtRole.getText().toString();
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                CastModel model= new CastModel(name,role,movieId);
                CastDatabase castDatabase= CastDatabase.getInstance(AddCast.this);
                long val = castDatabase.castDao().insertCast(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){
                    Toast.makeText(AddCast.this, "Cast not added", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(AddCast.this, "Cast added", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("type","Admin");
                    startActivity(new Intent(AddCast.this, MainActivity.class).putExtras(bundle));
                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();

    }
}