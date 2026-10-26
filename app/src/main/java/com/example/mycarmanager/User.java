package com.example.mycarmanager;

import java.util.ArrayList;

public class User {

    private String username, password, email, imgPath;
    // Uri dell'immagine del profilo scelta dalla galleria, null se non impostata
    private String imageUri;
    private ArrayList<Car> garage = new ArrayList<>();
    protected static ArrayList<User> users = new ArrayList<>();

    public User(){

        this.setUsername("");
        this.setPassword("");
        this.setImgPath("");
        this.setEmail("");
    }

    public User(String username, String password, String email, String imgPath) {

        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setImgPath(imgPath);

        boolean check = false;

        for(int i = 0; i < users.size(); i++){

            if(users.get(i).getUsername().equals(username)) {
                check = true;
                break;
            }
        }

        if(!check)
            users.add(this);

        // L'utente demo ha già tutte le auto del catalogo nel garage
        if(username.equals("user"))
            this.garage.addAll(Car.getCatalog());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // Immagine del profilo predefinita, usata se non è stata scelta un'immagine dalla galleria
    public int getImgResource() {
        switch (imgPath) {
            case "profile_pic_2": return R.drawable.profile_pic_2;
            case "profile_pic_3": return R.drawable.profile_pic_3;
            case "profile_pic_4": return R.drawable.profile_pic_4;
            case "profile_pic_5": return R.drawable.profile_pic_5;
            case "profile_pic_6": return R.drawable.profile_pic_6;
            default: return R.drawable.default_profile_pic;
        }
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public ArrayList<Car> getGarage() {
        return garage;
    }
}