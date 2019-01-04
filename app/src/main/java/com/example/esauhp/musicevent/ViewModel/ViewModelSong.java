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
import com.example.esauhp.musicevent.Song;

import java.util.List;

public class ViewModelSong extends AndroidViewModel {
    private static MutableLiveData<List<Song>> listaSong;
    private Application application;
    private String apiKey = "ee5a521b423f66ab12730b69b24cbcfb";
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=disco&api_key="+apiKey+"&limit=10&format=json";

    public ViewModelSong(Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<Song>> getSong(){
        if(listaSong==null){
            listaSong=new MutableLiveData<>();
            loadSongs();
        }

        return listaSong;
    }

    private void loadSongs(){

        Uri baseUri = Uri.parse(URL);
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
