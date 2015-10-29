package com.smartlapse.com.smartlapse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    /**
     * Keys to register into the SharedPreferences of the application
     */
    private String PREFS_DURATIONH = "DurationH";
    private String PREFS_DURATIONMIN = "DurationMin";
    private String PREFS_INTERVAL = "Interval";
    private String PREFS_STEPS = "Steps";
    private EditText GetPreferences;
    private EditText SetPreferences;
    private GlobalVariable appState ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appState= ((GlobalVariable)getApplicationContext());

        setSettingsPreferences();
    }

    private void setSettingsPreferences() {
        SetPreferences=(EditText) findViewById(R.id.DurationHEditText);
        SetPreferences.setText(appState.getPref(PREFS_DURATIONH,getApplicationContext()));

        SetPreferences=(EditText) findViewById(R.id.DurationMinEditText);
        SetPreferences.setText(appState.getPref(PREFS_DURATIONMIN, getApplicationContext()));

        SetPreferences=(EditText) findViewById(R.id.IntervalEditText);
        SetPreferences.setText(appState.getPref(PREFS_INTERVAL, getApplicationContext()));

        SetPreferences=(EditText) findViewById(R.id.StepsEditText);
        SetPreferences.setText(appState.getPref(PREFS_STEPS,getApplicationContext()));
    }

    public void onClickSubmitParameters(View view) {

        GetPreferences=(EditText) findViewById(R.id.DurationHEditText);
        appState.putPref(PREFS_DURATIONH, GetPreferences.getText().toString(),getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.DurationMinEditText);
        appState.putPref(PREFS_DURATIONMIN, GetPreferences.getText().toString(),getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.IntervalEditText);
        appState.putPref(PREFS_INTERVAL,GetPreferences.getText().toString(),getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.StepsEditText);
        appState.putPref(PREFS_STEPS, GetPreferences.getText().toString(),getApplicationContext());

        Toast.makeText(getApplicationContext(), "Settings saved",
                Toast.LENGTH_LONG).show();
    }

}
