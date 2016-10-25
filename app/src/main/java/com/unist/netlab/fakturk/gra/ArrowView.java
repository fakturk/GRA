package com.unist.netlab.fakturk.gra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by fakturk on 24/10/2016.
 */

public class ArrowView extends SurfaceView implements SurfaceHolder.Callback
{
    Paint paint = new Paint();
    float lineStartX, lineStartY, lineFinishX, lineFinishY;
    Canvas canvas;
    int width, height, clientHeight;
    Context context;
    SurfaceHolder mHolder;
    DrawThread thread;

    Boolean _run;
    Boolean isDrawing = true;
    Bitmap mBitmap;

//    public ArrowView(Context context)
//    {
//
//        super(context);
//        this.context = context;
//        init();
////        Paint paint = new Paint();
//    }
//    public ArrowView(Context context, AttributeSet attrs, int defStyle)
//    {
//
//        super(context, attrs, defStyle);
//        this.context = context;
//
//        init();
////        Paint paint = new Paint();
//    }
    public ArrowView(Context context, AttributeSet attrs)
    {

        super(context, attrs);
        this.context = context;
        init();
//        Paint paint = new Paint();


//the bitmap we wish to draw

//         mbitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.topArrow);

        SurfaceHolder holder = getHolder();

        holder.addCallback(this);
    }

    class DrawThread extends Thread {
        // Local variable to store the reference to the surfaceHolder
        private SurfaceHolder mSurfaceHolder;

        // Constructor of DrawThread
        public DrawThread(SurfaceHolder surfaceHolder){
            // Store in a local variable the reference to the surfaceHolder.
            mSurfaceHolder = surfaceHolder;
        }

        // Method called by the surfaceCreated and surfaceDestroyed callbacks
        public void setRunning(boolean run) {
            _run = run;
        }

        @Override
        public void run() {


            while (_run){
                if(isDrawing == true){
                    try{
                        canvas = mSurfaceHolder.lockCanvas(null);
                        // Perform graphical operations on the canvas
                        if(mBitmap == null){
                            mBitmap =  Bitmap.createBitmap (1, 1, Bitmap.Config.ARGB_8888);
                        }
                        final Canvas c = new Canvas (mBitmap);

                        c.drawColor(Color.WHITE);
                        c.drawCircle(80,80, 30, paint);
                        c.drawLine(80, 80, 80, 200, paint);
                        c.drawText(""+canvas.getWidth()+", "+canvas.getHeight(), 0, 200,paint);

                        canvas.drawBitmap (mBitmap, 0,  0,null);
                    } finally {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        lineStartX= this.getWidth()/2;
        lineStartY = this.getHeight()/2;
        System.out.println("ondraw: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);

        canvas.drawLine(0, 0, 250, 250, paint);
        canvas.drawLine(250, 0, 0, 250, paint);
        canvas.drawLine(lineStartX,lineStartY,lineStartX+lineFinishX,lineStartY+lineFinishY,paint);

    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas)
//    {
//
//        super.dispatchDraw(canvas);
//        lineStartX= this.getWidth()/2;
//        lineStartY = this.getHeight()/2;
//        System.out.println("ondraw: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);
//
//        canvas.drawLine(0, 0, 250, 250, paint);
//        canvas.drawLine(250, 0, 0, 250, paint);
//        canvas.drawLine(lineStartX,lineStartY,lineStartX+lineFinishX,lineStartY+lineFinishY,paint);
//
//    }

    void setLine(float lineFinishX, float lineFinishY)
    {
        this.lineFinishX = lineFinishX;
        this.lineFinishY = lineFinishY;
        System.out.println("setLine: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);
//        invalidate();
        System.out.println("setLine after invalidate: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);

    }

    void init()
    {
//        setWillNotDraw(false);
        mHolder = getHolder();
        mHolder.addCallback(this);

        lineStartX=0;
        lineStartY=0;
        lineFinishX=0;
        lineFinishY=0;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
// Starts thread execution
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        mBitmap =  Bitmap.createBitmap (width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        // Finish thread execution
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}
