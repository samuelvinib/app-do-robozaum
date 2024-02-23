/*
 *  Copyright (C) 2017 OrionStar Technology Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ainirobot.robotos.fragment;

import android.content.Context;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import android.os.Handler;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ainirobot.coreservice.client.listener.Person;
import com.ainirobot.coreservice.client.person.PersonApi;
import com.ainirobot.coreservice.client.person.PersonListener;
import com.ainirobot.robotos.R;
import com.ainirobot.robotos.maputils.FaceDetectionUtils;


import org.w3c.dom.Text;

public class MainFragment extends BaseFragment {
    private static Timer timer;

    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_main_layout,null,false);
        bindViews(root);
        hideBackView();
        hideResultView();
        return root;
    }

    private void bindViews(View root) {
        TextView mDynamicText = root.findViewById(R.id.dynamicTextView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mDynamicText.setText("Aguarde...");
            }
        }, 2000);

        mActivity.switchFragment(ScreenSaver.newInstance());


    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}
