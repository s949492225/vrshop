package com.syiyi.vrshop;

import android.graphics.RectF;
import com.syiyi.vrshop.vr.Scene;
import com.syiyi.vrshop.vr.VRPath;


/**
 * 数据中心
 * Created by songlintao on 2017/9/11.
 */

public class DataManager {
    private Scene currentScene;

    public DataManager() {
        currentScene = new Scene("index.jpg");
        Scene scene2 = new Scene("index2.jpg");
        currentScene.addVRPath(new VRPath(new RectF(90f, 0f, 131f, 0f), scene2));
    }

    public void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
