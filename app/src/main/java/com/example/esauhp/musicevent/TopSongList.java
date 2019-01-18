package com.example.esauhp.musicevent;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esauhp.musicevent.Adapter.AlbumAdapter;
import com.example.esauhp.musicevent.Adapter.SongAdapter;
import com.example.esauhp.musicevent.ViewModel.ViewModelSong;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TopSongList extends Fragment implements SongAdapter.OnButtonClickedListener{

    private List<Song> listaSong;
    RecyclerView recyclerView;
    TextView mensaje;
    private ViewModelSong viewModelSong;
    private SongAdapter adapter;
    private String pais;

    public TopSongList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song_list, container, false);
        pais="vacio";
        listaSong = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerSong);
        mensaje = view.findViewById(R.id.textViewEmpty);
        mensaje.setVisibility(View.GONE);
        if(esTablet(getContext())){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        adapter = new SongAdapter(getContext(),listaSong, this);
        recyclerView.setAdapter(adapter);
        mostrarDatos();
        return view;
    }


    public void mostrarDatos() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        boolean conectar= networkInfo!=null && networkInfo.isConnected();
        if(conectar){
            viewModelSong = ViewModelProviders.of(this).get(ViewModelSong.class);
            viewModelSong.getSong(pais).observe(this, new Observer<List<Song>>() {
                @Override
                public void onChanged(@Nullable List<Song> songs) {
                    listaSong.clear();
                    if(songs!=null){
                        mensaje.setVisibility(View.GONE);
                        listaSong.addAll(songs);
                        adapter.notifyDataSetChanged();
                    }else{
                        listaSong.addAll(songs);
                        adapter.notifyDataSetChanged();
                        mensaje.setVisibility(View.VISIBLE);
                        mensaje.setText(R.string.found);
                    }
                }

            });
        }else {
            mensaje.setVisibility(View.VISIBLE);
            mensaje.setText(R.string.internet_connection);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onButtonClicked(View v, Song song) {
        if(v.getId()==R.id.song_list){
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(song.getUrl()));
            startActivity(intent);
        }
    }


    public void setTextFiltrar(String text){
        pais=text;
        mostrarDatos();
    }
    public static boolean esTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
