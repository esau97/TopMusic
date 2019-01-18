package com.example.esauhp.musicevent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.esauhp.musicevent.Album;
import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    private Context context;
    private List<Album> albumList;
    private OnButtonClickedListener listener;

    public AlbumAdapter(Context context, List<Album> objects) {
        this.context=context;
        this.albumList = objects;
    }
    public AlbumAdapter(Context context, List<Album> objects, OnButtonClickedListener listener) {
        this.context=context;
        this.albumList = objects;
        this.listener=listener;
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
        String albumes = album.getNombreAlbum();
        if(albumes.length()>22){
            albumes = albumes.substring(0,22);
            albumes = albumes +"...";
        }
        viewHolder.nameAlbum.setText(albumes);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,album);
            }
        });
        if(album.getUrlImage().equals("")){
            viewHolder.imageAlbum.setImageResource(R.drawable.auricularesinterrogacion);

        }else{
            Picasso.get().load(album.getUrlImage()).into(viewHolder.imageAlbum);
        }


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
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.album_list);
            orden = (TextView) itemView.findViewById(R.id.ordenAlbum);
            imageAlbum = (ImageView) itemView.findViewById(R.id.imagenAlbum);
            nameAlbum = (TextView) itemView.findViewById(R.id.albumNameAlbum);
            nameArtist = (TextView) itemView.findViewById(R.id.artistNameAlbum);


        }

    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Album album);
    }

}
