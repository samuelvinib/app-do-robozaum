package com.ainirobot.robotos.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ainirobot.robotos.R;
import com.ainirobot.robotos.maputils.TimerManager;

public class MainFragment extends BaseFragment {
    protected TextView mDynamicText;
    protected Handler mHandler;
    protected TimerManager mTimerManager;


    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_main_layout,null,false);
        bindViews(root);
        hideBackView();
        mTimerManager = TimerManager.getInstance(mActivity);
        mTimerManager.start();
        hideResultView();
        return root;
    }

    private void bindViews(View root) {
        mDynamicText = root.findViewById(R.id.dynamicTextView);

    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}
