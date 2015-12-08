package com.smartlapse.com.smartlapse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends BlunoLibrary  {
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

        Log.d("BLE", "starting Process Creation");
        onCreateProcess();														//onCreate Process by BlunoLibrary
        Log.d("BLE", "finished Process Creation");
        serialBegin(115200);
    }

    private void setSettingsPreferences() {
        SetPreferences=(EditText) findViewById(R.id.DurationHEditText);
        SetPreferences.setText(Integer.toString(appState.getPref(PREFS_DURATIONH, getApplicationContext())));

        SetPreferences = (EditText) findViewById(R.id.DurationMinEditText);
        SetPreferences.setText(Integer.toString(appState.getPref(PREFS_DURATIONMIN, getApplicationContext())));

        SetPreferences = (EditText) findViewById(R.id.IntervalEditText);
        SetPreferences.setText(Integer.toString(appState.getPref(PREFS_INTERVAL, getApplicationContext())));

        SetPreferences = (EditText) findViewById(R.id.StepsEditText);
        SetPreferences.setText(Integer.toString(appState.getPref(PREFS_STEPS, getApplicationContext())));
    }

    public void onClickSubmitParameters(View view) {

        GetPreferences=(EditText) findViewById(R.id.DurationHEditText);
        appState.putPref(PREFS_DURATIONH, Integer.parseInt(GetPreferences.getText().toString()),getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.DurationMinEditText);
        appState.putPref(PREFS_DURATIONMIN, Integer.parseInt(GetPreferences.getText().toString()),getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.IntervalEditText);
        appState.putPref(PREFS_INTERVAL, Integer.parseInt(GetPreferences.getText().toString()), getApplicationContext());

        GetPreferences=(EditText) findViewById(R.id.StepsEditText);
        appState.putPref(PREFS_STEPS, Integer.parseInt(GetPreferences.getText().toString()),getApplicationContext());

        Toast.makeText(getApplicationContext(), "Settings saved",
                Toast.LENGTH_LONG).show();
    }


    public void onClickScan(View view) {
        buttonScanOnClickProcess();
    }

    public void onClickSend(View view) {
        final int Steps=appState.getPref(PREFS_STEPS, getApplicationContext());
        serialSend(Integer.toString(Steps));
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        String state = "Nope";
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                state = "Connected";
                Toast.makeText(this, "State : " + state, Toast.LENGTH_SHORT)
                        .show();
                break;
            case isConnecting:
                state = "Connecting";
                break;
            case isToScan:
                state = "is To Scan";
                break;
            case isScanning:
                state = "Scanning";
                break;
            case isDisconnecting:
                state = "isDisconnecting";
                break;
            default:
                break;
        }
        Log.d("Tag Name", "State : " + state);
    }

    @Override
    public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
        // TODO Auto-generated method stub
        // serialReceivedText.append(theString);							//append the text into the EditText
        Toast.makeText(this, theString, Toast.LENGTH_SHORT)
                .show();
        Log.v("BLE", "Received : " + theString);
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }
}
