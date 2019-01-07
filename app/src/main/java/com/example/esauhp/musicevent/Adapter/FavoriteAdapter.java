package com.example.esauhp.musicevent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.R;
import com.squareup.picasso.Picasso;

import java.util.List;
//RecyclerView.Adapter<AlbumAdapter.ViewHolder>

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    private Context context;
    private List<Artist> artistList;
    private FavoriteAdapter.OnButtonClickedListener listener;

    public FavoriteAdapter(Context context, List<Artist> objects, OnButtonClickedListener listener) {
        this.context=context;
        this.artistList = objects;
        this.listener=listener;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_favorite_list, viewGroup, false);
        FavoriteAdapter.ViewHolder viewHolder = new FavoriteAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder viewHolder, int i) {
        final Artist artist = artistList.get(i);
        viewHolder.nameArtistFavorite.setText(artist.getNombreArtista());
        Picasso.get().load(artist.getUrlImage()).into(viewHolder.imageArtist);
        //viewHolder.imagenFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        /*viewHolder.imagenFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "El artista ha sido añadido a favorito", Toast.LENGTH_SHORT).show();

            }
        });*/
    }


    @Override
    public int getItemCount() {
        if(artistList!=null){
            return artistList.size();
        }else{
            return 0;
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageArtist;
        public TextView nameArtistFavorite;
        public ImageView imagenFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageArtist = (ImageView) itemView.findViewById(R.id.imageArtistFavorite);
            nameArtistFavorite = (TextView) itemView.findViewById(R.id.nameArtistFavorite);
            imagenFavorite = (ImageView) itemView.findViewById(R.id.imagenFavoritoArtist);

        }

    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Artist artist);
    }
}
