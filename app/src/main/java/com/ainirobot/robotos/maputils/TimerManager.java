package com.ainirobot.robotos.maputils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

public class TimerManager extends Service {
    private Timer timer;
    private PersonApi mPersonApi;
    private long elapsedTime = 0;
    private boolean isRunning = true;
    private MainActivity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mPersonApi = PersonApi.getInstance();
        mActivity = MainActivity.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long elapsedTimeInSeconds = elapsedTime / 1000;
                    Log.i("Add Time", String.valueOf(elapsedTime));
                    if(isRunning){
                        elapsedTime += 1000;
                    }
                    try {
                        List<Person> allFaceList = mPersonApi.getAllFaceList();
                        if(elapsedTimeInSeconds >= 10){
                            resetTimer();
                            mActivity.switchFragment(ScreenSaver.newInstance());
                        } else if (!allFaceList.isEmpty() && !isRunning) {
                            Log.i("Face Scan", "Face Detected");
                            isRunning = true;
                            mActivity.switchFragment(MainFragment.newInstance());
                        }else if (!allFaceList.isEmpty()){
                            Log.i("Face Scan", "Face Detected");
                            resetTimer();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000); // Execute the task every second
        return START_STICKY; // Service will be restarted if it gets killed by the system
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void resetTimer() {
        if (isRunning) {
            isRunning = false;
        }
        elapsedTime = 0;
    }
}
