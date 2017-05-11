package com.example.chenfu.ex05_04;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GeometricView extends View {
    private Paint paint = new Paint();
    private boolean Rect = false;
    private int color_argb = Color.argb(255, 0, 0, 255); ;
    /*Layout建立時所呼叫的Method*/
    public GeometricView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    /*提供set方法*/
    public void setRect(boolean rect) {
        Rect = rect;
    }

    public void setColor_argb(int color_argb) {
        this.color_argb = color_argb;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color_argb);
        if(Rect){
            canvas.drawRect(150,150,500,500,paint);
        }else{
            canvas.drawCircle(500,500,300,paint);
        }
    }
}
