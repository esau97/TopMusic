package com.example.esauhp.musicevent.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.TopAlbumsFiltrar;
import com.example.esauhp.musicevent.TopAlbumsList;


public class TopAlbumFragment extends Fragment implements TopAlbumsFiltrar.OnNameSent{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_albums,container,false);
        return view;
    }

    @Override
    public void onChange(String text) {
        TopAlbumsList topAlbumsList= (TopAlbumsList) getChildFragmentManager().findFragmentById(R.id.fragment2);
        if(topAlbumsList!=null){
            topAlbumsList.setTextFiltrar(text);
        }

    }
}
