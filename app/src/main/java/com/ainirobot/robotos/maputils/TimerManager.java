package com.ainirobot.robotos.maputils;

import android.os.Handler;
import android.util.Log;

import com.ainirobot.robotos.fragment.ScreenSaver;
import com.ainirobot.robotos.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private static TimerManager instance;
    private MainActivity mActivity;

    private Timer timer;
    private long elapsedTime = 0;
    private boolean isRunning = false;
    private Handler handler;
    private static final long DELAY = 1000;

    private TimerManager(MainActivity activity) {
        mActivity = activity;
        handler = new Handler();
    }

    public static synchronized TimerManager getInstance(MainActivity activity) {
        if (instance == null) {
            instance = new TimerManager(activity);
        }
        return instance;
    }

    public void start() {
        if (!isRunning) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    elapsedTime += 1000;
                    Log.i("Adiciona ai", String.valueOf(elapsedTime));
                    checkElapsedTime();
                }
            }, 0, 1000);
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            timer.cancel();
            isRunning = false;
        }
    }

    public void resume() {
        if (!isRunning) {
            start();
        }
    }

    public void reset() {
        if (isRunning) {
            timer.cancel();
            isRunning = false;
        }
        elapsedTime = 0;
    }

    private void checkElapsedTime() {
        long elapsedTimeInSeconds = elapsedTime / 1000;
        if (elapsedTimeInSeconds >= 5) {
            pause();
            reset();
            try {
                mActivity.switchFragment(ScreenSaver.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
