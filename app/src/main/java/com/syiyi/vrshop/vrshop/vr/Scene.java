package com.syiyi.vrshop.vrshop.vr;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景
 * Created by songlintao on 2017/9/11.
 */

public class Scene {
    private List<VRObject> objects = new ArrayList<>();
    private List<VRPath> paths = new ArrayList<>();
    private String scenePic;


    public Scene(String scenePic) {
        this.scenePic = scenePic;
    }

    public void addVRObject(VRObject vrObject) {
        objects.add(vrObject);
    }

    public void addVRPath(VRPath vrPath) {
        paths.add(vrPath);
    }

    public String getScenePic() {
        return scenePic;
    }

    public VRObject isHitVRObject(float x, float y) {
        for (VRObject vrObject : objects) {
            if (vrObject.isHit(x, y)) {
                return vrObject;
            }
        }
        return null;
    }

    public VRPath isHitVRPath(float x, float y) {
        for (VRPath vrPath : paths) {
            if (vrPath.isHit(x, y)) {
                return vrPath;
            }
        }
        return null;
    }
}
