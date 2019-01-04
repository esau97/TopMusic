package com.example.esauhp.musicevent;

import android.util.Log;

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
            JSONObject root = new JSONObject(result);
            JSONObject albums = root.getJSONObject("albums");
            JSONArray arrayAlbum = albums.getJSONArray("album");

            for (int i = 0; i < arrayAlbum.length(); i++) {

                JSONObject al = arrayAlbum.getJSONObject(i);

                Album album = new Album();
                album.setFavorite(false);
                album.setNombreAlbum(al.getString("name"));
                JSONObject art = al.getJSONObject("artist");
                album.setNombreArtista(art.getString("name"));
                JSONArray photo = al.getJSONArray("image");
                JSONObject p = photo.getJSONObject(1);
                album.setUrlImage(p.getString("#text"));
                listaAlbums.add(album);

            }
            for (int i = 0; i < listaAlbums.size(); i++) {
                Log.i("Prueba",listaAlbums.get(i).getNombreAlbum().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaAlbums;
    }

    public static List<Artist> extraerArtist(String result){
        List<Artist> listaArtist = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(result);
            JSONObject artist = root.getJSONObject("topartists");
            JSONArray arrayArtist = artist.getJSONArray("artist");

            for (int i = 0; i < arrayArtist.length(); i++) {

                JSONObject al = arrayArtist.getJSONObject(i);

                Artist artista = new Artist();
                artista.setFavorite(false);
                artista.setNombreArtista(al.getString("name"));

                JSONArray photo = al.getJSONArray("image");
                JSONObject p = photo.getJSONObject(1);
                artista.setUrlImage(p.getString("#text"));
                listaArtist.add(artista);

            }
            for (int i = 0; i < listaArtist.size(); i++) {
                Log.i("Prueba",listaArtist.get(i).getNombreArtista().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaArtist;
    }
    public static List<Artist> extraerCanciones(String result){
        List<Artist> listaArtist = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(result);
            JSONObject artist = root.getJSONObject("topartists");
            JSONArray arrayArtist = artist.getJSONArray("artist");

            for (int i = 0; i < arrayArtist.length(); i++) {

                JSONObject al = arrayArtist.getJSONObject(i);

                Artist artista = new Artist();
                artista.setFavorite(false);
                artista.setNombreArtista(al.getString("name"));

                /*JSONArray photo = new JSONArray(ob.getJSONArray("image"));
                album.setUrlImage(photo.getString(1));*/
                listaArtist.add(artista);

            }
            for (int i = 0; i < listaArtist.size(); i++) {
                Log.i("Prueba",listaArtist.get(i).getNombreArtista().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaArtist;
    }

}