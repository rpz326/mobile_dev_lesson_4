package ru.mirea.sukhanovmd.lesson4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.sukhanovmd.lesson4.databinding.ActivityMainBinding;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isPlaying = false;
    private int currentTrackIndex = 0;

    private String[][] tracks = {
            {"Первый трек", "Исполнитель 1"},
            {"Второй трек", "Исполнитель 2"},
            {"Третий трек", "Исполнитель 3"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setCurrentTrack(currentTrackIndex);

        binding.playPauseButton.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            if (isPlaying) {
                binding.playPauseButton.setImageResource(R.drawable.pause_circle_28);
            } else {
                binding.playPauseButton.setImageResource(R.drawable.play_circle_28);
            }
        });

        binding.skipBackButton.setOnClickListener(v -> {
            if (currentTrackIndex > 0) {
                currentTrackIndex--;
                setCurrentTrack(currentTrackIndex);
            }
        });

        binding.skipForwardButton.setOnClickListener(v -> {
            if (currentTrackIndex < tracks.length - 1) {
                currentTrackIndex++;
                setCurrentTrack(currentTrackIndex);
            }
        });
    }

    private void setCurrentTrack(int index) {
        binding.songTitleTextView.setText(tracks[index][0]);
        binding.artistNameTextView.setText(tracks[index][1]);
    }
}