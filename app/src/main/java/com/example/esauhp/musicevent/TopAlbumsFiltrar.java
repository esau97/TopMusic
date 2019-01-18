package com.example.esauhp.musicevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class TopAlbumsFiltrar extends Fragment {

    OnNameSent callback;
    String artista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_top_albums_filtrar, container, false);
        final EditText editText = view.findViewById(R.id.editTextFiltrarAlbums);

        final ImageButton imageButton1 = view.findViewById(R.id.imageBuscarAlbum);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().equals("")){
                    String valor = "vacio";
                    callback.onChange(valor);
                }else{
                    String valor = editText.getText().toString();
                    callback.onChange(valor);
                    Toast.makeText(getContext(),editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (TopAlbumsFiltrar.OnNameSent) getParentFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Debería implementar el interfaz OnNameSent");
        }
    }

    public interface OnNameSent {
        public void onChange(String text);
    }


}
