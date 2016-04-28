package com.dk.view.visualizer.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dk on 16/4/28.
 */
public class MusicVisualizerView extends View {

    private List<VisualizerData> dataList = new ArrayList<>();

    private long duration = 1000;
    private long current = 0;
    private long bufferCurrent = 0;

    private int gap = 10;
    private int barWidth = 0;
    private int barHeightMax = 0;
    private int count = 0;

    public MusicVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<VisualizerData> dataList) {
        this.dataList = dataList;
        computerParams();
    }

    private void computerParams() {
        count = dataList.size();
        barWidth = (getWidth() - (count - 1) * gap) / count;
    }

    private float getProgress() {
        return current * 1.0f / duration;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (int i = 0; i < dataList.size(); i++) {
            drawBar(dataList.get(i), canvas);
        }
    }

    public void drawBar(VisualizerData data, Canvas canvas) {

    }


}
