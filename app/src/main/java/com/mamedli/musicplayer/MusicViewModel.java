package com.mamedli.musicplayer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MusicViewModel extends ViewModel {
    private ArrayList<String> musicList;

    public void setMusicList(ArrayList<String> musicList) {
        this.musicList = musicList;
    }

    public LiveData<ArrayList<String>> getMusicList() {
        return new MutableLiveData<>(musicList);
    }
}
