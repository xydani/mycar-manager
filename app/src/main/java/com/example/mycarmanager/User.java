package com.example.mycarmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String username, password, email, imgPath;
    private ArrayList<Car> garage = new ArrayList<>();
    protected static ArrayList<User> users = new ArrayList<>();
    protected static Map<String, String> imageMap = new HashMap<>();
    protected static boolean adminCreated = false;

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

        if(username.equals("user")){

            Car volkswagenPolo = new Car("Volkswagen", "Polo", "volkswagenPolo",
                    "GH236FF", "petrol", "sedan", "5", "1478",
                    "5", "abs", "5", "22", "103", "76",
                    "10.9", "98", 39.222334, 9.114042,
                    true, 100, new AirConditioning(true,
                    18.0f, 3), true, new Radio(true,
                    103.2f, 25), false);

            Car bmwI3 = new Car("BMW", "I3", "bmwI3",
                    "GK211TR", "electric", "citycar", "5", "1345",
                    "5", "abs", "5", "12.9", "9.3", "170",
                    "7.1", "67", 40.72501018307786, 8.564402393845278,
                    false, 95, new AirConditioning(false,
                    0f, 0), false, new Radio(false,
                    0f, 0), false);

            Car jeepCherokee = new Car("Jeep", "Cherokee", "jeepCherokee",
                    "GS011FA", "diesel", "suv", "5", "2097",
                    "5", "abs", "6", "15.7", "385", "380",
                    "6.3", "87", 40.32217776840813, 9.305939442833397,
                    true, 0, new AirConditioning(true,
                    28f, 4), false, new Radio(true,
                    69.2f, 10), true);

            Car skodaEnyaq = new Car("Skoda", "Enyaq", "skodaEniaq",
                    "PD007DS", "electric", "suv", "5", "2120",
                    "5", "abs", "6", "19.6", "112", "177",
                    "8.5", "50", 39.88222501781158, 8.60992834858646,
                    false, 25, new AirConditioning(false,
                    0f, 0), true, new Radio(true,
                    88.5f, 21), true);

            Car dodgeChallenger = new Car("Dodge", "Challenger", "dodgeChallenger",
                    "EL334BK", "petrol", "sportive", "3", "2032",
                    "5", "abs", "8", "14.6", "232", "375",
                    "6.8", "25", 40.966031679556814, 8.208155130870656,
                    true, 0, new AirConditioning(false,
                    0f, 0), false, new Radio(false,
                    0f, 0), false);

            Car toyotaYaris = new Car("Toyota", "Yaris", "toyotaYaris",
                    "BR772WF", "diesel", "citycar", "3", "1170",
                    "5", "abs", "5", "8.2", "92", "116",
                    "11.2", "98", 40.21429794421172, 9.280052406532452,
                    true, 100, new AirConditioning(true,
                    16f, 3), false, new Radio(false,
                    0f, 0), true);

            this.garage.add(volkswagenPolo);
            this.garage.add(bmwI3);
            this.garage.add(jeepCherokee);
            this.garage.add(skodaEnyaq);
            this.garage.add(dodgeChallenger);
            this.garage.add(toyotaYaris);
        }
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

    public ArrayList<Car> getGarage() {
        return garage;
    }
}