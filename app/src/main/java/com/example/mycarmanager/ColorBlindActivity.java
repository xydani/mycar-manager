package com.example.mycarmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;

public class ColorBlindActivity extends NavigationActivity {
    // Testo mostrato per ogni filtro, nell'ordine dei temi [NOR-DEU-PRO-TRI]
    private static final String[] FILTER_NAMES = {
            "NO FILTER APPLIED", "DEUTERAN FIX FILTER", "PROTAN FIX FILTER", "TRITAN FIX FILTER"
    };

    // Sfondo del pulsante selezionato, in base al tema attivo [NOR-DEU-PRO-TRI]
    private static final int[] CHECKED_BUTTON_COLORS = {
            0xFFA3C0CD, 0xFFADABC9, 0xFFAFAFC9, 0xFFA4C7C6
    };

    // Dichiarazione di variabili di istanza
    private Button applyButton;
    private ImageView imageView;
    private LinearLayout[] filterButtons;
    private TextView filterName;

    // Tema corrispondente al pulsante selezionato
    private int checkedTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_color_blind_v2);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // All'apertura è selezionato il tema attivo
        selectFilter(selectedTheme);

        // Inizializza i listener per gli elementi della pagina
        initListeners();
    }

    private void initViews() {
        // Inizializza gli elementi della pagina
        applyButton = findViewById(R.id.applyFilterButton);
        filterName = findViewById(R.id.colourBlindExampleText);
        imageView = findViewById(R.id.colourBlindExampleImage);

        // Pulsanti nell'ordine dei temi [NOR-DEU-PRO-TRI]
        filterButtons = new LinearLayout[] {
                findViewById(R.id.noColourBlindButton),
                findViewById(R.id.deuteranopiaButton),
                findViewById(R.id.protanopiaButton),
                findViewById(R.id.tritanopiaButton)
        };
    }

    private void initListeners() {
        // Listener pulsante "Apply"
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorFix(checkedTheme);
            }
        });

        // Listener pulsanti dei filtri
        for (int i = 0; i < filterButtons.length; i++) {
            final int theme = i + 1;

            filterButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectFilter(theme);
                }
            });
        }
    }

    // Mostra l'immagine di esempio con il filtro del tema e seleziona il relativo pulsante
    private void selectFilter(int theme) {
        setFilteredImage(imageView, R.drawable.car_rainbow_dodge_img, theme);
        filterName.setText(FILTER_NAMES[theme - 1]);

        // Cambio colore sfondo pulsante
        for (int i = 0; i < filterButtons.length; i++) {
            if (i == theme - 1)
                filterButtons[i].setBackgroundColor(CHECKED_BUTTON_COLORS[selectedTheme - 1]);
            else
                filterButtons[i].setBackgroundColor(0x00000000);
        }

        checkedTheme = theme;
    }

    // Salva il tema scelto e ricarica la pagina per applicarlo
    private void colorFix (int theme) {
        Editor editor = sharedPreferences.edit();
        editor.putInt("SelectedTheme", theme);
        editor.apply();

        startActivity(new Intent(ColorBlindActivity.this, ColorBlindActivity.class));
        finish();
    }
}