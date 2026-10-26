package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;

import android.text.Editable;
import android.text.TextWatcher;

// Controlli sui dati inseriti nella registrazione e nella modifica dell'account
// I metodi check* restituiscono l'id della stringa con il messaggio di errore da mostrare,
// oppure VALID se il valore è valido
public class InputValidation {

    public static final int VALID = 0;

    public static int checkUsername(String username) {
        if (isUsernameInUse(username))
            return R.string.error_username_in_use;
        if (username.isEmpty())
            return R.string.error_username_empty;
        return VALID;
    }

    public static int checkEmail(String email) {
        if (isEmailInUse(email))
            return R.string.error_email_in_use;
        if (email.isEmpty())
            return R.string.error_email_empty;
        if (!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
            return R.string.error_email_format;
        return VALID;
    }

    public static int checkPassword(String password) {
        if (password.isEmpty())
            return R.string.error_password_empty;
        if (password.length() < 8)
            return R.string.error_password_length;
        if (!password.matches(".*[0-9].*"))
            return R.string.error_password_number;
        if (!password.matches(".*[a-z].*"))
            return R.string.error_password_lower_case;
        if (!password.matches(".*[A-Z].*"))
            return R.string.error_password_upper_case;
        if (password.matches("[a-zA-Z0-9]*"))
            return R.string.error_password_special_character;
        return VALID;
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