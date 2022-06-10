package com.example.shareytrips;

public class Post {
    private String city;
    private String company_choice;
    private String cost;
    private String smallDesc;
    private String date1;
    private String date2;
    private String bigDesc;
    private int rating;
    private String username;

    public Post(String city, String company_choice, String cost, String smallDesc, String date1, String date2, String bigDesc, int rating, String username){
        this.city = city;
        this.company_choice = company_choice;
        this.cost = cost;
        this.smallDesc = smallDesc;
        this.date1 = date1;
        this.date2 = date2;
        this.bigDesc = bigDesc;
        this.rating = rating;
        this.username = username;


    }
    public Post(){

    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany_choice() {
        return company_choice;
    }

    public void setCompany_choice(String company_choice) {
        this.company_choice = company_choice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        this.smallDesc = smallDesc;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBigDesc() {
        return bigDesc;
    }

    public void setBigDesc(String bigDesc) {
        this.bigDesc = bigDesc;
    }
}
