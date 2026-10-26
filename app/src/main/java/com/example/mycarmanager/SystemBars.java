package com.example.mycarmanager;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Da Android 15 (API 35) le app vengono disegnate anche sotto status bar e barra di navigazione,
// e i colori android:statusBarColor e android:navigationBarColor del tema vengono ignorati
public class SystemBars {

    // Sposta la pagina tra le barre di sistema e colora le barre con i colori del tema, come prima di Android 15
    // Da chiamare dopo setContentView
    public static void applyInsets(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM)
            return;

        TypedArray themeColors = activity.obtainStyledAttributes(
                new int[] {android.R.attr.statusBarColor, android.R.attr.navigationBarColor});
        final int statusBarColor = themeColors.getColor(0, Color.BLACK);
        final int navigationBarColor = themeColors.getColor(1, Color.BLACK);
        themeColors.recycle();

        View content = activity.findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(content, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsets) {
                Insets bars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                Insets keyboard = windowInsets.getInsets(WindowInsetsCompat.Type.ime());

                // Con la tastiera aperta la pagina si accorcia, come con adjustResize
                view.setPadding(bars.left, bars.top, bars.right, Math.max(bars.bottom, keyboard.bottom));

                // Sfondo colorato solo nelle zone sotto le barre
                LayerDrawable background = new LayerDrawable(new Drawable[] {
                        new ColorDrawable(statusBarColor), new ColorDrawable(navigationBarColor)
                });
                background.setLayerGravity(0, Gravity.TOP | Gravity.FILL_HORIZONTAL);
                background.setLayerHeight(0, bars.top);
                background.setLayerGravity(1, Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
                background.setLayerHeight(1, bars.bottom);
                view.setBackground(background);

                return WindowInsetsCompat.CONSUMED;
            }
        });
    }
}