package com.example.esauhp.musicevent;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.example.esauhp.musicevent.Adapter.AlbumAdapter;
import com.example.esauhp.musicevent.ViewModel.ViewModelAlbum;

import java.util.ArrayList;
import java.util.List;

public class TopAlbumsList extends Fragment implements AlbumAdapter.OnButtonClickedListener {


    private List<Album> listaAlbum;
    RecyclerView recyclerView;
    TextView mensaje;
    private ViewModelAlbum viewModelAlbum;
    private AlbumAdapter adapter;

    Activity activity;

    public TopAlbumsList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_albums_recycler, container, false);

        listaAlbum = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerAlbums);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AlbumAdapter(getContext(),listaAlbum);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModelAlbum = ViewModelProviders.of(this).get(ViewModelAlbum.class);
        viewModelAlbum.getAlbum().observe(this, new Observer<List<Album>>() {
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
        /*
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        boolean conectar= networkInfo!=null && networkInfo.isConnected();
        if(conectar){
            viewModelAlbum = ViewModelProviders.of(this).get(ViewModelAlbum.class);
            viewModelAlbum.getAlbum().observe(this, new Observer<List<Album>>() {
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
        }*/
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
