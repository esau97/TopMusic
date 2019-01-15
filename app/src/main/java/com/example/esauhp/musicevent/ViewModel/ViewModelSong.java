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
import com.example.esauhp.musicevent.QueryUtils;
import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.Song;

import java.util.List;

public class ViewModelSong extends AndroidViewModel {
    private static MutableLiveData<List<Song>> listaSong;
    private Application application;
    private String apiKey = "ee5a521b423f66ab12730b69b24cbcfb";
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=disco&api_key="+getApplication().getString(R.string.apiKey)+"&limit=10&format=json";
    private String URL2;
    private String texto;

    public ViewModelSong(Application application) {
        super(application);
        this.texto="vacio";
        this.application = application;
    }

    public LiveData<List<Song>> getSong(String texto){

        if(listaSong==null || !this.texto.equals(texto)){
            this.texto=texto;
            listaSong=new MutableLiveData<>();
            loadSongs();
        }

        return listaSong;
    }

    private void loadSongs(){
        Uri baseUri;
        if(texto.equals("vacio")){
            baseUri= Uri.parse(URL);
        }else{
            URL2="http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country="+texto+"&api_key="+application.getString(R.string.apiKey)+"&limit=10&format=json";
            baseUri = Uri.parse(URL2);
        }

        Uri.Builder uriBuilder = baseUri.buildUpon();
        final RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request=new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Song>lista=QueryUtils.extraerSongs(response);
                listaSong.setValue(lista);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error ViewModel",error.getMessage());
            }
        });
        requestQueue.add(request);

    }

}
