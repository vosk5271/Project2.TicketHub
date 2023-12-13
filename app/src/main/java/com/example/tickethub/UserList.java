package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tickethub.Adapter.BookingAdapter;
import com.example.tickethub.Adapter.MovieAdapter;
import com.example.tickethub.Adapter.UserAdapter;
import com.example.tickethub.Database.MovieDatabase;
import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.UserModel;
import com.example.tickethub.databinding.ActivityUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    ActivityUserListBinding binding;

    UserAdapter userAdapter;
    List<UserModel> userModels= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.rvUsserlist.setHasFixedSize(true);
        binding.rvUsserlist.setLayoutManager(new LinearLayoutManager(this));


        fetchUser();


    }

    public void fetchUser() {
        class GetSavedTasks extends AsyncTask<Void, Void, List<UserModel>> {
            @Override
            protected List<UserModel> doInBackground(Void... voids) {
                UserDatabase userDatabase=UserDatabase.getInstance(UserList.this);
                userModels = userDatabase.userDao().getUserList();
                return userModels;
            }

            @Override
            protected void onPostExecute(List<UserModel> val) {
                super.onPostExecute(val);
                if(userModels.size()!=0){

                    userAdapter= new UserAdapter(UserList.this,userModels);
                    binding.rvUsserlist.setAdapter(userAdapter);
                    binding.noTxt.setVisibility(View.GONE);
                    binding.rvUsserlist.setVisibility(View.VISIBLE);
                } else {
                    //   Toast.makeText(MovieList.this, "No Movie Found", Toast.LENGTH_SHORT).show();
                    binding.noTxt.setVisibility(View.VISIBLE);
                    binding.rvUsserlist.setVisibility(View.GONE);
                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();

    }
}