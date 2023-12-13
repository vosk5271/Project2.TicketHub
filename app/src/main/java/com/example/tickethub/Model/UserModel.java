package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserModel")

public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pass")
    private String pass;

    public UserModel(int userId, String name, String phone, String email, String pass) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    @Ignore
    public UserModel(int userId, String name, String phone, String email) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    @Ignore
    public UserModel(String name, String phone, String email, String pass) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
