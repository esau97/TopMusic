package com.example.esauhp.musicevent;

import java.io.Serializable;

public class Album implements Serializable {
    private String nombreArtista;
    private String nombreAlbum;
    private int orden;
    private String urlImage;
    private boolean favorite;
    private String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
    public Album(){

    }
    public  Album(String nombreAlbum){
        this.nombreAlbum=nombreAlbum;
    }

    public Album(String nombreArtista, String nombreAlbum, int orden) {
        this.nombreArtista = nombreArtista;
        this.nombreAlbum = nombreAlbum;
        this.orden = orden;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
