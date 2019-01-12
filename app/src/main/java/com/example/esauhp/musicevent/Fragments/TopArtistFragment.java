package com.example.esauhp.musicevent.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.TopArtistFiltrar;
import com.example.esauhp.musicevent.TopArtistList;


public class TopArtistFragment extends Fragment implements TopArtistFiltrar.OnNameSent {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_artist,container,false);

        return view;
    }

    @Override
    public void onChange(String text) {
        TopArtistList topArtistList = (TopArtistList) getChildFragmentManager().findFragmentById(R.id.fragment4);
        if(topArtistList!=null){
            topArtistList.setTextFiltrar(text);
        }
        int num=2;
    }
}

