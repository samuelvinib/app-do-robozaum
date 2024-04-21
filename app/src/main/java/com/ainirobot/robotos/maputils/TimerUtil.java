package com.ainirobot.robotos.maputils;


import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
    protected static TimerUtil instance;
    private Timer timer;
    private long elapsedTime = 0;
    private boolean isRunning = false;

    public void start() {
        if (!isRunning) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    elapsedTime += 1000;
                    Log.i("Adiciona ai", String.valueOf(elapsedTime));
                }
            }, 0, 1000);
            isRunning = true;
        }
    }

    public static synchronized TimerUtil getInstance() {
        if (instance == null) {
            instance = new TimerUtil();
        }
        return instance;
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
        timer.cancel();
        elapsedTime = 0;
        isRunning = false;
    }

    public long getElapsedTimeInSeconds() {
        return elapsedTime / 1000;
    }

    public long getElapsedTimeInMillis() {
        return elapsedTime;
    }
}
