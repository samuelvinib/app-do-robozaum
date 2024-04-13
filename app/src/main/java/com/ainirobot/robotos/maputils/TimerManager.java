package com.ainirobot.robotos.maputils;

import android.util.Log;
import android.view.View;

import com.ainirobot.coreservice.client.listener.Person;
import com.ainirobot.coreservice.client.person.PersonApi;
import com.ainirobot.robotos.fragment.MainFragment;
import com.ainirobot.robotos.fragment.ScreenSaver;
import com.ainirobot.robotos.MainActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private static TimerManager instance;
    private final MainActivity mActivity;

    private Timer timer;

    private final PersonApi mPersonApi; // Adicionado para lidar com a detecção de rosto
    private long elapsedTime = 0;
    private boolean isRunning = false;

    private TimerManager(MainActivity activity) {
        mActivity = activity;
        mPersonApi = PersonApi.getInstance(); // Inicialize o PersonApi
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
                    long elapsedTimeInSeconds = elapsedTime / 1000;
                    Log.i("Add Time", String.valueOf(elapsedTime));
                    View focusedView = mActivity.getCurrentFocus();
                    if (focusedView != null) {
                        Log.i("My view", focusedView.toString());
                    } else {
                        Log.i("My view", "No view is currently focused");
                    }

                    try {
//                        List<Person> allFaceList = mPersonApi.getAllFaceList();
                        boolean allFaceList = false;
                        if(elapsedTimeInSeconds >= 10){
                            reset();
                            mActivity.switchFragment(ScreenSaver.newInstance());
                        }
                        checkPersonChange();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public void faceDetected() {
        pause();
        reset();
    }

    public void noFaceDetected() {
        resume();
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
        if (elapsedTimeInSeconds >= 20) {
            pause();
            reset();
            try {
                mActivity.switchFragment(ScreenSaver.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPersonChange() {
        List<Person> allFaceList = mPersonApi.getAllFaceList();
        if (!allFaceList.isEmpty()) {
            Log.i("Face Scan", "Face Detected");
            // Se rosto detectado, pause o timer
            faceDetected();
        } else {
            Log.i("Face Scan", "Face Detected");
            // Se nenhum rosto detectado, retome o timer
            noFaceDetected();
        }
    }

}
