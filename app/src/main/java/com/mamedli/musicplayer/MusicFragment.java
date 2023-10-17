package com.mamedli.musicplayer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamedli.musicplayer.databinding.FragmentMusicBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MusicFragment extends Fragment {

    private FragmentMusicBinding binding;
    private static final String MUSIC_LIST = "music_list";

    private ArrayList<String> musicList;

    public static MusicFragment newInstance(ArrayList<String> musicList) {
        MusicFragment musicFragment = new MusicFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(MUSIC_LIST, musicList);
        musicFragment.setArguments(args);

        return musicFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            musicList = getArguments().getStringArrayList(MUSIC_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMusicBinding.inflate(inflater, container, false);

        setupRecyclerView();
        return binding.getRoot();
    }

    private void setupRecyclerView(){
        MusicAdapter musicAdapter = new MusicAdapter(musicList);
        binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMusicList.setAdapter(musicAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}