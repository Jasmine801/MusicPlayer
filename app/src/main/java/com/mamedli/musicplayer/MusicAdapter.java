package com.mamedli.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    ArrayList<String> musicList;

    public MusicAdapter(ArrayList<String> musicList){
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View musicItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        return new MusicViewHolder(musicItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        String item = musicList.get(position);
        holder.musicItemView.setText(item);
    }


    @Override
    public int getItemCount() {
        return (musicList != null) ? musicList.size() : 0;
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder{
        public TextView musicItemView;
        public MusicViewHolder(View musicItem){
            super(musicItem);
            musicItemView = musicItem.findViewById(R.id.tvMusicName);
        }

    }
}
