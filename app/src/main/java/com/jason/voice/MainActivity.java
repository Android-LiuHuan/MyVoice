package com.jason.voice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.jason.voicelibrary.Player;
import com.jason.voicelibrary.Recorder;
import com.jason.voicelibrary.RecordListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecordListener {
    private TextView tv, tv2;
    private Button btn1, btn2;
    private SeekBar seekBar;
    private float pitchShift = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pitchShift = (float) progress / 100;
                tv2.setText(String.valueOf(pitchShift));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Recorder.getInstance().setListener(this).start(this);
                break;
            case R.id.btn2:
                Player.getInstance().setPitchShift(pitchShift).start(this);
                break;
        }
    }

    @Override
    public void onComplete(String path) {
        tv.setText("path:" + path);
    }

    @Override
    public void onCancel() {
        tv.setText("path:");
    }
}
