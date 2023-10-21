package com.mamedli.musicplayer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
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

    private static ViewModelStoreOwner viewModelStoreOwner;
    private MusicViewModel musicViewModel;

    private ArrayList<String> musicList;

    public MusicFragment() {
        // Инициализировать musicList
        musicList = new ArrayList<String>();
    }

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

        viewModelStoreOwner = requireActivity();
        musicViewModel = new ViewModelProvider(viewModelStoreOwner).get(MusicViewModel.class);

        if (getArguments() != null) {
            musicViewModel.setMusicList(getArguments().getStringArrayList(MUSIC_LIST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMusicBinding.inflate(inflater, container, false);

        //setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setupRecyclerView();

        musicViewModel.getMusicList().observe(getViewLifecycleOwner(), musicList -> {
            // Обновить список музыки
            binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvMusicList.setAdapter(new MusicAdapter(musicList));
        });
    }

    private void setupRecyclerView(){
        MusicAdapter musicAdapter = new MusicAdapter(musicList);
        musicAdapter.notifyDataSetChanged();
        binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMusicList.setAdapter(musicAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}