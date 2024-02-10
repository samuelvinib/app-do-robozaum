package com.ainirobot.robotos.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.ainirobot.robotos.MainActivity;
import com.ainirobot.robotos.R;

public class ScreenSaver extends Fragment {
    protected MainActivity mActivity;
    private static final String ARG_VIDEO_URL = "video_url";
    private String mVideoUrl;

    public ScreenSaver() {
        // Required empty public constructor
    }

    public static ScreenSaver newInstance(String videoUrl) {
        ScreenSaver fragment = new ScreenSaver();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString(ARG_VIDEO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_saver, container, false);
        VideoView videoView = view.findViewById(R.id.video_view);

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
                    mActivity.switchFragment(MainFragment.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainActivity");
        }
    }
}
