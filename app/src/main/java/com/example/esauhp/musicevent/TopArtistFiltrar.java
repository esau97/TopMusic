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


public class TopArtistFiltrar extends Fragment  {

    OnNameSent callback;
    private boolean toggle;

    public static String api="ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=disco&api_key=ee5a521b423f66ab12730b69b24cbcfb&limit=10&format=json";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_artist_filtrar, container, false);
        api="ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=disco&api_key="+getString(R.string.apiKey)+"&limit=10&format=json";

        final EditText editText = view.findViewById(R.id.editTextFiltrarArtist);

        final ImageButton imageButton1 = view.findViewById(R.id.imageBuscarArtist);
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
