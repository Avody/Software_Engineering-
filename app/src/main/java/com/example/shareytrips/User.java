package com.example.shareytrips;

public class User {
    private String fullname;
    private String email;
    private String password;
    private String interests;
    private String id;


    public User(String fullname, String email, String password, String interests,String id) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.interests = interests;
        this.id=id;
    }

    /*getters and setters*/
    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getInterests() {
        return interests;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

}
