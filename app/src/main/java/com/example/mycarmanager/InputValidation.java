package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;

import android.text.Editable;
import android.text.TextWatcher;

// Controlli sui dati inseriti nella registrazione e nella modifica dell'account
// I metodi check* restituiscono il messaggio di errore da mostrare, oppure null se il valore è valido
public class InputValidation {

    public static String checkUsername(String username) {
        if (isUsernameInUse(username))
            return "Username already in use";
        if (username.isEmpty())
            return "Username cannot be empty";
        return null;
    }

    public static String checkEmail(String email) {
        if (isEmailInUse(email))
            return "Email already in use";
        if (email.isEmpty())
            return "Email cannot be empty";
        if (!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
            return "Invalid email format";
        return null;
    }

    public static String checkPassword(String password) {
        if (password.isEmpty())
            return "Password cannot be empty";
        if (password.length() < 8)
            return "Must be at least 8 characters";
        if (!password.matches(".*[0-9].*"))
            return "Must contain 1 number";
        if (!password.matches(".*[a-z].*"))
            return "Must contain 1 lower-case letter";
        if (!password.matches(".*[A-Z].*"))
            return "Must contain 1 upper-case letter";
        if (password.matches("[a-zA-Z0-9]*"))
            return "Must contain 1 special character";
        return null;
    }

    // Ritorna true se lo username è già usato da un utente registrato
    public static boolean isUsernameInUse(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    // Ritorna true se l'email è già usata da un utente registrato
    public static boolean isEmailInUse(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    // TextWatcher che notifica solo la modifica del testo
    public abstract static class InputWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            onInputChanged();
        }

        @Override
        public void afterTextChanged(Editable editable) {}

        public abstract void onInputChanged();
    }
}