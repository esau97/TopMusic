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

import com.example.esauhp.musicevent.Adapter.TopArtistAdapter;
import com.example.esauhp.musicevent.ViewModel.ViewModelArtist;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class TopArtistList extends Fragment implements TopArtistAdapter.OnButtonClickedListener{
    private List<Artist> listaArtist;
    RecyclerView recyclerView;
    TextView mensaje;
    private ViewModelArtist viewModelArtist;
    private TopArtistAdapter adapter;

    public TopArtistList() {
    }

    Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_artist_list, container, false);

        listaArtist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerArtist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TopArtistAdapter(getContext(),listaArtist);
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
            viewModelArtist = ViewModelProviders.of(this).get(ViewModelArtist.class);
            viewModelArtist.getArtist().observe(this, new Observer<List<Artist>>() {
                @Override
                public void onChanged(@Nullable List<Artist> artist) {
                    listaArtist.clear();
                    if(artist!=null){
                        listaArtist.addAll(artist);
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


    @Override
    public void onButtonClicked(View v, Artist artist) {
        if(artist.isFavorite()){
            ImageView imageView = v.findViewById(R.id.imagenFavoritoArtist);
            imageView.setImageResource(R.drawable.ic_star_border_white_24dp);
            artist.setFavorite(false);
        }else {
            ImageView imageView = v.findViewById(R.id.imagenFavoritoArtist);
            imageView.setImageResource(R.drawable.ic_star_white_24dp);
            artist.setFavorite(true);
        }
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(Uri uri);
    }
}
