package ru.mirea.sukhanovmd.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.sukhanovmd.looper.databinding.ActivityMainBinding;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task executed. Result: " + msg.getData().getString("result"));
            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = binding.editTextAge.getText().toString();
                String job = binding.editTextJob.getText().toString();
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("AGE", age);
                bundle.putString("JOB", job);
                msg.setData(bundle);
                myLooper.mHandler.sendMessageDelayed(msg, Integer.parseInt(age) * 1000);
            }
        });
    }
}