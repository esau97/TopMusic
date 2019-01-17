package com.example.esauhp.musicevent;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.esauhp.musicevent.DataBaseRoom.DataBaseRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QueryUtils {
    public static DataBaseRoom dbr;
    public static Context context;
    public QueryUtils(){}

    public static List<Album> extraerAlbums(String result,String artista){
        List<Album> listaAlbums = new ArrayList<>();
        Log.i("Metodo mostrar datos",artista);
        if(artista.equals("vacio")){
            try{
                JSONObject root = new JSONObject(result);
                JSONObject albums = root.getJSONObject("albums");
                JSONArray arrayAlbum = albums.getJSONArray("album");

                for (int i = 0; i < arrayAlbum.length(); i++) {

                    JSONObject al = arrayAlbum.getJSONObject(i);

                    Album album = new Album();
                    album.setFavorite(false);
                    album.setNombreAlbum(al.getString("name"));
                    album.setUrl(al.getString("url"));
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

        }else {
            try{
                JSONObject root = new JSONObject(result);
                JSONObject albums = root.getJSONObject("topalbums");
                JSONArray arrayAlbum = albums.getJSONArray("album");

                for (int i = 0; i < arrayAlbum.length(); i++) {

                    JSONObject al = arrayAlbum.getJSONObject(i);

                    Album album = new Album();
                    album.setFavorite(false);
                    album.setNombreAlbum(al.getString("name"));
                    album.setUrl(al.getString("url"));
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
        }

        return listaAlbums;
    }

    public static List<Artist> extraerArtist(String result){
        dbr= DataBaseRoom.getInstance(context);
        List<Artist> listaArtist = new ArrayList<>();
        String name, url, image;


        try{
            JSONObject root = new JSONObject(result);
            JSONObject artist = root.getJSONObject("topartists");
            JSONArray arrayArtist = artist.getJSONArray("artist");

            for (int i = 0; i < arrayArtist.length(); i++) {

                JSONObject al = arrayArtist.getJSONObject(i);
                name =(al.getString("name"));
                url =(al.getString("url"));
                JSONArray photo = al.getJSONArray("image");
                JSONObject p = photo.getJSONObject(1);
                image =(p.getString("#text"));
                Artist artista = new ComprobarFav().execute(name).get();
                if(artista==null){
                    listaArtist.add(new Artist(image,name,url,false));
                }else {
                    listaArtist.add(artista);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return listaArtist;
    }

    public static List<Song> extraerSongs(String result){
        List<Song> listaSong = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(result);
            JSONObject artist = root.getJSONObject("tracks");
            JSONArray arrayArtist = artist.getJSONArray("track");

            for (int i = 0; i < arrayArtist.length(); i++) {

                JSONObject al = arrayArtist.getJSONObject(i);
                Song song = new Song();
                song.setFavorite(false);
                song.setSongName(al.getString("name"));
                song.setUrl(al.getString("url"));
                JSONObject nameArtist = al.getJSONObject("artist");
                song.setArtistName(nameArtist.getString("name"));
                JSONArray photo = al.getJSONArray("image");
                JSONObject p = photo.getJSONObject(1);
                song.setUrlImage(p.getString("#text"));
                listaSong.add(song);

            }
            for (int i = 0; i < listaSong.size(); i++) {
                Log.i("Prueba",listaSong.get(i).getSongName().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaSong;
    }

    private static class ComprobarFav extends AsyncTask<String,Void, Artist> {
        @Override
        protected Artist doInBackground(String... strings) {
            Artist artist = dbr.artistDAO().getArtistFav(strings[0]);
            return artist;
        }
    }

}
