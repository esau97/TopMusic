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
import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.QueryUtils;

import java.util.List;

public class ViewModelArtist extends AndroidViewModel {
    private static MutableLiveData<List<Artist>> listaArtist;
    private Application application;
    private String apiKey = "ee5a521b423f66ab12730b69b24cbcfb";
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=disco&api_key="+apiKey+"&limit=10&format=json";

    public ViewModelArtist(Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<Artist>> getArtist(){
        if(listaArtist==null){
            listaArtist=new MutableLiveData<>();
            loadArtist();
        }

        return listaArtist;
    }

    private void loadArtist(){

        Uri baseUri = Uri.parse(URL);
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
}
