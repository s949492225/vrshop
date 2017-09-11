package com.syiyi.vrshop;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import static android.hardware.SensorManager.SENSOR_DELAY_FASTEST;

/**
 * Created by songlintao on 2017/9/8.
 */

@SuppressWarnings("all")
public class RoateManager {

    private Activity activity;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    private onHeadRotationChangeListener rotationChangeListener;
    private VrPanoramaView panoWidgetView;

    public RoateManager(Activity activity, VrPanoramaView panoWidgetView, onHeadRotationChangeListener rotationChangeListener) {
        this.activity = activity;
        this.panoWidgetView = panoWidgetView;
        this.rotationChangeListener = rotationChangeListener;
        this.listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotation = new float[2];
                RoateManager.this.panoWidgetView.getHeadRotation(rotation);
                RoateManager.this.rotationChangeListener.onHeadRotation(rotation[0], rotation[1]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void start() {
        if (this.sensorManager == null) {
            this.sensorManager = (SensorManager) this.activity.getSystemService("sensor");
        }

        if (this.sensor == null) {
            this.sensor = this.sensorManager.getDefaultSensor(1);
        }
        this.sensorManager.unregisterListener(listener);
        this.sensorManager.registerListener(listener, this.sensor, SENSOR_DELAY_FASTEST);
    }

    public void stop() {
        if (this.sensorManager != null) {
            this.sensorManager.unregisterListener(listener);
        }
    }

    public interface onHeadRotationChangeListener {
        void onHeadRotation(float x, float y);
    }

}
