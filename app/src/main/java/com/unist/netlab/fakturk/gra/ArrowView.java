package com.unist.netlab.fakturk.gra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fakturk on 24/10/2016.
 */

public class ArrowView extends View
{
    Paint paint = new Paint();

    public ArrowView(Context context)
    {

        super(context);
        Paint paint = new Paint();
    }
    public ArrowView(Context context, AttributeSet attrs, int defStyle)
    {

        super(context, attrs, defStyle);
        Paint paint = new Paint();
    }
    public ArrowView(Context context, AttributeSet attrs)
    {

        super(context, attrs);
        Paint paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        canvas.drawLine(0, 0, 250, 250, paint);
        canvas.drawLine(250, 0, 0, 250, paint);
    }
}
