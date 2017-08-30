package com.jason.voicelibrary.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.jason.voicelibrary.R;

/**
 * Created by Admin on 2017-08-07.
 */

public class RecordViewDialog extends Dialog {
    private Context context;
    private VoiceLineView voicLine;
    private ImageView ok, delete;
    private Chronometer chronometer;
    private View.OnClickListener listener;

    public RecordViewDialog(Context context, int theme, View.OnClickListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        this.setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        chronometer.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        chronometer.stop();
    }

    public void setVolume(int volume) {
        voicLine.setVolume(volume);
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.record_view_dialog, null);
        setContentView(view);
        voicLine = (VoiceLineView) view.findViewById(R.id.voicLine);
        ok = (ImageView) view.findViewById(R.id.ok);
        delete = (ImageView) view.findViewById(R.id.delete);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        ok.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        //设置dialog大小，这里是一个小赠送，模块好的控件大小设置
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.45); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(params);
    }
}