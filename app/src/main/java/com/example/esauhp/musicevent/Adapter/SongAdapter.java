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
import com.example.esauhp.musicevent.R;
import com.example.esauhp.musicevent.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private Context context;
    private List<Song> songList;
    private OnButtonClickedListener listener;

    public SongAdapter(Context context, List<Song> objects) {
        this.context=context;
        this.songList = objects;
    }
    public SongAdapter(Context context, List<Song> objects, OnButtonClickedListener listener) {
        this.context=context;
        this.songList = objects;
        this.listener=listener;
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
        String artistas = song.getArtistName();
        if(artistas.length()>22){
            artistas = artistas.substring(0,22);
            artistas = artistas +"...";
        }
        viewHolder.artistNameSong.setText(artistas);
        String songs = song.getSongName();
        if(songs.length()>22){
            songs = songs.substring(0,22);
            songs = songs +"...";
        }
        viewHolder.songNameSong.setText(songs);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,song);
            }
        });
        Picasso.get().load(song.getUrlImage()).into(viewHolder.imageSong);

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
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.song_list);
            orden = (TextView) itemView.findViewById(R.id.ordenSong);
            imageSong = (ImageView) itemView.findViewById(R.id.imagenSong);
            songNameSong = (TextView) itemView.findViewById(R.id.songNameSong);
            artistNameSong = (TextView) itemView.findViewById(R.id.artistNameSong);

        }

    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Song song);
    }
}
