package com.ainirobot.robotos.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ainirobot.coreservice.client.ApiListener;
import com.ainirobot.coreservice.client.listener.Person;
import com.ainirobot.coreservice.client.person.PersonApi;
import com.ainirobot.coreservice.client.listener.TextListener;
import com.ainirobot.coreservice.client.speech.SkillApi;
import com.ainirobot.coreservice.client.speech.SkillCallback;
import com.ainirobot.coreservice.client.speech.entity.TTSEntity;
import com.ainirobot.robotos.LogTools;
import com.ainirobot.robotos.MainActivity;
import com.ainirobot.robotos.R;
import com.ainirobot.robotos.application.RobotOSApplication;
import com.ainirobot.robotos.application.SpeechCallback;
import com.ainirobot.robotos.maputils.TimerManager;

import java.util.List;

public class MainFragment extends BaseFragment {
    private SkillApi mSkillApi;
    private TextListener mTextListener;
    private SkillCallback mSkillCallback;

    private SpeechCallback mSpeechCallback;

    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_main_layout, null, false);
        bindViews(root);
        hideBackView();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            Intent serviceIntent = new Intent(activity, TimerManager.class);
            activity.startService(serviceIntent);
        }
        hideResultView();

        return root;
    }

    private void bindViews(View root) {
        TextView mDynamicText = root.findViewById(R.id.dynamicTextView);

        mDynamicText.setOnClickListener(v -> {
            try {
                // Inicie a conversa básica
                if (mSkillApi != null) {
                    mSkillApi.playText(new TTSEntity("sid-1234567890", "Hi, how are you?"), mTextListener);
                    // Aguarde alguns segundos antes de enviar a próxima mensagem
                    Thread.sleep(3000);
                    mSkillApi.playText(new TTSEntity("sid-1234567890", "It's works!!"), mTextListener);
                }

                mDynamicText.setText("Tell me something");

                mSkillApi.setRecognizable(true);
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}
