package com.syiyi.vrshop.vrshop.vr;

import android.graphics.RectF;

/**
 * 路径
 * Created by songlintao on 2017/9/11.
 */

public class VRPath {
    private RectF path;
    private Scene nextScene;

    public VRPath(RectF path, Scene nextScene) {
        this.path = path;
        this.nextScene = nextScene;
    }

    public boolean isHit(float x, float y) {
        return x > path.left && x < path.right;
    }

    public Scene getNextScene() {
        return nextScene;
    }
}
