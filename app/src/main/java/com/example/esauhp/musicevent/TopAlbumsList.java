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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.esauhp.musicevent.Adapter.AlbumAdapter;
import com.example.esauhp.musicevent.ViewModel.ViewModelAlbum;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.CONNECTIVITY_SERVICE;


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

        View view = inflater.inflate(R.layout.fragment_top_albums_recycler, container, false);
        artista="vacio";
        listaAlbum = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerAlbums);
        mensaje = view.findViewById(R.id.textViewEmpty);
        mensaje.setVisibility(View.GONE);
        if(esTablet(getContext())){ // Llamo al metodo esTablet que me devuelve un booleano y me indica si nos encontramos en una tablet o no
            // Si es tablet creamos un gridLayout para mostrar la lista dividida en dos
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else{
            // Si estamos en el móvil creamos un Linear y mostramos una sola lista vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        // Inicializo el adapter
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
            // Se instancia el ViewModel
            viewModelAlbum = ViewModelProviders.of(this).get(ViewModelAlbum.class);
            // Observamos si se han producido cambios
            viewModelAlbum.getAlbum(artista).observe(this, new Observer<List<Album>>() {
                @Override
                public void onChanged(@Nullable List<Album> albums) {
                    // Limpiamos la lista
                    listaAlbum.clear();
                    // Comprobamos si la lista que recibe por parámetros no es nula y
                    if(albums!=null && albums.size()!=0){

                        mensaje.setVisibility(View.GONE);
                        // Añado a la lista los albumes
                        listaAlbum.addAll(albums);
                        adapter.notifyDataSetChanged();
                    }else{
                        // Si es nula, añado a la lista para que esté vacía y muestro un mensaje en el que indico que no se encuentra
                        listaAlbum.addAll(albums);
                        adapter.notifyDataSetChanged();
                        mensaje.setVisibility(View.VISIBLE);
                        mensaje.setText(R.string.found);
                    }
                }

            });
        }else{
            // Si no tiene conexión a internet muestro un mensaje que indica que no tiene conexión a internet
            mensaje.setVisibility(View.VISIBLE);
            mensaje.setText(R.string.internet_connection);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //
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
