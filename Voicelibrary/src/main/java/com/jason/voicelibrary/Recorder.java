package com.jason.voicelibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.jason.voicelibrary.utils.FileUtil;
import com.jason.voicelibrary.view.RecordViewDialog;
import java.io.File;

/**
 * Created by Admin on 2017-08-07.
 */

public class Recorder {
    private static Recorder instance;
    private String fileName = "temp.wav";
    private RecordThread recordThread;
    private File file;
    private RecordViewDialog recordViewDialog;
    private RecordListener recordListener;

    private Recorder() {
    }

    public static synchronized Recorder getInstance() {
        if (instance == null) {
            instance = new Recorder();
        }
        return instance;
    }

    public Recorder setListener(RecordListener listener) {
        recordListener = listener;
        return instance;
    }

    public void start(Context context) {
        if (recordThread != null) {
            stop();
        }
        file = new File(FileUtil.getCacheRootFile(context), fileName);
        recordThread = new RecordThread(file, handler);
        recordThread.start();

        recordViewDialog = new RecordViewDialog(context, R.style.Dialog, onClickListener);
        recordViewDialog.show();
    }

    public void stop() {
        if (recordThread != null) {
            recordThread.quit();
            recordThread = null;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recordViewDialog != null) recordViewDialog.dismiss();
            }
        }, 100);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stop();
            int i = v.getId();
            if (i == R.id.ok) {
                recordListener.onComplete(file.getPath());
            } else if (i == R.id.delete) {
                new File(FileUtil.getCacheRootFile(v.getContext()), fileName).delete();
                recordListener.onCancel();
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (recordThread == null) return;
            if (recordViewDialog != null) {
                try {
                    recordViewDialog.setVolume(Integer.parseInt(new java.text.DecimalFormat("0").format(msg.obj)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
