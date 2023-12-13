package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tickethub.Adapter.UserAdapter;
import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.UserModel;
import com.example.tickethub.databinding.ActivityLoginBinding;
import com.example.tickethub.databinding.ActivitySignupBinding;

import java.util.List;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);

                type = radioButton.getText().toString();
            }
        });
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("")){
                    Toast.makeText(SignupActivity.this, "Select Type", Toast.LENGTH_SHORT).show();
                }else if(binding.edtName.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }else if(binding.edtEmail.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "Enter Email Id", Toast.LENGTH_SHORT).show();
                }else if(binding.edtPhone.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }else if (binding.edtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                }else if (!binding.edtPassword.getText().toString().equals(binding.edtCnfrmPass.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password should be Same", Toast.LENGTH_SHORT).show();
                }else {
                    fetchUser();

                }
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
    private void fetchUser() {
        String name=binding.edtName.getText().toString();
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                UserDatabase userDatabase=UserDatabase.getInstance(SignupActivity.this);
                int val = userDatabase.userDao().isDataExist(name);
                return val;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val==0){
                    SignUP();
                }else {
                    Toast.makeText(SignupActivity.this, "User already exixts!!", Toast.LENGTH_SHORT).show();
                }

            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();

    }
    private void SignUP() {
        String name=binding.edtName.getText().toString();
        String email=binding.edtEmail.getText().toString();
        String phone=binding.edtPhone.getText().toString();
        String pass=binding.edtPassword.getText().toString();

        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                UserModel model= new UserModel(name,email,phone,pass);
                UserDatabase userDatabase=UserDatabase.getInstance(SignupActivity.this);

                long val = userDatabase.userDao().insertUser(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){
                    Toast.makeText(SignupActivity.this, "User not added", Toast.LENGTH_SHORT).show();

                }else {
                    Constant constant= new Constant(SignupActivity.this);
                    constant.setUser_id(val.toString());
                    constant.setName(name);
                    constant.setEmail(email);
                    constant.setNumber(phone);
                    Bundle bundle= new Bundle();
                    bundle.putString("type",type);
                    Toast.makeText(SignupActivity.this, "You have Signed up successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this,MainActivity.class).putExtras(bundle));
finish();
                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();





    }
}