package com.syiyi.vrshop;

import android.graphics.RectF;

import com.syiyi.vrshop.vr.Scene;
import com.syiyi.vrshop.vr.VRObject;
import com.syiyi.vrshop.vr.VRPath;


/**
 * 数据中心
 * Created by songlintao on 2017/9/11.
 */

public class DataManager {
    private Scene currentScene;

    public DataManager() {
        currentScene = new Scene("index.jpg");
        currentScene.addVRObject(new VRObject(new RectF(31.95f, -26.25f, 41.46f, -32.54f), "漂亮的水杯"));

        Scene scene2 = new Scene("index2.jpg");
        scene2.addVRObject(new VRObject(new RectF(7.35f, -26.25f, 18f, -32.54f), "移动硬盘"));
        scene2.addVRObject(new VRObject(new RectF(20.6f, -15.7f, 43.34f, -27.5f), "好美的花"));


        scene2.addVRPath(new VRPath(new RectF(-98f, 0f, -29f, 0f), currentScene));
        currentScene.addVRPath(new VRPath(new RectF(54f, 0f, 114f, 0f), scene2));

    }

    public void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
