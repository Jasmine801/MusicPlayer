package com.mamedli.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mamedli.musicplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final int MY_PERMISSION_REQUEST = 100;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> audioFiles = getAudioFiles(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permissions", "Requesting READ_MEDIA_AUDIO");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                    MY_PERMISSION_REQUEST);
        } else {
            Log.d("Permissions", "READ_MEDIA_AUDIO already granted");
        }


        for (String audioFile : audioFiles) {
            Log.d("AudioFiles", "Title: " + audioFile);
        }
        MusicFragment musicFragment = MusicFragment.newInstance(audioFiles);


        binding.bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_player){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, musicFragment) // использовать ваш ID контейнера
                            .commit();
                } else if (item.getItemId() == R.id.nav_recorder) {
                    //
                }
                
                return true;
            }
        });

    }
    public ArrayList<String> getAudioFiles(Context context) {
        ArrayList<String> audioFiles = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String[] projection = {MediaStore.Audio.Media.TITLE};
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int songTitle = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
               // int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                do {
                    //String audioFilePath = cursor.getString(dataColumn);
                    String currentTitle = cursor.getString(songTitle);
                    audioFiles.add(currentTitle);
                } while (cursor.moveToNext());
            }
        }

        return audioFiles;
    }


}