package com.jason.voicelibrary;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Admin on 2017-08-07.
 */

public class RecordThread extends Thread {
    private static final String TAG = Recorder.class.getSimpleName();
    private static final int SAMPLE_RATE_INHZ = 8000;
    private boolean running;
    private AudioRecord audioRecord;
    private int bufSize;
    private byte[] mBytes;
    private FileOutputStream os;
    private Handler handler;

    public RecordThread(File file, Handler handler) {
        this.handler = handler;
        bufSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufSize);
        mBytes = new byte[bufSize];

        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            running = true;
            byte[] bytes_pkg;
            audioRecord.startRecording();
            while (running) {
                int len = audioRecord.read(mBytes, 0, bufSize);
                if (len > 0) {
                    updataVolume(mBytes);
                    bytes_pkg = mBytes.clone();
                    try {
                        os.write(bytes_pkg, 0, bytes_pkg.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updataVolume(byte[] buffer) {
        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) buffer.length;
        double volume = 10 * Math.log10(mean);

        Message message = new Message();
        message.obj = volume;
        handler.sendMessage(message);
    }

    public void quit() {
        running = false;
    }

    private void release() {
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
        mBytes = null;
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "stop");
    }
}
