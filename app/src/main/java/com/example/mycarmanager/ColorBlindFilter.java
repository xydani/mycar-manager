package com.example.mycarmanager;
import android.graphics.Bitmap;
import android.graphics.*;

public class ColorBlindFilter {

    // Enumerazione per i tipi di daltonismo
    public enum ColorBlindType {DEUTERANOPIA, PROTANOPIA, TRITANOPIA};

    // Le matrici sono 4x5 come richiesto da ColorMatrix: righe R, G, B, A

    // Matrice per la correzione della deuteranopia
    public static final float[] MATRIX_DEUTERANOPIA = {
            0.625f, 0.375f, 0, 0, 0,
            0.7f, 0.3f, 0, 0, 0,
            0, 0.3f, 0.7f, 0, 0,
            0, 0, 0, 1, 0
    };

    // Matrice per la correzione della protanopia
    public static final float[] MATRIX_PROTANOPIA = {
            0.567f, 0.433f, 0, 0, 0,
            0.558f, 0.442f, 0, 0, 0,
            0, 0.242f, 0.758f, 0, 0,
            0, 0, 0, 1, 0
    };

    // Matrice per la correzione della tritanopia
    public static final float[] MATRIX_TRITANOPIA = {
            0.95f, 0.05f, 0, 0, 0,
            0, 0.433f, 0.567f, 0, 0,
            0, 0.475f, 0.525f, 0, 0,
            0, 0, 0, 1, 0
    };

    public static Bitmap applyFilter(Bitmap source, ColorBlindType type) {
        float[] matrix;

        switch (type) {
            case DEUTERANOPIA:
                matrix = MATRIX_DEUTERANOPIA;
                break;
            case PROTANOPIA:
                matrix = MATRIX_PROTANOPIA;
                break;
            case TRITANOPIA:
                matrix = MATRIX_TRITANOPIA;
                break;
            default:
                // Nessuna correzione applicata
                return source;
        }

        Bitmap filteredBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(matrix)));

        Canvas canvas = new Canvas(filteredBitmap);
        canvas.drawBitmap(source, 0, 0, paint);

        return filteredBitmap;
    }
}