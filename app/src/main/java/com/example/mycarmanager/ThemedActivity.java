package com.example.mycarmanager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

// Activity base che applica il tema per daltonici scelto dall'utente
public class ThemedActivity extends AppCompatActivity {

    // Valori salvati in "SelectedTheme"
    public static final int BASE_THEME = 1;
    public static final int DEUTERAN_THEME = 2;
    public static final int PROTAN_THEME = 3;
    public static final int TRITAN_THEME = 4;

    protected SharedPreferences sharedPreferences;
    protected int selectedTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il tema in base alle preferenze
        // (stesso file usato da PreferenceManager.getDefaultSharedPreferences)
        sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", BASE_THEME);
        setTheme(getThemeStyle(selectedTheme));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Se il tema è stato cambiato in un'altra pagina, la pagina viene ricreata con il nuovo tema
        if (sharedPreferences.getInt("SelectedTheme", BASE_THEME) != selectedTheme)
            recreate();
    }

    public static int getThemeStyle(int theme) {
        switch (theme) {
            case DEUTERAN_THEME: return R.style.DEUTERAN_THEME;
            case PROTAN_THEME: return R.style.PROTAN_THEME;
            case TRITAN_THEME: return R.style.TRITAN_THEME;
            default: return R.style.BASE_THEME;
        }
    }

    // Tipo di daltonismo corrispondente al tema, null per il tema base
    public static ColorBlindFilter.ColorBlindType getColorBlindType(int theme) {
        switch (theme) {
            case DEUTERAN_THEME: return ColorBlindFilter.ColorBlindType.DEUTERANOPIA;
            case PROTAN_THEME: return ColorBlindFilter.ColorBlindType.PROTANOPIA;
            case TRITAN_THEME: return ColorBlindFilter.ColorBlindType.TRITANOPIA;
            default: return null;
        }
    }

    // Mostra l'immagine applicando il filtro del tema scelto
    protected void setFilteredImage(ImageView imageView, int drawableId) {
        setFilteredImage(imageView, drawableId, selectedTheme);
    }

    // Mostra l'immagine applicando il filtro del tema indicato
    protected void setFilteredImage(ImageView imageView, int drawableId, int theme) {
        ColorBlindFilter.ColorBlindType type = getColorBlindType(theme);

        if (type == null) {
            imageView.setImageResource(drawableId);
        } else {
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
            imageView.setImageBitmap(ColorBlindFilter.applyFilter(originalBitmap, type));
        }
    }
}
