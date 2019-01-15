package com.example.esauhp.musicevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TopSongFiltrar extends Fragment {

    OnNameSent callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_song_filtrar, container, false);
        // Inflate the layout for this fragment
        final EditText editText = view.findViewById(R.id.editTextFiltrarSong);

        final ImageButton imageButton1 = view.findViewById(R.id.imageBuscarSong);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().equals("")){
                    String valor = "vacio";

                    callback.onChange(valor);
                    Toast.makeText(getContext(),"Vacío", Toast.LENGTH_SHORT).show();
                }else{
                    String valor = editText.getText().toString();

                    callback.onChange(valor);
                    Toast.makeText(getContext(),editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }



            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnNameSent) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Debería implementar el interfaz OnNameSent");
        }
    }
    public interface OnNameSent {
        public void onChange(String text);
    }

}
