package com.ainirobot.robotos.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.ainirobot.robotos.R;

public class ScreenSaver extends BaseFragment {
    protected String mVideoUrl;

    @Override
    public View onCreateView(Context context) {
        mVideoUrl = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.background;
        View root = mInflater.inflate(R.layout.fragment_screen_saver,null,false);
        VideoView videoView = root.findViewById(R.id.video_view);

        if (mVideoUrl != null) {
            videoView.setVideoPath(mVideoUrl);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
            videoView.start();
        }

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   switchFragment(MainFragment.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bindViews(root);
        hideBackView();
        hideResultView();
        return root;
    };

    private void bindViews(View root) {
        VideoView mVideoView = root.findViewById(R.id.video_view);
        mVideoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switchFragment(MainFragment.newInstance());
            }
        });


    }


    public static Fragment newInstance() {
        return new ScreenSaver();
    }
}
