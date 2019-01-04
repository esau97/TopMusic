package com.example.esauhp.musicevent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.R;
import com.squareup.picasso.Picasso;

import java.util.List;
//RecyclerView.Adapter<AlbumAdapter.ViewHolder>

public class TopArtistAdapter extends RecyclerView.Adapter<TopArtistAdapter.ViewHolder>{
    private Context context;
    private List<Artist> artistList;

    public TopArtistAdapter(Context context, List<Artist> objects) {
        this.context=context;
        this.artistList = objects;
    }

    @NonNull
    @Override
    public TopArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_list, viewGroup, false);
        TopArtistAdapter.ViewHolder viewHolder = new TopArtistAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull TopArtistAdapter.ViewHolder viewHolder, int i) {
        final Artist artist = artistList.get(i);
        viewHolder.orden.setText(i+1+"");
        viewHolder.nameArtist.setText(artist.getNombreArtista());
        Picasso.get().load(artist.getUrlImage()).into(viewHolder.imageArtist);
        viewHolder.imagenFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        viewHolder.imagenFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "El álbum ha sido añadida a favoritos", Toast.LENGTH_SHORT).show();

            }
        });
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

        public TextView orden;
        public ImageView imageArtist;
        public TextView nameArtist;
        public ImageView imagenFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orden = (TextView) itemView.findViewById(R.id.ordenArtist);
            imageArtist = (ImageView) itemView.findViewById(R.id.imagenArtist);
            nameArtist = (TextView) itemView.findViewById(R.id.artistNameArtist);
            imagenFavorite = (ImageView) itemView.findViewById(R.id.imagenFavoritoArtist);

        }

    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Artist artist);
    }
}
