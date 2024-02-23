package com.ainirobot.robotos.maputils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import com.ainirobot.coreservice.client.listener.Person;
import com.ainirobot.coreservice.client.person.PersonApi;
import com.ainirobot.coreservice.client.person.PersonListener;
public class FaceDetectionUtils {

    private static Timer timer;

    public static void setupFaceDetectionListener() {
        PersonListener listener = new PersonListener() {
            @Override
            public void personChanged() {
                super.personChanged();

                cancelTimer();
            }
        };
        PersonApi.getInstance().registerPersonListener(listener);

        startTimer();
    }

    private static void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Person> faceList = PersonApi.getInstance().getAllFaceList();
                if (faceList.isEmpty()) {
                    System.out.println("Foi");
                }
                cancelTimer();
            }
        }, 20000);
    }

    private static void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}