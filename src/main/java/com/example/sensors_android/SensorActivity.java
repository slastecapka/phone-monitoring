package com.example.sensors_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SensorActivity extends AppCompatActivity {


    // объекты для работы с датчиками
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    ListView sensorValueList; //список для отображения данных датчика
    TextView sensorName; // поле отображения названия (type) датчика

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        getSupportActionBar().hide();

        //привязка с реальными View элементами активити
        sensorValueList = findViewById(R.id.sensorValueList);
        sensorName = findViewById(R.id.sensorName);

       //инициализируем менеджера датчиков
       sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

       int type = Integer.parseInt(getIntent().getStringExtra("typeSensor")); // получаем тип нужного датчика


       if (sensorManager != null)
           sensor = sensorManager.getDefaultSensor(type);  // если все хорошо, то инициализируем сенсор

       sensorName.setText("датчик: " + sensor.getName());  // отбражаем его название (type) в поле TextView

       // создаем адаптер и привязываем наш listVew к нему
       List<String> list = new ArrayList<>();
       ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
       sensorValueList.setAdapter(adapter);

       //"слушатель" сенсора
       sensorEventListener = new SensorEventListener() {

           //метод обработки при получении данных с датчика
           @Override
           public void onSensorChanged(SensorEvent sensorEvent) {
               list.clear();
               float[] nums = sensorEvent.values; // получаем данные от датчика в виде массива float[]
               String[] a=Arrays.toString(nums).split("[\\[\\]]")[1].split(", "); // преобразуем массив чисел в массив строк
               list.addAll(Arrays.asList(a)); // создаем список для адаптера из массива
               adapter.notifyDataSetChanged(); // обновляем адаптер, а значит и список
           }

           @Override
           public void onAccuracyChanged(Sensor sensor, int i) {

           }
       };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor,SensorManager.SENSOR_DELAY_FASTEST);  //регистрируем "слушателя датчика" каждый раз при возобновлении окна
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener); //убираем регистрацию счетчика
    }
}