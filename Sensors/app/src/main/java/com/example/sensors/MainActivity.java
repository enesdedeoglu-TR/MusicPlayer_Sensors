package com.example.sensors;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor mLight;
    private Sensor mACcelerometer;

    TextView luxView, xView, yView, zView;
    ImageView imageView;
    Button startButton;

    String state1;
    String state2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineSensors();

        luxView = (TextView) findViewById(R.id.luxView);
        xView = (TextView) findViewById(R.id.xView);
        yView = (TextView) findViewById(R.id.yView);
        zView = (TextView) findViewById(R.id.zView);

        imageView = (ImageView) findViewById(R.id.imageView);
        startButton = (Button) findViewById(R.id.startButton);

    }

    public void defineSensors(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mACcelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    public void startSensors(View v){
        if (startButton.getText().toString().equals("Sensörleri Başlat")){
            sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, mACcelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            startButton.setText("Sensörleri Durdur");
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
        }else{
            sensorManager.unregisterListener(this);
            startButton.setText("Sensörleri Başlat");
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_offline));
            luxView.setText("-");
            xView.setText("-");
            yView.setText("-");
            zView.setText("-");
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            luxView.setText(String.valueOf(sensorEvent.values[0]));
            if (sensorEvent.values[0] < 5){
                state1 = "cepte";
            }else{
                state1 = "masada";
            }
        }

        if(sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            float hareket = (float) Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1] + sensorEvent.values[2] * sensorEvent.values[2]);
            if (hareket < 0.5){
                state2 = "haraketsiz";
            }else{
                state2 = "hareketli";
            }
            xView.setText(String.valueOf(sensorEvent.values[0]));
            yView.setText(String.valueOf(sensorEvent.values[1]));
            zView.setText(String.valueOf(sensorEvent.values[2]));
        }

        Intent intent = new Intent();
        intent.setAction("DurumBilgisi");
        intent.putExtra("Durum Bilgisi", state1 + " " + state2);
        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}