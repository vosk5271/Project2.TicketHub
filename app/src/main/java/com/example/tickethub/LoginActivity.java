package com.example.tickethub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.Model.UserModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tickethub.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);

                type=radioButton.getText().toString();
               }
        });
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("")){
                    Toast.makeText(LoginActivity.this, "Select Type", Toast.LENGTH_SHORT).show();
                }else if(binding.edtUsername.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (binding.edtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    if(type.equals("Admin")){
                        if(binding.edtUsername.getText().toString().equals("admin2") &&
                                binding.edtPassword.getText().toString().equals("admin2")){
                            Constant constant= new Constant(LoginActivity.this);
                            constant.setName("admin2");
                            Bundle bundle = new Bundle();
                            bundle.putString("type", type);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtras(bundle));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Check Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Login();

                    }
                }
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

    }

    private void Login() {
        String name=binding.edtUsername.getText().toString();

        String pass=binding.edtPassword.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                UserDatabase userDatabase=UserDatabase.getInstance(LoginActivity.this);
                int val = userDatabase.userDao().Login(name,pass);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==1){
                    getData();
                }else {
                    Toast.makeText(LoginActivity.this, "Check Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();


    }

    private void getData() {
        String name=binding.edtUsername.getText().toString();

        String pass=binding.edtPassword.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, List<UserModel>> {
            @Override
            protected List<UserModel> doInBackground(Void... voids) {
                UserDatabase userDatabase=UserDatabase.getInstance(LoginActivity.this);
                List<UserModel> models= new ArrayList<>();
                models = userDatabase.userDao().LoginData(name,pass);
                return models;
            }

            @Override
            protected void onPostExecute(List<UserModel> models) {
                super.onPostExecute(models);
                if (models.size()!=0){

                    Constant constant= new Constant(LoginActivity.this);

                    constant.setUser_id(String.valueOf(models.get(0).getUserId()));
                    constant.setNumber(models.get(0).getPhone());
                    constant.setName(models.get(0).getName());
                    constant.setEmail(models.get(0).getEmail());
                    Bundle bundle= new Bundle();
                    bundle.putString("type",type);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtras(bundle));

                    Toast.makeText(LoginActivity.this, "You have Logged In successfully", Toast.LENGTH_SHORT).show();
                }

            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }


}