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
    private String URL = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=disco&api_key="+getApplication().getString(R.string.apiKey)+"&limit=30&format=json";
    private String URL2;
    private String texto;
    private String pais;

    public ViewModelSong(Application application) {
        super(application);
        this.texto="vacio";
        this.application = application;
        this.pais="";
    }

    public LiveData<List<Song>> getSong(String texto){

        if(listaSong==null || !this.texto.equals(texto)){
            this.texto=texto;
            listaSong=new MutableLiveData<>();
            traducirPais();
        }

        return listaSong;
    }

    private void loadSongs(String pais){
        Uri baseUri;
        if(texto.equals("vacio")){
            baseUri= Uri.parse(URL);
        }else{
            URL2="http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country="+pais+"&api_key="+application.getString(R.string.apiKey)+"&limit=30&format=json";
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
    private void traducirPais(){
        final String[] pais = new String[1];
        if(texto.equals("vacio")){
            loadSongs("vacio");
        }else{
            Uri base;
            base = Uri.parse("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190118T121608Z.2f292786c0cf1823.aed1e452629c66caf9dac60415df51464ddd9195&text="+texto+"&lang=es-en");
            Uri.Builder uriBuild = base.buildUpon();
            final RequestQueue requ= Volley.newRequestQueue(application);
            StringRequest request=new StringRequest(Request.Method.GET, uriBuild.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pais[0] = QueryUtils.extraerPais(response);
                    loadSongs(pais[0]);
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

}
