package com.unist.netlab.fakturk.gra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView tv_gravity;
    Intent i;
    Button buttonStart;

    float[] acc, gyr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_gravity = (TextView) findViewById(R.id.tv_gravity);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        i = new Intent(this, SensorService.class);



        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                acc = (intent.getFloatArrayExtra("ACC_DATA"));
                gyr = intent.getFloatArrayExtra("GYR_DATA");


                tv_gravity.setText("Fatih");
                tv_gravity.setText(intent.getStringExtra("ACC"));
            }
        }, new IntentFilter(SensorService.ACTION_SENSOR_BROADCAST));


        buttonStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (buttonStart.getText().equals("Start")) {
                    buttonStart.setText("Stop");
                    startService(new Intent(MainActivity.this,SensorService.class));

                }
                else
                {
                    buttonStart.setText("Start");
                    stopService(new Intent(MainActivity.this,SensorService.class));


                }
            }
        });
    }
}