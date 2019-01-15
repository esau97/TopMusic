package com.example.esauhp.musicevent;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esauhp.musicevent.Adapter.AlbumAdapter;
import com.example.esauhp.musicevent.ViewModel.ViewModelAlbum;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TopAlbumsList extends Fragment {


    private List<Album> listaAlbum;
    RecyclerView recyclerView;
    TextView mensaje;
    private ViewModelAlbum viewModelAlbum;
    private AlbumAdapter adapter;
    private String artista;
    Activity activity;

    public TopAlbumsList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_albums_recycler, container, false);
        artista="vacio";
        listaAlbum = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerAlbums);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AlbumAdapter(getContext(),listaAlbum);
        recyclerView.setAdapter(adapter);
        mostrarDatos();
        return view;
    }


    public void mostrarDatos() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        boolean conectar= networkInfo!=null && networkInfo.isConnected();
        if(conectar){
            viewModelAlbum = ViewModelProviders.of(this).get(ViewModelAlbum.class);

            viewModelAlbum.getAlbum(artista).observe(this, new Observer<List<Album>>() {
                @Override
                public void onChanged(@Nullable List<Album> albums) {
                    listaAlbum.clear();
                    if(albums!=null){
                        listaAlbum.addAll(albums);
                        adapter.notifyDataSetChanged();
                    }else{
                        mensaje.setText("No se ha encontrado ningún álbum");
                    }
                }

            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setTextFiltrar(String text){
        artista=text;
        Log.i("Metodo coger artista",artista);
        mostrarDatos();
    }


}
