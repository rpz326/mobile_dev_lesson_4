package ru.mirea.sukhanovmd.thread;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.Arrays;
import ru.mirea.sukhanovmd.thread.databinding.ActivityMainBinding;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.sukhanovmd.thread.databinding.ActivityMainBinding;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = binding.infoTextView;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-09-21, НОМЕР ПО СПИСКУ: 21");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.button.setOnClickListener(v -> {
            calculateAverageLessons();
        });
    }

    private void calculateAverageLessons() {
        int totalLessons = Integer.parseInt(binding.editTextTotalLessons.getText().toString());
        int totalDays = Integer.parseInt(binding.editTextTotalDays.getText().toString());

        Thread backgroundThread = new Thread(() -> {
            double averageLessonsPerDay = (double) totalLessons / totalDays;

            runOnUiThread(() -> {
                binding.textView.setText(String.format("Среднее количество пар в день за месяц: %.2f", averageLessonsPerDay));
            });
        });
        backgroundThread.start();
    }
}