package com.example.mycarmanager;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class CarManageActivity extends NavigationActivity {
    // Numero di funzioni gestibili: power, finestrini, clima, chiusura, radio, fari
    private static final int NUMBER_OF_SETTINGS = 6;

    private ImageView carBrandImage, carFuelImage, carImage, carTypeImage, manageImage,
            manageLeftArrow, manageRightArrow;
    private MaterialButton functionButton;
    private LinearLayout carFunctionContainer;
    private TextView carFunctionText;
    private int currentSettingIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_manage);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna informazioni auto corrente e impostazioni
        showCurrentCar();

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews() {
        carBrandImage = findViewById(R.id.carBrandImage);
        carFuelImage = findViewById(R.id.carFuelImage);
        carFunctionContainer = findViewById(R.id.carFunctionContainer);
        carFunctionText = findViewById(R.id.carFunctionText);
        carImage = findViewById(R.id.carImage);
        carTypeImage = findViewById(R.id.carTypeImage);
        functionButton = findViewById(R.id.functionButton);
        manageImage = findViewById(R.id.manageImage);
        manageLeftArrow = findViewById(R.id.manageLeftArrow);
        manageRightArrow = findViewById(R.id.manageRightArrow);

        currentSettingIndex = 0;
    }

    public void initListeners() {
        // Listener pulsante settings
        functionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(currentSettingIndex) {
                    // CASO POWER ON
                    case 0:
                        currentCar.setPowerOn(!currentCar.getPowerOn());
                        break;

                    // CASO CAR WINDOWS
                    case 1:
                        showWindowsDialog();
                        break;

                    //CASO AIR CONDITIONING
                    case 2:
                        showAirConditioningDialog();
                        break;

                    // CASO LOCK
                    case 3:
                        currentCar.setCarUnlocked(!currentCar.getCarUnlocked());
                        break;

                    // CASO RADIO
                    case 4:
                        showRadioDialog();
                        break;

                    // CASO HEADLIGHTS
                    case 5:
                        currentCar.setAreLightsOn(!currentCar.getAreLightsOn());
                        break;
                }

                updateCarSettings();
            }
        });

        // Listener freccia selezione impostazione [dx]
        manageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSetting();
            }
        });

        // Listener freccia selezione impostazione [sx]
        manageLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevSetting();
            }
        });

        // Listener scorrimento dito selezione impostazione
        carFunctionContainer.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2;
            static final int MIN_DISTANCE = 1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            if (x2 > x1) { prevSetting(); }
                            else { nextSetting(); }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void nextSetting() {
        // Dopo l'ultima impostazione si torna alla prima
        currentSettingIndex = (currentSettingIndex + 1) % NUMBER_OF_SETTINGS;
        updateCarSettings();
    }

    private void prevSetting() {
        // Prima della prima impostazione si passa all'ultima
        currentSettingIndex = (currentSettingIndex - 1 + NUMBER_OF_SETTINGS) % NUMBER_OF_SETTINGS;
        updateCarSettings();
    }

    @Override
    protected void onCarChanged() {
        // Immagine dell'auto, con il filtro del tema scelto
        setFilteredImage(carImage, currentCar.getCarImageResource());

        // Marca, carburante e tipo di auto
        carBrandImage.setImageResource(currentCar.getBrandLogoResource());
        carFuelImage.setImageResource(currentCar.getFuelImageResource());
        carTypeImage.setImageResource(currentCar.getCarTypeImageResource());

        // Aggiorna informazioni sulle impostazioni
        updateCarSettings();
    }

    private void updateCarSettings() {
        switch (currentSettingIndex) {
            // CASO POWER ON/OFF
            case 0:
                if (currentCar.getPowerOn())
                    showSetting(R.drawable.manage_on, "POWER ON", "ON", R.attr.okBtnBackground);
                else
                    showSetting(R.drawable.manage_on, "POWER ON", "OFF", R.attr.notOkBtnBackground);
                break;

            // CASO CAR WINDOWS
            case 1:
                showSetting(R.drawable.manage_carwindow, "CAR WINDOWS", "EDIT", R.attr.editBtnBackground);
                break;

            // CASO AIR CONDITIONING
            case 2:
                showSetting(R.drawable.manage_ac, "AIR CONDITIONING", "EDIT", R.attr.editBtnBackground);
                break;

            // CASO LOCK
            case 3:
                if (currentCar.getCarUnlocked())
                    showSetting(R.drawable.manage_lock, "LOCK", "OPEN", R.attr.okBtnBackground);
                else
                    showSetting(R.drawable.manage_lock, "LOCK", "LOCKED", R.attr.notOkBtnBackground);
                break;

            // CASO RADIO
            case 4:
                showSetting(R.drawable.manage_radio, "RADIO", "EDIT", R.attr.editBtnBackground);
                break;

            // CASO HEADLIGHTS
            case 5:
                if (currentCar.getAreLightsOn())
                    showSetting(R.drawable.manage_headlights, "HEADLIGHTS", "ON", R.attr.okBtnBackground);
                else
                    showSetting(R.drawable.manage_headlights, "HEADLIGHTS", "OFF", R.attr.notOkBtnBackground);
                break;

            default: break;
        }
    }

    // Visualizza immagine, nome e pulsante con l'impostazione corrente
    private void showSetting(int imageId, String functionName, String buttonText, int buttonColorAttr) {
        manageImage.setImageResource(imageId);
        carFunctionText.setText(functionName);
        functionButton.setText(buttonText);
        ViewCompat.setBackgroundTintList(functionButton, getButtonColor(buttonColorAttr));
    }

    private ColorStateList getButtonColor (int color) {
        // Ottieni il colore dall'attributo customBackgroundTint
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(color, typedValue, true);
        int customBackgroundTint = typedValue.data;

        // Crea una ColorStateList con il colore ottenuto dall'attributo
        return ColorStateList.valueOf(customBackgroundTint);
    }

    public void showWindowsDialog(){
        Dialog windowsDialog = createDialog(R.layout.car_windows_dialog, R.id.closeWindowsDialog);

        Slider windowsLevelSlider = windowsDialog.findViewById(R.id.windowsLevelSlider);
        final TextView windowsLevelText = windowsDialog.findViewById(R.id.windowsLevelText);

        // Listener slider e modifica testo [WINDOWS]
        windowsLevelSlider.setCustomThumbDrawable(R.drawable.black_rectangle);
        windowsLevelSlider.setValue(currentCar.getWindowsLevel());
        windowsLevelText.setText(currentCar.getWindowsLevel() + "%");
        windowsLevelSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                windowsLevelText.setText((int)value + "%");
                currentCar.setWindowsLevel((int)value);
            }
        });

        windowsDialog.show();
    }

    public void showAirConditioningDialog(){
        Dialog airDialog = createDialog(R.layout.car_air_dialog, R.id.closeAirDialog);

        Slider temperatureSlider = airDialog.findViewById(R.id.temperatureSlider);
        final TextView temperatureText = airDialog.findViewById(R.id.temperatureText);
        Slider powerSlider = airDialog.findViewById(R.id.powerSlider);
        final TextView powerText = airDialog.findViewById(R.id.powerText);
        SwitchCompat airSwitch = airDialog.findViewById(R.id.airSwitch);
        final AirConditioning air = currentCar.getAirConditioning();

        // Listener slider e modifica testo [AIR - TEMPERATURE]
        temperatureSlider.setCustomThumbDrawable(R.drawable.black_rectangle);
        temperatureSlider.setValue(air.getTemperature());
        temperatureText.setText(String.valueOf(air.getTemperature()));
        temperatureSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                temperatureText.setText(String.valueOf(value));
                air.setTemperature(value);
            }
        });

        // Listener SwitchCompat accensione e spegnimento clima
        airSwitch.setChecked(air.getIsAirConditioningOn());
        airSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                air.setIsAirConditioningOn(isChecked);
            }
        });

        // Listener slider e modifica testo [AIR - POWER]
        powerSlider.setCustomThumbDrawable(R.drawable.black_rectangle);
        powerSlider.setValue(air.getPowerLevel());
        powerText.setText(String.valueOf(air.getPowerLevel()));
        powerSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                powerText.setText(String.valueOf((int)value));
                air.setPowerLevel((int)value);
            }
        });

        airDialog.show();
    }

    public void showRadioDialog(){
        Dialog radioDialog = createDialog(R.layout.car_radio_dialog, R.id.closeRadioDialog);

        Slider volumeSlider = radioDialog.findViewById(R.id.radioVolumeSlider);
        final TextView volumeText = radioDialog.findViewById(R.id.radioVolumeText);
        Slider radioStationSlider = radioDialog.findViewById(R.id.radioStationSlider);
        final TextView radioStationText = radioDialog.findViewById(R.id.radioStationText);
        SwitchCompat radioSwitch = radioDialog.findViewById(R.id.radioSwitch);
        final Radio radio = currentCar.getRadio();

        // Listener slider e modifica testo [RADIO - VOLUME]
        volumeSlider.setCustomThumbDrawable(R.drawable.black_rectangle);
        volumeSlider.setValue(radio.getVolume());
        volumeText.setText(String.valueOf(radio.getVolume()));
        volumeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                volumeText.setText(String.valueOf((int)value));
                radio.setVolume((int)value);
            }
        });

        // Listener SwitchCompat accensione e spegnimento radio
        radioSwitch.setChecked(radio.getIsRadioOn());
        radioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radio.setIsRadioOn(isChecked);
            }
        });

        // Listener slider e modifica testo [RADIO - STAZIONE]
        radioStationSlider.setCustomThumbDrawable(R.drawable.black_rectangle);
        radioStationSlider.setValue(radio.getRadioStation());
        radioStationText.setText(String.valueOf(radio.getRadioStation()));
        radioStationSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                radioStationText.setText(String.valueOf(value));
                radio.setRadioStation(value);
            }
        });

        radioDialog.show();
    }

    // Crea un dialog delle impostazioni, con animazione e pulsante di chiusura
    private Dialog createDialog(int layoutId, int closeButtonId) {
        final Dialog dialog = new Dialog(CarManageActivity.this);
        dialog.setContentView(layoutId);
        dialog.getWindow().getAttributes().windowAnimations = R.style.buttonDialog;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().gravity = Gravity.TOP;

        ImageButton closeButton = dialog.findViewById(closeButtonId);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}