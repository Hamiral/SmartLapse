package com.smartlapse.com.smartlapse;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public final static String DEBUG_TAG = "MakePhotoActivity";
    private Camera camera;
    private int cameraId = 0;
    public CameraPreview mPreview;
    private GlobalVariable appState ;
    private String PREFS_DURATIONH = "DurationH";
    private String PREFS_DURATIONMIN = "DurationMin";
    private String PREFS_INTERVAL = "Interval";
    private String PREFS_STEPS = "Steps";
    private static int count = 0;
    /**
     * Timer to regularly check the status of the connection with the login API
     */
    Timer myTimer;
    final Handler myHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appState= ((GlobalVariable)getApplicationContext());

        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            cameraId = findBackFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(this, "No front facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                camera = Camera.open(cameraId);
            }
        }
        // Create an instance of Camera


        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        /*
        Log.d("BLE", "starting Process Creation");
        onCreateProcess();														//onCreate Process by BlunoLibrary
        Log.d("BLE", "finished Process Creation");
        serialBegin(115200);
        */
    }

    public void onClickRecord(View view) {
        final int DurationH=appState.getPref(PREFS_DURATIONH, getApplicationContext());
        final int DurationMin=appState.getPref(PREFS_DURATIONMIN, getApplicationContext());
        final int Intervals=appState.getPref(PREFS_INTERVAL, getApplicationContext());

        //serialSend(Integer.toString(Intervals));


        count=0;
       myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                camera.takePicture(null, null, null,
                        new PhotoHandler(getApplicationContext()));

                count++;
                if (((DurationH * 60 + DurationMin) * 60 / Intervals) <= count) {
                    myTimer.cancel();
                    myTimer.purge();
                }
            }
        }, 0, Intervals * 1000);
    }


    public void onClickSettings(View view) {
        Intent intent= new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();
        }
        super.onPause();
        //onPauseProcess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.startPreview();
        //onResumeProcess();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (camera != null) {
            camera.release();
            camera = null;
        }
        //onDestroyProcess();
    }

    /*@Override
    public boolean onKeyUp(int keyCode,KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_HOME:
                break;
        }
        return super.onKeyDown(keyCode,event);
    }*/

    /*
    public void onClickScan(View view) {
        buttonScanOnClickProcess();
    }

    @Override
    public void onConectionStateChange(BlunoLibrary.connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
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

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }
    */
}
