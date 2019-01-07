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

import com.example.esauhp.musicevent.Album;
import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private Context context;
    private List<Song> songList;

    public SongAdapter(Context context, List<Song> objects) {
        this.context=context;
        this.songList = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder viewHolder, int i) {
        final Song song= songList.get(i);
        viewHolder.orden.setText(i+1+"");
        viewHolder.artistNameSong.setText(song.getArtistName());
        viewHolder.songNameSong.setText(song.getSongName());
        Picasso.get().load(song.getUrlImage()).into(viewHolder.imageSong);

        viewHolder.imagenFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
        viewHolder.imagenFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "La canción ha sido añadida a favoritos", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        if(songList!=null){
            return songList.size();
        }else{
            return 0;
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orden;
        public ImageView imageSong;
        public TextView songNameSong;
        public TextView artistNameSong;
        public ImageView imagenFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orden = (TextView) itemView.findViewById(R.id.ordenSong);
            imageSong = (ImageView) itemView.findViewById(R.id.imagenSong);
            songNameSong = (TextView) itemView.findViewById(R.id.songNameSong);
            artistNameSong = (TextView) itemView.findViewById(R.id.artistNameSong);
            imagenFavorite = (ImageView) itemView.findViewById(R.id.imagenFavoritoSong);

        }

    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Album album);
    }
}
