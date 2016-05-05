package com.dk.view.visualizer.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dk on 16/4/28.
 */
public class MusicVisualizerView extends View {

    private List<VisualizerData> dataList = new ArrayList<>();

    private long duration = 1000;
    private int current = 0;
    private float bufferCurrent = 0.0f;

    private int gap = 5;
    private int barWidth = 0;
    private int barHeightMax = 0;
    //    private int valueMax = Short.MAX_VALUE;
    private int valueMax = 100;
    private int count = 0;
    private Paint paint;
    private Paint shadowPaint;
    private Shader shader;
    private int centerY = 0;
    private int centerBarHeight = 12;

    public MusicVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setData(List<VisualizerData> dataList) {
        this.dataList = dataList;
        computerParams();
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                computerParams();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 计算&初始化绘制数据
     */
    private void computerParams() {
        if (dataList == null || dataList.size() == 0)
            return;
        count = dataList.size();
        centerY = getHeight() / 2;
        barWidth = (getWidth() - (count - 1) * gap) / count;
        barHeightMax = getHeight() / 2;
        paint = new Paint();
        paint.setColor(0xffffffff);
        shadowPaint = new Paint();
        shadowPaint.setColor(0x77ffffff);
    }

    private float getProgress() {
        return current * 1.0f / duration;
    }

    private float getBufferProgress() {
        return bufferCurrent;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (dataList == null || dataList.size() == 0)
            return;
        //完整的bar个数
        int bufferCount = (int) (dataList.size() * getBufferProgress());
        //正在等待填充的bar的进度
        float bufferPercent = dataList.size() * getBufferProgress() - bufferCount;


        //完整的bar个数
        int playCount = (int) (dataList.size() * getProgress());
        //正在等待填充的bar的进度
        float playPercent = dataList.size() * getProgress() - bufferCount;

        //先绘制所有完整的bar
        for (int i = 0; i < Math.min(bufferCount, dataList.size()); i++) {
            drawTopBar(dataList.get(i), i, canvas, 1, i > playCount ? shadowPaint : paint);
            drawBottomBar(dataList.get(i), i, canvas, 1, i > playCount ? shadowPaint : paint);
        }
        if (bufferCount < dataList.size()) {
            //绘制正在填充中的bar
            drawTopBar(dataList.get(bufferCount), bufferCount, canvas, bufferPercent, shadowPaint);
            drawBottomBar(dataList.get(bufferCount), bufferCount, canvas, bufferPercent, shadowPaint);
        }
        drawCenterBar(canvas);
        postInvalidate();
    }

    /**
     * 绘制音量柱的上半截
     *
     * @param data     数据源
     * @param position 位置
     * @param canvas   画布
     * @param percent  填充比例
     */
    public void drawTopBar(VisualizerData data, int position, Canvas canvas, float percent, Paint paint) {
        canvas.drawRect(barWidth * position + gap * position, centerY - percent * data.getValue() * 1.0f / valueMax * barHeightMax, barWidth * (position + 1) + gap * position, centerY, paint);
    }

    /**
     * 绘制音量柱的下半截
     *
     * @param data     数据源
     * @param position 位置
     * @param canvas   画布
     * @param percent  填充比例
     */
    public void drawBottomBar(VisualizerData data, int position, Canvas canvas, float percent, Paint paint) {
        canvas.drawRect(barWidth * position + gap * position, centerY, barWidth * (position + 1) + gap * position, centerY + percent * data.getValue() * 1.0f / valueMax * barHeightMax, paint);
    }

    /**
     * 画中间那杠
     *
     * @param canvas
     */
    public void drawCenterBar(Canvas canvas) {
        canvas.drawRect(0, centerY - centerBarHeight / 2, getWidth(), centerY + centerBarHeight / 2, paint);
    }


    public int getCurrent() {
        return current;
    }

    public void setBufferCurrent(float bufferCurrent) {
        this.bufferCurrent = bufferCurrent;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
