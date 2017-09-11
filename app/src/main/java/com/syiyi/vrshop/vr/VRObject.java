package com.syiyi.vrshop.vr;

import android.graphics.RectF;

/**
 * vr 物体
 * Created by songlintao on 2017/9/11.
 */

public class VRObject {
    RectF rect = new RectF();

    private String name;

    public VRObject(RectF rect, String name) {
        this.rect = rect;
        this.name = name;
    }

    public boolean isHit(float x, float y) {
        return x > rect.left && x < rect.right && y < rect.top && y > rect.bottom;
    }


    public String getName() {
        return name;
    }
}
