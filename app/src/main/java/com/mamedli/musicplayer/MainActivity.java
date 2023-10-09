package com.mamedli.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mamedli.musicplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final int MY_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MusicFragment musicFragment = MusicFragment.newInstance(getMusic());


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


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST);
        }
    }

    public ArrayList<String> getMusic() {
        ArrayList<String> arrayList = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Audio.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%sdcard/Music/%"};


        Cursor songCursor = contentResolver.query(songUri, null, selection, selectionArgs, null);
        if (songCursor != null) {
            Log.d("MyApp", "Cursor is not null, count: " + songCursor.getCount());
        } else {
            Log.d("MyApp", "Cursor is null");
        }

        if (songCursor != null && songCursor.moveToFirst()) {
            // ...
        } else {
            Log.d("MyApp", "Cursor is empty or cannot move to first");
        }
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do {
                String currentTitle = songCursor.getString(songTitle);
                arrayList.add(currentTitle);
            } while (songCursor.moveToNext());
            songCursor.close();
        }
        return arrayList;
    }

}