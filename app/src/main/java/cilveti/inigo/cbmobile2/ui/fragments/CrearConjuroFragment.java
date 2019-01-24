package cilveti.inigo.cbmobile2.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.constants.Constants;
import cilveti.inigo.cbmobile2.models.Conjuro;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearConjuroFragment extends Fragment {
    TextInputEditText et_nombre;
    EditText et_descripcion;
    Button guardarButton;

    public CrearConjuroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_crear_conjuro, container, false);
        et_nombre = rootView.findViewById(R.id.et_nombre);
        et_descripcion = rootView.findViewById(R.id.et_descripcion);
        guardarButton = rootView.findViewById(R.id.btn_guardar);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_descripcion.getText()!=null && et_nombre.getText()!=null && !et_nombre.getText().toString().equals("") && !et_descripcion.getText().toString().equals("")){

                    final String descripcion = et_descripcion.getText().toString();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(descripcion);
                    Conjuro conjuro = new Conjuro(et_nombre.getText().toString(), list );

                    Intent intent = new Intent();
                    intent.putExtra("conjuro", new Gson().toJson(conjuro));
                    getActivity().setResult(Constants.RESULT_CODE_NUEVO_CONJURO, intent);
                    getActivity().finish();
                }
            }
        });

        return rootView;
    }

}
