package com.dk.view.visualizer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dk.view.visualizer.util.MusicVisualizerView;
import com.dk.view.visualizer.util.VisualizerData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MusicVisualizerView musicVisualizerView;
    int[] data = new int[]{
            01, 01, 01, 12, 20, 30, 40, 66, 99, 66,
            44, 12, 33, 55, 77, 88, 99, 57, 44, 33,
            22, 11, 02, 03, 14, 44, 55, 77, 99, 87,
            55, 33, 22, 26, 27, 33, 55, 66, 73, 23,
            13, 12, 11, 07, 14, 22, 24, 27, 33, 42,
            67, 76, 82, 93, 76, 54, 42, 32, 21, 07,
            13, 12, 11, 07, 14, 22, 24, 27, 33, 42,
            67, 76, 82, 93, 76, 54, 01, 01, 01, 00
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicVisualizerView = (MusicVisualizerView) findViewById(R.id.music_visualizer);

        //Test data
        List<VisualizerData> dataList = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
//            dataList.add(new VisualizerData((int) (Math.random() * Short.MAX_VALUE)));
            dataList.add(new VisualizerData(data[i]));
        }


        musicVisualizerView.setData(dataList);
        ObjectAnimator bufferAnimation = ObjectAnimator.ofFloat(musicVisualizerView, "bufferCurrent", 0, 1);
        bufferAnimation.setDuration(6000);
        bufferAnimation.start();

        ObjectAnimator playAnimtion = ObjectAnimator.ofInt(musicVisualizerView, "current", 0, 1000);
        playAnimtion.setStartDelay(6000);
        playAnimtion.setDuration(6000);
        playAnimtion.start();
    }
}
