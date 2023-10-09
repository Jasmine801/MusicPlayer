package com.mamedli.musicplayer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamedli.musicplayer.databinding.FragmentMusicBinding;

import java.util.ArrayList;

public class MusicFragment extends Fragment {

    private FragmentMusicBinding binding;
    private static final String MUSIC_LIST = "music_list";

    public ArrayList<String> musicList;

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
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    private void setupRecyclerView(){
        binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));

        MusicAdapter musicAdapter = new MusicAdapter(musicList);

        binding.rvMusicList.setAdapter(musicAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}