package com.example.rc.app_sensores;


import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.hardware.Sensor;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor sensorDeTemperatura = null;
    private Sensor sensorDeProximidad = null;
    private Sensor sensorDeLuz = null;
    private Sensor sensorAcelerometro = null;
    private Sensor sensorDeOrientacion = null;

    private TextView textViewAcelerometro = null;
    private TextView textViewOrientacion = null;
    private TextView textViewProcimidad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAcelerometro = (TextView) findViewById(R.id.sensorDeMovimiento);
        textViewAcelerometro.setTextSize(15);

        textViewProcimidad = (TextView) findViewById(R.id.sensorDeOrientacion);
        textViewProcimidad.setTextSize(15);

        textViewOrientacion = (TextView) findViewById(R.id.sensorDeProcimidad);
        textViewOrientacion.setTextSize(15);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorDeProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorDeTemperatura = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        sensorDeLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorDeOrientacion = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    // Metodo para iniciar la escucha de los eventos de los sensores

    public void iniciarSensores() {

        if (sensorAcelerometro == null) {
            Toast.makeText(getApplicationContext(), "No hay Sensor movimiento", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Hay Sensor de movimiento", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorDeProximidad == null) {
            Toast.makeText(getApplicationContext(), "No hay Sensor de Proximidad", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Hay Sensor de Proximidad", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, sensorDeProximidad, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorDeLuz == null) {
            Toast.makeText(getApplicationContext(), "No hay Sensor de Luz", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Hay Sensor de Luz", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, sensorDeLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorDeTemperatura == null) {
            Toast.makeText(getApplicationContext(), "No hay sensor de Temperatura", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Hay sensor de Temperatura", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, sensorDeTemperatura, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorDeOrientacion == null) {
            Toast.makeText(getApplicationContext(), "No hay sensor de Orientacion", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Hay sensor de Orientacion", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, sensorDeOrientacion, SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    // metodo para detener los eventos de escucha de los sensores y evitar que la aplicacion consuma recursos
    public void detenerSensores() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

        if (!listaSensores.isEmpty()) {
            Sensor acelerometerSensor = listaSensores.get(0);
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(acelerometerSensor.TYPE_ACCELEROMETER));
        }


        listaSensores = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        if (!listaSensores.isEmpty()) {
            Sensor proximitySensor = listaSensores.get(0);
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(proximitySensor.TYPE_PROXIMITY));
        }

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
        if (!listaSensores.isEmpty()) {
            Sensor luzSensor = listaSensores.get(0);
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(luzSensor.TYPE_LIGHT));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent arg0) {
        synchronized (this) {
            float[] masData;
            float x;
            float y;
            float z;

            // TODO Auto-generated method stub
            switch (arg0.sensor.getType()) {
                case Sensor.TYPE_PROXIMITY:
                    masData = arg0.values;
                    if (masData[0] == 0) {
                        textViewAcelerometro.setTextSize(textViewAcelerometro.getTextSize() + 10);
                    } else {
                        textViewAcelerometro.setTextSize(textViewAcelerometro.getTextSize() - 10);
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    masData = arg0.values;
                    x = masData[0];
                    y = masData[1];
                    z = masData[2];
                    textViewAcelerometro.setText("x: " + x + "\ny: " + y + "\nz: " + z);
                    break;
                case Sensor.TYPE_ORIENTATION:
                    masData = arg0.values;
                    x = masData[0];
                    y = masData[1];
                    textViewOrientacion.setText("x: " + x + "\ny: " + y);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.iniciar:
                iniciarSensores();
                return true;
            case R.id.detener:
                detenerSensores();
                return true;


        }
        return super.onOptionsItemSelected(item);

    }
}

