package com.example.vgxchange.api.dto;

public class UserAuthentification
{
    public String login;
    public String password;

    public UserAuthentification(String login, String password)
    {
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}