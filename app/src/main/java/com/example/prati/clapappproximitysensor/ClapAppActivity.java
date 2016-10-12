package com.example.prati.clapappproximitysensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClapAppActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sM;
    private MediaPlayer mP;
    private final static int MAX_VOLUME = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clap_activity);
        setupSensor();
    }

    private void setupSensor() {
        sM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sM.registerListener(this, sM.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        mP = MediaPlayer.create(this, R.raw.music); 
        if (sM.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            Toast.makeText(getApplicationContext(), "Senor ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Sensor OFF", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onPause()    {
        super.onPause();
        sM.unregisterListener(this);
    }
	
	@Override
    protected void onResume()   {
        super.onResume();
        sM.registerListener(this, sM.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }
	
    public void closeApp(View v) {
        ClapAppActivity.this.finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            mP.start();
            Toast.makeText(getApplicationContext(), "Mobile is closer, Playing Music", Toast.LENGTH_SHORT).show();
        } else {
            mP.start();
            mP.pause();
            Toast.makeText(getApplicationContext(), "Mobile is far, Music Paused", Toast.LENGTH_SHORT).show();
        }
    }

}
