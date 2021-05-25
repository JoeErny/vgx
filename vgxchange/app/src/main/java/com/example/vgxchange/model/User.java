package com.example.vgxchange.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user")
public class User
{
    @PrimaryKey
    @NotNull
    public String id;
    public String name;
    public String firstname;
    public String mail;
    public String login;
    public String telephone;
    public String password;

    public User(String id, String name, String firstname, String mail, String login, String telephone, String password)
    {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.mail = mail;
        this.login = login;
        this.telephone = telephone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        firstname = firstname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}