package com.example.esauhp.musicevent.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.DataBaseRoom.DataBaseRoom;
import com.example.esauhp.musicevent.QueryUtils;
import com.example.esauhp.musicevent.R;

import java.util.List;

public class ViewModelArtist extends AndroidViewModel {
    private static MutableLiveData<List<Artist>> listaArtist;
    private static LiveData<List<Artist>> listaArtistaFavoritos;
    private Application application;

    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=disco&api_key="+getApplication().getString(R.string.apiKey)+"&limit=30&format=json";
    private String URL2 ;
    DataBaseRoom dbRoom;
    private String texto;
    // Constructor donde inicializo los parámetros
    public ViewModelArtist(Application application) {
        super(application);
        this.texto="vacio";
        this.application = application;
        dbRoom = DataBaseRoom.getInstance(application);
        listaArtistaFavoritos = dbRoom.artistDAO().getArtist(); // Obtengo los artistas favoritos de la base de datos
    }
    // Devuelvo la lista con todos los artistas
    public LiveData<List<Artist>> getArtist(String texto){

        if(listaArtist==null || !this.texto.equals(texto)){
            this.texto=texto;
            listaArtist=new MutableLiveData<>(); // Es una clase donde almacenas los datos que se pueden observar
            comprobarPais(); // Llamada al método comprobar país en el que compruebo el país introducido
        }

        return listaArtist;
    }

    // Compruebo si ha introducido algún país en el Edit Text para usar una api u otra
    private void comprobarPais(){

        final String[] pais = new String[1];
        // Compruebo si la variable texto contiene la palabra "vacío" para usar una api u otra
        if(texto.equals("vacio")){
            // Si está vacío llamo directamente al método que me carga los artistas
            loadArtist("vacio");
        }else{
            // Si no está vacío traduzco el texto introducido en el editText al inglés ya que la API funciona en inglés
            Uri base;
            base = Uri.parse("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190118T121608Z.2f292786c0cf1823.aed1e452629c66caf9dac60415df51464ddd9195&text="+texto+"&lang=es-en");
            Uri.Builder uriBuild = base.buildUpon();
            final RequestQueue requ= Volley.newRequestQueue(application);
            StringRequest request=new StringRequest(Request.Method.GET, uriBuild.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pais[0] = QueryUtils.extraerPais(response);
                    // Una vez obtenido el nombre del país en inglés llamamos al método cargar artistas
                    loadArtist(pais[0]);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error ViewModel",error.getMessage());
                }
            });
            requ.add(request);


        }

    }

    public LiveData<List<Artist>> getArtistFav(){
        return  listaArtistaFavoritos;
    } // Devuelvo una lista con los artistas Favoritos
    public void addArtist(Artist artist){
        new AsyncAddArtist().execute(artist);
    } // Llamo a la clase asincrona para guardar los artistas en la base de datos
    public void deleteArtist(Artist artist){
        new DeleteAsyncArtist().execute(artist);
    } // Llamo a la clase asincrona para eliminar a los artistas


    public void loadArtist(String paisBusqueda){
        Uri baseUri;
        // Vuelvo a comprobar si el texto está vacío para usar una API u otra
        if(texto.equals("vacio")){
            // Parseo el String
            baseUri= Uri.parse(URL);
        }else {
            URL2 = "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=" + paisBusqueda + "&api_key=" + application.getString(R.string.apiKey) + "&limit=30&format=json";
            baseUri = Uri.parse(URL2);
        }
        Uri.Builder uriBuilder = baseUri.buildUpon();
        final RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request=new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Artist>lista=QueryUtils.extraerArtist(response);
                listaArtist.setValue(lista);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error ViewModel",error.getMessage());
            }
        });
        requestQueue.add(request);



    }

    // Clase asincrona encargada de añadir los artistas a la base de datos
    private class AsyncAddArtist extends AsyncTask<Artist,Void,Long> {
        Artist artist;

        @Override
        protected Long doInBackground(Artist... artists) {
            long id = -1;


            if (artists.length!=0) {
                String name = artists[0].getNombreArtista();
                Log.d("Artist",name);
                artist = artists[0];

                id = dbRoom.artistDAO().insertArtist(artists[0]);
                Log.d("Artist", String.valueOf(id));
                artist.setId(id);

            }
            return id;
        }

        @Override
        protected void onPostExecute(Long id){
            if(id==-1){
                Toast.makeText(getApplication(),"Error al añadir artista", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplication(), "Artista favorito añadido", Toast.LENGTH_SHORT).show();
            }
        }

    }
    // Clase asincrona encargada de eliminar los artistas a la base de datos
    private class DeleteAsyncArtist extends AsyncTask<Artist,Void,Void>{
        @Override
        protected Void doInBackground(Artist... artists){
            dbRoom.artistDAO().deleteArtist(artists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplication(), "Artista favorito eliminado", Toast.LENGTH_SHORT).show();
        }
    }

}
