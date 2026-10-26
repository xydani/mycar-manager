package com.example.mycarmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;

public class ColorBlindActivity extends NavigationActivity {
    // Dichiarazione di variabili di istanza
    private Button applyButton;
    private ImageView imageView;
    private LinearLayout noneButton, deuButton, proButton, triButton;
    private TextView filterName;

    int activeButton, activeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_color_blind_v2);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna la pagina in base al tema
        updateExampleImage(selectedTheme);

        // Aggiorna pulsante attivo
        updateCheckedButton(selectedTheme - 1);

        // Inizializza i listener per gli elementi della pagina
        initListeners();
    }

    private void initViews() {
        // Inizializza gli elementi della pagina
        applyButton = findViewById(R.id.applyFilterButton);
        filterName = findViewById(R.id.colourBlindExampleText);
        imageView = findViewById(R.id.colourBlindExampleImage);

        noneButton = findViewById(R.id.noColourBlindButton);
        deuButton = findViewById(R.id.deuteranopiaButton);
        proButton = findViewById(R.id.protanopiaButton);
        triButton = findViewById(R.id.tritanopiaButton);

        activeButton = 0;
        activeMode = selectedTheme - 1;
    }

    private void initListeners() {
        // Listener pulsante "Apply"
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleApplyButtonClick();
            }
        });

        noneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Applicazione filtro all'immagine di esempio
                imageView.setImageResource(R.drawable.car_rainbow_dodge_img);
                filterName.setText("NO FILTER APPLIED");

                // Cambio colore sfondo pulsante
                updateCheckedButton(0);

                activeButton = 0;
            }
        });

        deuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Applicazione filtro all'immagine di esempio
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                imageView.setImageBitmap(filteredBitmap);
                filterName.setText("DEUTERAN FIX FILTER");

                // Cambio colore sfondo pulsante
                updateCheckedButton(1);

                activeButton = 1;
            }
        });

        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Applicazione filtro all'immagine di esempio
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                imageView.setImageBitmap(filteredBitmap);
                filterName.setText("PROTAN FIX FILTER");

                // Cambio colore sfondo pulsante
                updateCheckedButton(2);

                activeButton = 2;
            }
        });

        triButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Applicazione filtro all'immagine di esempio
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                imageView.setImageBitmap(filteredBitmap);
                filterName.setText("TRITAN FIX FILTER");

                // Cambio colore sfondo pulsante
                updateCheckedButton(3);

                activeButton = 3;
            }
        });
    }

    private void handleApplyButtonClick() {
        if (activeButton == 0) {colorFix(1); activeMode = 0;}
        else if (activeButton == 1) {colorFix(2); activeMode = 1;}
        else if (activeButton == 2) {colorFix(3); activeMode = 2;}
        else {colorFix(4); activeMode = 3;}
    }

    private void updateCheckedButton (int activeButton) {
        switch (activeButton) {
            case 0:
                if (activeMode == 0) {noneButton.setBackgroundColor(0xFFA3C0CD);}
                else if (activeMode == 1) {noneButton.setBackgroundColor(0xFFADABC9);}
                else if (activeMode == 2) {noneButton.setBackgroundColor(0xFFAFAFC9);}
                else if (activeMode == 3) {noneButton.setBackgroundColor(0xFFA4C7C6);}
                proButton.setBackgroundColor(0x00000000);
                deuButton.setBackgroundColor(0x00000000);
                triButton.setBackgroundColor(0x00000000);
                break;

            case 1:
                if (activeMode == 0) {deuButton.setBackgroundColor(0xFFA3C0CD);}
                else if (activeMode == 1) {deuButton.setBackgroundColor(0xFFADABC9);}
                else if (activeMode == 2) {deuButton.setBackgroundColor(0xFFAFAFC9);}
                else if (activeMode == 3) {deuButton.setBackgroundColor(0xFFA4C7C6);}
                noneButton.setBackgroundColor(0x00000000);
                proButton.setBackgroundColor(0x00000000);
                triButton.setBackgroundColor(0x00000000);
                break;

            case 2:
                if (activeMode == 0) {proButton.setBackgroundColor(0xFFA3C0CD);}
                else if (activeMode == 1) {proButton.setBackgroundColor(0xFFADABC9);}
                else if (activeMode == 2) {proButton.setBackgroundColor(0xFFAFAFC9);}
                else if (activeMode == 3) {proButton.setBackgroundColor(0xFFA4C7C6);}
                noneButton.setBackgroundColor(0x00000000);
                deuButton.setBackgroundColor(0x00000000);
                triButton.setBackgroundColor(0x00000000);
                break;

            case 3:
                if (activeMode == 0) {triButton.setBackgroundColor(0xFFA3C0CD);}
                else if (activeMode == 1) {triButton.setBackgroundColor(0xFFADABC9);}
                else if (activeMode == 2) {triButton.setBackgroundColor(0xFFAFAFC9);}
                else if (activeMode == 3) {triButton.setBackgroundColor(0xFFA4C7C6);}
                noneButton.setBackgroundColor(0x00000000);
                proButton.setBackgroundColor(0x00000000);
                deuButton.setBackgroundColor(0x00000000);
                break;
        }
    }

    private void updateExampleImage(int selectedTheme) {
        // Aggiorna l'immagine di esempio in base al tema
        switch (selectedTheme) {
            case 1:
                imageView.setImageResource(R.drawable.car_rainbow_dodge_img);
                filterName.setText("NO FILTER APPLIED");
                break;

            case 2:
                Bitmap originalBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap2 = ColorBlindFilter.applyFilter(originalBitmap2, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                imageView.setImageBitmap(filteredBitmap2);
                filterName.setText("DEUTERAN FIX FILTER");
                break;

            case 3:
                Bitmap originalBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap3 = ColorBlindFilter.applyFilter(originalBitmap3, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                imageView.setImageBitmap(filteredBitmap3);
                filterName.setText("PROTAN FIX FILTER");
                break;

            case 4:
                Bitmap originalBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.car_rainbow_dodge_img);
                Bitmap filteredBitmap4 = ColorBlindFilter.applyFilter(originalBitmap4, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                imageView.setImageBitmap(filteredBitmap4);
                filterName.setText("TRITAN FIX FILTER");
                break;
        }
    }

    private void colorFix (int mode) {
        Editor editor = sharedPreferences.edit();
        editor.putInt("SelectedTheme", mode);
        editor.apply();

        startActivity(new Intent(ColorBlindActivity.this, ColorBlindActivity.class));
        finish();
    }
}