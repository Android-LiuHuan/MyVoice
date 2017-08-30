package com.jason;

/**
 * Created by Jason on 2017-08-29 17:53
 */

public class VoiceProcessor {
    static {
        System.loadLibrary("smbPitchShift");
    }

    public static synchronized byte[] dispose(float pitchShift, int sampleRate, int size, byte[] in) {
        byte[] out = new byte[size];
        dispose(pitchShift, sampleRate, size, in, out, new float[size / 2], new float[size / 2]);
        return out;
    }

    private static native void dispose(float pitchShift, int sampleRate, int size, byte[] in, byte[] out, float[] floatIn, float[] floatOut);
}
