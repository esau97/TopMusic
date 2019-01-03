package com.example.esauhp.musicevent.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.esauhp.musicevent.Album;
import com.example.esauhp.musicevent.QueryUtils;

import java.util.List;

public class ViewModelAlbum extends AndroidViewModel {
    private static MutableLiveData<List<Album>> listaAlbum;
    private Application application;
    private String apiKey = "ee5a521b423f66ab12730b69b24cbcfb";
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettopalbums&tag=disco&api_key="+apiKey+"&limit=10&format=json";

    public ViewModelAlbum(Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<Album>> getAlbum(){
        if(listaAlbum==null){
            listaAlbum=new MutableLiveData<>();
            loadAlbums();
        }

        return listaAlbum;
    }

    private void loadAlbums(){

        final RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Album>lista=QueryUtils.extraerAlbums(response);
                listaAlbum.setValue(lista);
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
