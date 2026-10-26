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
        assertEquals(R.string.error_username_in_use, InputValidation.checkUsername("user"));
        assertEquals(R.string.error_username_empty, InputValidation.checkUsername(""));
        assertEquals(InputValidation.VALID, InputValidation.checkUsername("mario"));
    }

    @Test
    public void checkUsername_controllaTuttiGliUtenti() {
        // Prima il ciclo sugli utenti teneva solo l'esito dell'ultimo
        new User("mario", "Password1!", "mario@email.it", "default_profile_pic");

        assertEquals(R.string.error_username_in_use, InputValidation.checkUsername("user"));
        assertEquals(R.string.error_username_in_use, InputValidation.checkUsername("mario"));
    }

    @Test
    public void checkEmail() {
        assertEquals(R.string.error_email_in_use, InputValidation.checkEmail("user@email.it"));
        assertEquals(R.string.error_email_empty, InputValidation.checkEmail(""));
        assertEquals(R.string.error_email_format, InputValidation.checkEmail("mario"));
        assertEquals(R.string.error_email_format, InputValidation.checkEmail("mario@email"));
        assertEquals(InputValidation.VALID, InputValidation.checkEmail("mario@email.it"));
    }

    @Test
    public void checkPassword() {
        assertEquals(R.string.error_password_empty, InputValidation.checkPassword(""));
        assertEquals(R.string.error_password_length, InputValidation.checkPassword("Ab1!"));
        assertEquals(R.string.error_password_number, InputValidation.checkPassword("Abcdefgh!"));
        assertEquals(R.string.error_password_lower_case, InputValidation.checkPassword("ABCDEFG1!"));
        assertEquals(R.string.error_password_upper_case, InputValidation.checkPassword("abcdefg1!"));
        assertEquals(R.string.error_password_special_character, InputValidation.checkPassword("Abcdefg12"));
        assertEquals(InputValidation.VALID, InputValidation.checkPassword("Abcdefg1!"));
    }
}