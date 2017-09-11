package com.syiyi.vrshop.vrshop;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.syiyi.vrshop.vrshop.vr.Scene;
import com.syiyi.vrshop.vrshop.vr.VRObject;
import com.syiyi.vrshop.vrshop.vr.VRPath;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private VrPanoramaView mPanoramaView;
    private RoateManager mRoateManager;
    private ImageLoaderTask mImageLoaderTask;
    private VrPanoramaView.Options mPanoOptions = new VrPanoramaView.Options();
    private float mRateX;
    private float mRateY;
    private VRPath mCurrentPath;
    private ImageView mGoNext;
    private DataManager mDataManager = new DataManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mGoNext = findViewById(R.id.go);
        mPanoramaView = findViewById(R.id.pano_view);
        mPanoramaView.setFullscreenButtonEnabled(false);
        mPanoramaView.setInfoButtonEnabled(false);
        mPanoramaView.setStereoModeButtonEnabled(false);
        mPanoramaView.setEventListener(new VrPanoramaEventListener() {
            @Override
            public void onClick() {
                VRObject object = mDataManager.getCurrentScene().isHitVRObject(MainActivity.this.mRateX, MainActivity.this.mRateY);
                if (object != null) {
                    Toast.makeText(MainActivity.this, object.getName(), Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(MainActivity.this, "点击的位置x:" + MainActivity.this.mRateX + "mRateY:" + MainActivity.this.mRateY, Toast.LENGTH_SHORT).show();
            }
        });
        if (mImageLoaderTask != null) {
            mImageLoaderTask.cancel(true);
        }
        mRoateManager = new RoateManager(this, mPanoramaView, new RoateManager.onHeadRotationChangeListener() {
            @Override
            public void onHeadRotation(float x, float y) {
                MainActivity.this.mRateX = x;
                MainActivity.this.mRateY = y;
                mCurrentPath = mDataManager.getCurrentScene().isHitVRPath(x, y);
                mGoNext.setVisibility(mCurrentPath == null ? View.GONE : View.VISIBLE);
            }
        });
        mGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPath != null) {
                    Scene scene = mCurrentPath.getNextScene();
                    loadData(scene);
                    mDataManager.setCurrentScene(scene);
                }
            }
        });
        loadData(mDataManager.getCurrentScene());
    }

    private void loadData(Scene currentScene) {
        mImageLoaderTask = new ImageLoaderTask();
        mImageLoaderTask.execute(Pair.create(currentScene.getScenePic(), mPanoOptions));
    }

    @Override
    protected void onPause() {
        mPanoramaView.pauseRendering();
        mRoateManager.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoramaView.resumeRendering();
        mRoateManager.start();
    }

    @Override
    protected void onDestroy() {
        mPanoramaView.shutdown();
        mRoateManager.stop();

        if (mImageLoaderTask != null) {
            mImageLoaderTask.cancel(true);
        }
        super.onDestroy();

    }

    /**
     * Helper class to manage threading.
     */
    class ImageLoaderTask extends AsyncTask<Pair<String, VrPanoramaView.Options>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Pair<String, VrPanoramaView.Options>... fileInformation) {
            VrPanoramaView.Options panoOptions = null;  // It's safe to use null VrPanoramaView.Options.
            InputStream istr = null;

            try {
                istr = getApplicationContext().getAssets().open(fileInformation[0].first);
                panoOptions = fileInformation[0].second;
            } catch (IOException e) {
                Log.e(TAG, "Could not load file: " + e);
                return false;
            }

            mPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(istr), panoOptions);
            mRoateManager.start();
            try {
                istr.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close input stream: " + e);
            }

            return true;
        }
    }

}
