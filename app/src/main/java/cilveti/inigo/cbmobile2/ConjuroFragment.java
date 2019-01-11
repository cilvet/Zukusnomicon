package cilveti.inigo.cbmobile2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cilveti.inigo.cbmobile2.models.Conjuro;
import dagger.Multibindings;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConjuroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConjuroFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String idConjuro;
    private MainProcess mainProcess;
    private Conjuro conjuro;

    TextView tv_nombre;
    TextView tv_nombre_raw;
    TextView tv_escuelas;
    TextView tv_nivel;
    TextView tv_componentes;
    TextView tv_tiempo_lanzamiento;
    TextView tv_alcance;
    TextView tv_objetivo;
    TextView tv_duracion;
    TextView tv_tirada_salvacion;
    TextView tv_resistencia_conjuros;
    TextView tv_descripcion;
    FloatingActionButton floatingActionButton;

    public ConjuroFragment() {
        // Required empty public constructor
    }

    public static ConjuroFragment newInstance(String conjuro) {

        Bundle args = new Bundle();
        args.putString("idconjuro", conjuro);
        ConjuroFragment fragment = new ConjuroFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.idConjuro = getArguments().getString("idconjuro");
        mainProcess = (MainProcess) getActivity();
        if(idConjuro!=null && mainProcess!=null){
            conjuro = mainProcess.getConjuro(idConjuro);
            if(conjuro!=null){
                tv_nombre.setText(conjuro.getNombre());
                tv_nombre_raw.setText(conjuro.getConjuroRaw());
                tv_escuelas.setText(conjuro.getEscuela());
                tv_nivel.setText(conjuro.getNiveles());
                tv_componentes.setText(conjuro.getComponentesString());
                tv_tiempo_lanzamiento.setText(conjuro.getTiempoLanzamiento());
                tv_alcance.setText(conjuro.getAlcance());
                tv_objetivo.setText(conjuro.getObjetivo());
                tv_duracion.setText(conjuro.getDuracion());
                tv_tirada_salvacion.setText(conjuro.getTiradaSalvacion());
                tv_resistencia_conjuros.setText(conjuro.getResistenciaConjuros());
                tv_descripcion.setText(conjuro.getDescripciones());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_conjuro, container, false);

        tv_nombre = rootView.findViewById(R.id.tv_nombre);
        tv_nombre_raw = rootView.findViewById(R.id.tv_raw_name);
        tv_escuelas = rootView.findViewById(R.id.tv_escuelas);
        tv_nivel = rootView.findViewById(R.id.tv_nivel);
        tv_componentes = rootView.findViewById(R.id.tv_componentes);
        tv_tiempo_lanzamiento = rootView.findViewById(R.id.tv_tiempo_lanzamiento);
        tv_alcance = rootView.findViewById(R.id.tv_alcance);
        tv_objetivo = rootView.findViewById(R.id.tv_objetivo);
        tv_duracion = rootView.findViewById(R.id.tv_duracion);
        tv_tirada_salvacion = rootView.findViewById(R.id.tv_tirada_salvacion);
        tv_resistencia_conjuros = rootView.findViewById(R.id.tv_resistencia_conjuros);
        tv_descripcion = rootView.findViewById(R.id.tv_descripcion);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainProcess.openSearch();
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
