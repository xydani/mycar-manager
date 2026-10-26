package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InputValidationTest {

    @Before
    public void setUp() {
        // Solo l'utente demo è registrato
        users.clear();
        new User("user", "user", "user@email.it", "default_profile_pic");
    }

    @Test
    public void checkUsername() {
        assertEquals("Username already in use", InputValidation.checkUsername("user"));
        assertEquals("Username cannot be empty", InputValidation.checkUsername(""));
        assertNull(InputValidation.checkUsername("mario"));
    }

    @Test
    public void checkUsername_controllaTuttiGliUtenti() {
        // Prima il ciclo sugli utenti teneva solo l'esito dell'ultimo
        new User("mario", "Password1!", "mario@email.it", "default_profile_pic");

        assertEquals("Username already in use", InputValidation.checkUsername("user"));
        assertEquals("Username already in use", InputValidation.checkUsername("mario"));
    }

    @Test
    public void checkEmail() {
        assertEquals("Email already in use", InputValidation.checkEmail("user@email.it"));
        assertEquals("Email cannot be empty", InputValidation.checkEmail(""));
        assertEquals("Invalid email format", InputValidation.checkEmail("mario"));
        assertEquals("Invalid email format", InputValidation.checkEmail("mario@email"));
        assertNull(InputValidation.checkEmail("mario@email.it"));
    }

    @Test
    public void checkPassword() {
        assertEquals("Password cannot be empty", InputValidation.checkPassword(""));
        assertEquals("Must be at least 8 characters", InputValidation.checkPassword("Ab1!"));
        assertEquals("Must contain 1 number", InputValidation.checkPassword("Abcdefgh!"));
        assertEquals("Must contain 1 lower-case letter", InputValidation.checkPassword("ABCDEFG1!"));
        assertEquals("Must contain 1 upper-case letter", InputValidation.checkPassword("abcdefg1!"));
        assertEquals("Must contain 1 special character", InputValidation.checkPassword("Abcdefg12"));
        assertNull(InputValidation.checkPassword("Abcdefg1!"));
    }
}