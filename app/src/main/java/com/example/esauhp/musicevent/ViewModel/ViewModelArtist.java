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
    private String apiKey = getApplication().getString(R.string.apiKey);
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=disco&api_key="+getApplication().getString(R.string.apiKey)+"&limit=10&format=json";
    private String URL2 ;
    DataBaseRoom dbRoom;
    private String texto;

    public ViewModelArtist(Application application) {
        super(application);
        this.texto="vacio";
        this.application = application;
        dbRoom = DataBaseRoom.getInstance(application);
        listaArtistaFavoritos = dbRoom.artistDAO().getArtist();
    }

    public LiveData<List<Artist>> getArtist(String texto){

        if(listaArtist==null || !this.texto.equals(texto)){
            this.texto=texto;
            listaArtist=new MutableLiveData<>();
            loadArtist();
        }

        return listaArtist;
    }

    private void loadArtist(){
        Uri baseUri;
        if(texto.equals("vacio")){
            baseUri= Uri.parse(URL);
        }else{
            URL2="http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country="+texto+"&api_key="+application.getString(R.string.apiKey)+"&limit=10&format=json";
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
    public LiveData<List<Artist>> getArtistFav(){
        return  listaArtistaFavoritos;
    }
    public void addArtist(Artist artist){
        new AsyncAddArtist().execute(artist);
    }
    public void deleteArtist(Artist artist){
        new DeleteAsyncArtist().execute(artist);
    }


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
