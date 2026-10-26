package com.example.mycarmanager;

import java.util.ArrayList;

class AirConditioning {
    public Boolean isAirConditioningOn;
    public float temperature;
    public int powerLevel;

    public AirConditioning(Boolean isAirConditioningOn, float temperature, int powerLevel) {
        this.isAirConditioningOn = isAirConditioningOn;
        this.powerLevel = powerLevel;
        this.temperature = temperature;
    }

    public Boolean getIsAirConditioningOn() {
        return isAirConditioningOn;
    }

    public void setIsAirConditioningOn(Boolean isAirConditioningOn) {
        this.isAirConditioningOn = isAirConditioningOn;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
}

class Radio {
    public Boolean isRadioOn;
    public float radioStation;
    public int volume;

    public Radio(Boolean isRadioOn, float radioStation, int volume) {
        this.isRadioOn = isRadioOn;
        this.radioStation = radioStation;
        this.volume = volume;
    }

    public Boolean getIsRadioOn () {
        return isRadioOn;
    }

    public void setIsRadioOn (Boolean isRadioOn) {
        this.isRadioOn = isRadioOn;
    }

    public float getRadioStation () {
        return radioStation;
    }

    public void setRadioStation (float radioStation) {
        this.radioStation = radioStation;
    }

    public int getVolume () {
        return volume;
    }

    public void setVolume (int volume) {
        this.volume = volume;
    }
}

public class Car {

    private String brand, name, image, plate, fuelType, carType;
    private String doors, weight, seats, abs, gears, consumption, emissions, horsepower;
    private String acceleration, fuelOrEnergyLevel;
    private double latitude, longitude;

    // Variabili impostazioni manage
    private Boolean isPowerOn;
    private int windowsLevel;
    private AirConditioning airConditioning;
    private Boolean isCarUnlocked;
    private Radio radio;
    private Boolean areLightsOn;

    public Car(String brand, String name, String image, String plate,
               String fuelType, String carType, String doors, String weight,
               String seats, String abs, String gears, String consumption, String emissions,
               String horsepower, String acceleration, String fuelOrEnergyLevel,
               double latitude, double longitude,

               Boolean isPowerOn, int windowsLevel, AirConditioning airConditioning,
               Boolean isCarUnlocked, Radio radio, Boolean areLightsOn) {

        this.brand = brand;
        this.name = name;
        this.image = image;
        this.plate = plate;
        this.fuelType = fuelType;
        this.carType = carType;
        this.doors = doors;
        this.weight = weight;
        this.seats = seats;
        this.abs = abs;
        this.gears = gears;
        this.consumption = consumption;
        this.emissions = emissions;
        this.horsepower = horsepower;
        this.acceleration = acceleration;
        this.fuelOrEnergyLevel = fuelOrEnergyLevel;
        this.latitude = latitude;
        this.longitude = longitude;

        // Impostazioni manage
        this.isPowerOn = isPowerOn;
        this.windowsLevel = windowsLevel;
        this.airConditioning = new AirConditioning(airConditioning.isAirConditioningOn, airConditioning.temperature, airConditioning.powerLevel);
        this.isCarUnlocked = isCarUnlocked;
        this.radio = new Radio(radio.isRadioOn, radio.radioStation, radio.volume);
        this.areLightsOn = areLightsOn;
    }

    // Catalogo delle auto disponibili, con dati di esempio (non c'è un vero collegamento all'auto)
    // Ogni chiamata restituisce nuove istanze, così ogni utente ha il proprio stato delle auto
    public static ArrayList<Car> getCatalog() {
        ArrayList<Car> catalog = new ArrayList<>();

        catalog.add(new Car("Volkswagen", "Polo", "volkswagenPolo",
                "GH236FF", "petrol", "sedan", "5", "1478",
                "5", "abs", "5", "22", "103", "76",
                "10.9", "98", 39.222334, 9.114042,
                true, 100, new AirConditioning(true,
                18.0f, 3), true, new Radio(true,
                103.2f, 25), false));
        catalog.add(new Car("BMW", "I3", "bmwI3",
                "GK211TR", "electric", "citycar", "5", "1345",
                "5", "abs", "5", "12.9", "9.3", "170",
                "7.1", "67", 40.72501018307786, 8.564402393845278,
                false, 95, new AirConditioning(false,
                0f, 0), false, new Radio(false,
                0f, 0), false));
        catalog.add(new Car("Jeep", "Cherokee", "jeepCherokee",
                "GS011FA", "diesel", "suv", "5", "2097",
                "5", "abs", "6", "15.7", "385", "380",
                "6.3", "87", 40.32217776840813, 9.305939442833397,
                true, 0, new AirConditioning(true,
                28f, 4), false, new Radio(true,
                69.2f, 10), true));
        catalog.add(new Car("Skoda", "Enyaq", "skodaEniaq",
                "PD007DS", "electric", "suv", "5", "2120",
                "5", "abs", "6", "19.6", "112", "177",
                "8.5", "50", 39.88222501781158, 8.60992834858646,
                false, 25, new AirConditioning(false,
                0f, 0), true, new Radio(true,
                88.5f, 21), true));
        catalog.add(new Car("Dodge", "Challenger", "dodgeChallenger",
                "EL334BK", "petrol", "sportive", "3", "2032",
                "5", "abs", "8", "14.6", "232", "375",
                "6.8", "25", 40.966031679556814, 8.208155130870656,
                true, 0, new AirConditioning(false,
                0f, 0), false, new Radio(false,
                0f, 0), false));
        catalog.add(new Car("Toyota", "Yaris", "toyotaYaris",
                "BR772WF", "diesel", "citycar", "3", "1170",
                "5", "abs", "5", "8.2", "92", "116",
                "11.2", "98", 40.21429794421172, 9.280052406532452,
                true, 100, new AirConditioning(true,
                16f, 3), false, new Radio(false,
                0f, 0), true));

        return catalog;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getGears() {
        return gears;
    }

    public void setGears(String gears) {
        this.gears = gears;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getEmissions() {
        return emissions;
    }

    public void setEmissions(String emissions) {
        this.emissions = emissions;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public String getFuelOrEnergyLevel() {
        return fuelOrEnergyLevel;
    }

    public void setFuelOrEnergyLevel(String fuelOrEnergyLevel) {
        this.fuelOrEnergyLevel = fuelOrEnergyLevel;
    }

    public Boolean getPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(Boolean powerOn) {
        isPowerOn = powerOn;
    }

    public int getWindowsLevel() {
        return windowsLevel;
    }

    public void setWindowsLevel(int windowsLevel) {
        this.windowsLevel = windowsLevel;
    }

    public Radio getRadio() {
        return radio;
    }

    public void setRadio(Radio radio) {
        this.radio.isRadioOn = radio.isRadioOn;
        this.radio.radioStation = radio.radioStation;
        this.radio.volume = radio.volume;
    }

    public Boolean getCarUnlocked() {
        return isCarUnlocked;
    }

    public void setCarUnlocked(Boolean carUnlocked) {
        isCarUnlocked = carUnlocked;
    }

    public Boolean getAreLightsOn() {
        return areLightsOn;
    }

    public void setAreLightsOn(Boolean areLightsOn) {
        this.areLightsOn = areLightsOn;
    }

    public AirConditioning getAirConditioning() {return airConditioning; }
    public void setAirConditioning (AirConditioning airConditioning) {
        this.airConditioning.isAirConditioningOn = airConditioning.isAirConditioningOn;
        this.airConditioning.powerLevel = airConditioning.powerLevel;
        this.airConditioning.temperature = airConditioning.temperature;
    }
}