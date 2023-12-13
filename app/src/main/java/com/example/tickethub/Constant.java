package com.example.tickethub;

import android.content.Context;
import android.content.SharedPreferences;

public class Constant {
    String user_id,name,number,email;
    private Context context;

    SharedPreferences sharedPreferences;


    public Constant(Context context) {

        this.context=context;

        sharedPreferences=context.getSharedPreferences("login_details", Context.MODE_PRIVATE);

    }
    public String getUser_id() {
        user_id=sharedPreferences.getString("user_id","");
        return user_id;
    }
    public void setUser_id(String  user_id) {
        sharedPreferences.edit().putString("user_id",user_id).commit();
        this.user_id = user_id;
    }
    public String getName() {
        name=sharedPreferences.getString("name","");
        return name;
    }
    public void setName(String  name) {
        sharedPreferences.edit().putString("name",name).commit();
        this.name = name;
    }
    public String getNumber() {
        number=sharedPreferences.getString("number","");
        return number;
    }
    public void setNumber(String  number) {
        sharedPreferences.edit().putString("number",number).commit();
        this.number = number;
    }
    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;
    }
    public void setEmail(String  email) {
        sharedPreferences.edit().putString("email",Constant.this.email).commit();
        this.email = Constant.this.email;
    }

    public  void  remove(){

        sharedPreferences.edit().clear().commit();

    }
}
