package com.example.shanksvilla.model;

public class User {

    private String name;
    private String imageUri;
    private String id;
    private String email;


    //Empty constructor for null data
    public User() {
    }


    //parameterised constructor for data
    public User(String name, String imageUri, String id, String email) {
        this.name = name;
        this.imageUri = imageUri;
        this.id = id;
        this.email = email;
    }

    /*
    Getters and setters for every field to provide abstraction
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
