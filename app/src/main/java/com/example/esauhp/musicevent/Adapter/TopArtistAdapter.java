package com.example.esauhp.musicevent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.R;
import com.squareup.picasso.Picasso;

import java.util.List;
//RecyclerView.Adapter<AlbumAdapter.ViewHolder>

public class TopArtistAdapter extends RecyclerView.Adapter<TopArtistAdapter.ViewHolder> {
    private Context context;
    private List<Artist> artistList;
    private OnButtonClickedListener listener;

    public TopArtistAdapter(Context context, List<Artist> objects) {
        this.context=context;
        this.artistList = objects;
    }
    public TopArtistAdapter(Context context, List<Artist> objects, OnButtonClickedListener listener) {
        this.context=context;
        this.artistList = objects;
        this.listener=listener;
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

        Picasso.get().load(artist.getUrlImage()).into(viewHolder.imageArtist);
        String artistas = artist.getNombreArtista();
        if(artistas.length()>22){
            artistas = artistas.substring(0,22);
            artistas = artistas +"...";
        }
        viewHolder.nameArtist.setText(artistas);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,artist);
            }
        });
        if(artist.isFavorite()){
            viewHolder.imagenFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        }else {
            viewHolder.imagenFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
        viewHolder.imagenFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,artist);
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
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.artist_list);
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
