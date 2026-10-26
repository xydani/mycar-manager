package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class CarCatalogTest {

    @Test
    public void catalogoConSeiAutoDiverse() {
        ArrayList<Car> catalog = Car.getCatalog();
        HashSet<String> plates = new HashSet<>();

        for (Car car : catalog)
            plates.add(car.getPlate());

        assertEquals(6, catalog.size());
        assertEquals(6, plates.size());
    }

    @Test
    public void ogniChiamataRestituisceNuoveIstanze() {
        // Ogni utente deve avere il proprio stato delle auto
        Car first = Car.getCatalog().get(0);
        Car second = Car.getCatalog().get(0);

        first.setPowerOn(!first.getPowerOn());
        first.getRadio().setVolume(first.getRadio().getVolume() + 1);

        assertNotEquals(first.getPowerOn(), second.getPowerOn());
        assertNotEquals(first.getRadio().getVolume(), second.getRadio().getVolume());
    }

    @Test
    public void utenteDemoConTutteLeAuto() {
        users.clear();
        User demo = new User("user", "user", "user@email.it", "default_profile_pic");
        User newUser = new User("mario", "Password1!", "mario@email.it", "default_profile_pic");

        assertEquals(6, demo.getGarage().size());
        assertTrue(newUser.getGarage().isEmpty());
    }
}