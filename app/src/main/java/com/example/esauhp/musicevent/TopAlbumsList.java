package com.example.esauhp.musicevent;


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
import com.example.esauhp.musicevent.ViewModel.ViewModelAlbum;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.view.View.GONE;

public class TopAlbumsList extends Fragment implements AlbumAdapter.OnButtonClickedListener{


    private List<Album> listaAlbum;
    RecyclerView recyclerView;
    TextView mensaje;
    private ViewModelAlbum viewModelAlbum;
    private AlbumAdapter adapter;
    private String artista;

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
        mensaje = view.findViewById(R.id.textViewEmpty);
        mensaje.setVisibility(View.GONE);
        if(esTablet(getContext())){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


        adapter = new AlbumAdapter(getContext(),listaAlbum,this);
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
                    if(albums!=null && albums.size()!=0){
                        mensaje.setVisibility(View.GONE);
                        listaAlbum.addAll(albums);
                        adapter.notifyDataSetChanged();
                    }else{
                        listaAlbum.addAll(albums);
                        adapter.notifyDataSetChanged();
                        mensaje.setVisibility(View.VISIBLE);
                        mensaje.setText(R.string.found);
                    }
                }

            });
        }else{
            mensaje.setVisibility(View.VISIBLE);
            mensaje.setText(R.string.internet_connection);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setTextFiltrar(String text){
        artista=text;
        mostrarDatos();
    }


    @Override
    public void onButtonClicked(View v, Album album) {
        if(v.getId()==R.id.album_list){
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(album.getUrl()));
            startActivity(intent);
        }
    }
    public static boolean esTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
