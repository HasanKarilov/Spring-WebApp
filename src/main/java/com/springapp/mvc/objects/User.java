package com.springapp.mvc.objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by hanaria on 1/27/16.
 */
public class User {
    @NotNull
    @Size(min = 3, message = "name length have to be over 3")
    private String name;
    @Size(min = 6, message = "password length hava to be over 6")
    private String password;
    private boolean isAdmin;

    public User() {
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
