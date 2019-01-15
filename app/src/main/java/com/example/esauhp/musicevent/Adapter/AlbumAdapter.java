package com.example.esauhp.musicevent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esauhp.musicevent.Album;
import com.example.esauhp.musicevent.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    private Context context;
    private List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> objects) {
        this.context=context;
        this.albumList = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder viewHolder, int i) {
        final Album album = albumList.get(i);
        viewHolder.orden.setText(i+1+"");
        viewHolder.nameArtist.setText(album.getNombreArtista());
        viewHolder.nameAlbum.setText(album.getNombreAlbum());
        Picasso.get().load(album.getUrlImage()).into(viewHolder.imageAlbum);

    }


    @Override
    public int getItemCount() {
        if(albumList!=null){
            return albumList.size();
        }else{
            return 0;
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orden;
        public ImageView imageAlbum;
        public TextView nameAlbum;
        public TextView nameArtist;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orden = (TextView) itemView.findViewById(R.id.ordenAlbum);
            imageAlbum = (ImageView) itemView.findViewById(R.id.imagenAlbum);
            nameAlbum = (TextView) itemView.findViewById(R.id.albumNameAlbum);
            nameArtist = (TextView) itemView.findViewById(R.id.artistNameAlbum);


        }

    }

}
