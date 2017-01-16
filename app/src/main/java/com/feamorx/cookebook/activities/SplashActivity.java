package com.feamorx.cookebook.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feamorx.cookebook.R;
import com.feamorx.cookebook.activities.MainActivity;
import com.feamorx.cookebook.model.MasterData;
import com.feamorx.cookebook.test.Points;
import com.feamorx.cookebook.test.Tester;
import com.feamorx.cookebook.test.tests.UseTestAssetsMasterDataTest;
import com.feamorx.cookebook.utils.CounterListener;
import com.feamorx.cookebook.utils.DataManager;
import com.feamorx.cookebook.utils.DataStorage;
import com.feamorx.cookebook.utils.Settings;

import java.io.File;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    private Settings settings;

    private boolean activityClosed;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityClosed = false;
        handler = new Handler(Looper.getMainLooper());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Tester.get().setTest(new UseTestAssetsMasterDataTest(this, "TestMasterData.js"));

        DataManager.get();
        settings = new Settings(this);
        checkAndLoadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            synchronized (this) {
                activityClosed = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (this) {
            activityClosed = true;
        }
    }

    public void uiPost(Runnable runnable) {
        synchronized (this) {
            if (!activityClosed) {
                runOnUiThread(runnable);
            }
        }
    }

    public void handlerPost(Runnable runnable) {
        synchronized (this) {
            if (!activityClosed) {
                handler.post(runnable);
            }
        }
    }

    public void handlerPostDelayed(Runnable runnable, long wait) {
        synchronized (this) {
            if (!activityClosed) {
                handler.postDelayed(runnable, wait);
            }
        }
    }

    private void checkAndLoadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dataFile = new File(getFilesDir(), "data.json");
                boolean needLoad = dataFile.exists();
                needLoad = (boolean) Tester.get().model(Points.Splash.CHECK_NEED_LOAD_DATA_FROM_FILE, needLoad);
                if (needLoad) {
                    DataStorage dataStorage = new DataStorage();
                    MasterData master = dataStorage.loadFromFile(dataFile.getAbsolutePath());
                    master = (MasterData) Tester.get().model(Points.Splash.UPDATE_LOADED_DATA_FROM_FILE, master);
                    if (master != null && master.updateTime != null) {
                        long now = Calendar.getInstance().getTime().getTime();
                        long updated = master.updateTime.getTime();
                        long interval = settings.getUpdateDataInterval();

                        boolean needUpdate = now - updated > interval;
                        needUpdate = (boolean) Tester.get().model(Points.Splash.CHECK_UPDATE_DATA_TIMEOUT, needUpdate, updated, now, interval);

                        if (needUpdate) {
                            //update
                            String url = settings.getUpdateDataUrl(master.version);
                            url = (String) Tester.get().model(Points.Splash.UPDATE_LOAD_DATA_URL_WITH_VERSION, url, master.version);
                            MasterData loadedMaster = dataStorage.loadFromUrl(url);
                            loadedMaster = (MasterData) Tester.get().model(Points.Splash.UPDATE_LOADED_DATA_FROM_URL_WITH_VERSION, loadedMaster);
                            if (loadedMaster != null && master.needUpdateByVersion(loadedMaster)) {
                                DataManager.get().setMasterData(loadedMaster);
                            } else {
                                DataManager.get().setMasterData(master);
                            }
                        } else {
                            //use
                            DataManager.get().setMasterData(master);
                        }
                    }
                } else {
                    String url = settings.getUpdateDataUrl(null);
                    url = (String) Tester.get().model(Points.Splash.UPDATE_LOAD_DATA_URL_NO_VERSION, url);
                    DataStorage dataStorage = new DataStorage();

                    MasterData loadedMaster = dataStorage.loadFromUrl(url);
                    loadedMaster = (MasterData) Tester.get().model(Points.Splash.UPDATE_LOADED_DATA_FROM_URL_NO_VERSION, loadedMaster);
                    if (loadedMaster != null) {
                        DataManager.get().setMasterData(loadedMaster);
                    } else {
                        DataManager.get().setMasterData(null);
                    }
                }

                handlerPost(new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                    }
                });
            }
        }).start();
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void loadDataFromUrl(final CounterListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(listener);
            }
        }).start();

    }

    private void loadSavedData(final CounterListener listener) {
        File dataFile = new File(getFilesDir(), "data.json");
        runOnUiThread(listener);
    }

    private class  DataLoadedListener extends CounterListener {

        public DataLoadedListener(int counter) {
            super(counter);
        }

        @Override
        public void onComplete() {

        }
    };

}
