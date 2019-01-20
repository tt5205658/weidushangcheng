package com.example.bw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ViewSj extends View {
    private Context mContext;
    private Paint p;

    public ViewSj(Context context) {
        super(context);
        mContext=context;
    }

    public ViewSj(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    private void init() {
        p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
