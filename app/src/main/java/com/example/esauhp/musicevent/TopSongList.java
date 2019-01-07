package com.example.esauhp.musicevent;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    Activity activity;

    public TopSongList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song_list, container, false);

        listaSong = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerSong);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SongAdapter(getContext(),listaSong);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        boolean conectar= networkInfo!=null && networkInfo.isConnected();
        if(conectar){
            viewModelSong = ViewModelProviders.of(this).get(ViewModelSong.class);
            viewModelSong.getSong().observe(this, new Observer<List<Song>>() {
                @Override
                public void onChanged(@Nullable List<Song> songs) {
                    listaSong.clear();
                    if(songs!=null){
                        listaSong.addAll(songs);
                        adapter.notifyDataSetChanged();
                    }else{
                        mensaje.setText("No se ha encontrado ninguna canción");
                    }
                }

            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onButtonClicked(View v, Album album) {
        if(album.isFavorite()){
            ImageView imageView = v.findViewById(R.id.imagenFavoritoAlbum);
            imageView.setImageResource(R.drawable.ic_star_border_white_24dp);
            album.setFavorite(false);
        }else {
            ImageView imageView = v.findViewById(R.id.imagenFavoritoAlbum);
            imageView.setImageResource(R.drawable.ic_star_white_24dp);
            album.setFavorite(true);
        }
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(Uri uri);
    }



}
