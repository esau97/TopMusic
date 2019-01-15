package com.example.esauhp.musicevent.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.TopSongFiltrar;
import com.example.esauhp.musicevent.TopSongList;


public class TopSongFragment extends Fragment implements TopSongFiltrar.OnNameSent {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_song,container,false);
        return view;
    }

    @Override
    public void onChange(String text) {
        TopSongList topSongList = (TopSongList) getChildFragmentManager().findFragmentById(R.id.fragment6);
        if(topSongList!=null){
            topSongList.setTextFiltrar(text);
        }

    }
}
