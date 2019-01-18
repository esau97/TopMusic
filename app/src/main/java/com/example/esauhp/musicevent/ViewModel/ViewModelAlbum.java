package com.example.esauhp.musicevent.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.esauhp.musicevent.Album;
import com.example.esauhp.musicevent.QueryUtils;
import com.example.esauhp.musicevent.R;

import java.util.List;



public class ViewModelAlbum extends AndroidViewModel {
    private static MutableLiveData<List<Album>> listaAlbum;
    private Application application;
    private String apiKey = "ee5a521b423f66ab12730b69b24cbcfb";
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettopalbums&tag=disco&api_key="+apiKey+"&limit=30&format=json";
    private String URL2 ;
    private String texto;


    public ViewModelAlbum(Application application) {
        super(application);
        this.texto="vacio";
        this.application = application;

    }

    public LiveData<List<Album>> getAlbum(String texto){

        if(listaAlbum==null || !this.texto.equals(texto)){
            this.texto=texto;
            listaAlbum=new MutableLiveData<>();
            loadAlbums();
        }

        return listaAlbum;
    }

    private void loadAlbums(){
        Uri baseUri;


        if(texto.equals("vacio")){
            baseUri= Uri.parse(URL);
        }else{

            URL2="http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist="+texto+"&api_key="+application.getString(R.string.apiKey)+"&limit=30&format=json";
            baseUri = Uri.parse(URL2);
        }

        Uri.Builder uriBuilder = baseUri.buildUpon();
        final RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request=new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Album>lista=QueryUtils.extraerAlbums(response,texto);
                listaAlbum.setValue(lista);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ViewModel",error.getMessage());
            }
        });
        requestQueue.add(request);

    }

}
