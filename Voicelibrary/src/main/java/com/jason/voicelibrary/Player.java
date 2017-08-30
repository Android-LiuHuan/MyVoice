package com.jason.voicelibrary;

import android.content.Context;
import com.jason.voicelibrary.utils.FileUtil;
import java.io.File;

/**
 * Created by Jason on 2017-08-29 11:54
 */

public class Player {
    private static Player instance;
    private String fileName = "temp.wav";
    private PlayerThread playerThread;
    private File file;
    private float pitchShift;

    private Player() {
    }

    public static synchronized Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public Player setPitchShift(float pitchShift) {
        this.pitchShift = pitchShift;
        return instance;
    }

    public void start(Context context) {
        if (playerThread != null) {
            stop();
        }
        file = new File(FileUtil.getCacheRootFile(context), fileName);
        playerThread = new PlayerThread(file, pitchShift);
        playerThread.start();
    }

    public void stop() {
        if (playerThread != null) {
            playerThread.quit();
            playerThread = null;
        }
    }
}
