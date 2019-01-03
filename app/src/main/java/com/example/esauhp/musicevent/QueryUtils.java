package com.example.esauhp.musicevent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public QueryUtils(){}

    public static List<Album> extraerAlbums(String result){
        List<Album> listaAlbums = new ArrayList<>();
        try{
            JSONArray albums = new JSONArray(result);

            for (int i = 0; i < albums.length(); i++) {
                JSONObject ob = albums.getJSONObject(i);
                Album album = new Album();
                album.setFavorite(false);
                album.setNombreAlbum(ob.getString("name"));
                JSONObject art = ob.getJSONObject("artist");
                album.setNombreArtista(art.getString("name"));
                JSONArray photo = new JSONArray(ob.getJSONArray("image"));
                album.setUrlImage(photo.getString(1));
                listaAlbums.add(album);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaAlbums;
    }
}
