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
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintGreen= new Paint(Paint.ANTI_ALIAS_FLAG);

    float lineStartX, lineStartY, lineFinishX, lineFinishY, lineZStartX, lineZStartY, lineZFinishX, lineZFinishY;
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


        SurfaceHolder holder = getHolder();

        holder.addCallback(this);
        thread = new DrawThread(holder);
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
//                        c.drawCircle(80,80, 30, paint);
//                        c.drawLine(80, 80, 80, 200, paint);
//                        c.drawText(""+canvas.getWidth()+", "+canvas.getHeight(), 0, 200,paint);
//                        c.drawLine(0, 0, 250, 250, paint);
//                        c.drawLine(250, 0, 0, 250, paint);

                        System.out.println("ondraw: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);

                        if (Math.abs(lineFinishX)>Math.abs(lineFinishY))
                        {
                            c.drawLine(lineStartX,lineStartY,lineStartX+lineFinishX,lineStartY+lineFinishY,paintGreen);
                        }
                        else
                        {
                            c.drawLine(lineStartX,lineStartY,lineStartX+lineFinishX,lineStartY+lineFinishY,paintBlue);
                        }
                        c.drawLine(lineZStartX,lineZStartY,lineZStartX+lineZFinishX,lineZStartY+lineZFinishY,paint);

                        canvas.drawBitmap (mBitmap, 0,  0,null);
                    } finally {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }


//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//
//
//    }
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

    void setLine(float lineFinishX, float lineFinishY, float lineFinishZ)
    {
        isDrawing=true;

        this.lineFinishX = lineFinishX;
        this.lineFinishY = lineFinishY;
        this.lineZFinishY = lineFinishZ;
        System.out.println("setLine: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);
//        invalidate();
        isDrawing=false;
        System.out.println("setLine after invalidate: "+lineStartX+", "+lineStartY+", "+lineFinishX+", "+lineFinishY);

    }

    void init()
    {
//        setWillNotDraw(false);
        mHolder = getHolder();
        mHolder.addCallback(this);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setStrokeWidth(8);

        paintRed.setColor(Color.RED);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(8);

        paintBlue.setColor(Color.GREEN);
        paintBlue.setStyle(Paint.Style.STROKE);
        paintBlue.setStrokeWidth(8);

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
        lineStartX= this.getWidth()/2;
        lineStartY = this.getHeight()/2;
        lineZStartX = this.getWidth()-50;
        lineZStartY = lineStartY;
        lineZFinishX = lineZStartX;

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,  int height)
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
