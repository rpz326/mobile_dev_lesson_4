package ru.mirea.sukhanovmd.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String age = bundle.getString("AGE");
                String job = bundle.getString("JOB");

                Log.d("MyLooper get message: ", "Возраст: " + age + " Работа: " + job);

                Message message = new Message();
                Bundle resultBundle = new Bundle();
                resultBundle.putString("result", "Возраст: " + age + " Работа: " + job);
                message.setData(resultBundle);

                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
