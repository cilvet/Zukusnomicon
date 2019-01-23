package cilveti.inigo.cbmobile2.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cilveti.inigo.cbmobile2.business.LocalMainSearchPresenter;
import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.business.interfaces.MainSearchContract;
import cilveti.inigo.cbmobile2.utils.ui.MaterialSearchBarCustom;
import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.utils.ui.ResizeAnimation;
import cilveti.inigo.cbmobile2.ui.adapters.SearchResultsAdapter;
import cilveti.inigo.cbmobile2.constants.Constants;
import cilveti.inigo.cbmobile2.models.SearchResult;

import static cilveti.inigo.cbmobile2.utils.ui.MaterialSearchBarCustom.BUTTON_FILTERS;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainSearchFragment extends Fragment implements MainSearchContract.View {

    private SearchResultsAdapter adapter;
    MainProcess mainProcess;
    MainSearchContract.Presenter presenter;
    MaterialSearchBarCustom searchBar;
    RelativeLayout filters;
    RecyclerView recyclerView;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    boolean filtros = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainProcess = (MainProcess) getActivity();
        presenter = new LocalMainSearchPresenter(mainProcess.getLocalDataFetcher(), this);


        View rootview =  inflater.inflate(R.layout.fragment_main_search, container, false);
        searchBar = rootview.findViewById(R.id.searchBar);
        filters = rootview.findViewById(R.id.filters);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchResultsAdapter(new ArrayList<SearchResult>(), getActivity(), mainProcess);
        recyclerView.setAdapter(adapter);
        searchBar.setMenuDividerEnabled(false);

        filters.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int filterHeight = filters.getMeasuredHeight();

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, filterHeight, getResources().getDisplayMetrics());


        searchBar.setOnSearchActionListener(new MaterialSearchBarCustom.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

                filters.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int filterHeight = filters.getMeasuredHeight();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, filterHeight, getResources().getDisplayMetrics());


                if(buttonCode==BUTTON_FILTERS){
                    if(filtros){
                        resizeFiltersAnimation(0, filterHeight, Constants.ANIMATION_TYPE_DESCENDENT);
                        filtros = !filtros;
                    }else{
                        resizeFiltersAnimation(filterHeight, 0, Constants.ANIMATION_TYPE_ASCENDENT);
                        filtros = !filtros;

                    }
                }
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("copy database")){
                    mainProcess.copyDatabase();
                }

                presenter.search(s.toString());
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootview;
    }

    /**
     * Animar redimensionando los filtros.
     *
     * @param targetHeight  Altura final de la animación de redimensión.
     * @param startHeight   Altura de inicio de la animación de redimensión.
     * @param animationType Tipo de animación, si ascendente o descendente.
     */
    private void resizeFiltersAnimation(int targetHeight, int startHeight, int animationType) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(filters, targetHeight, startHeight, animationType);
        resizeAnimation.setDuration(200);
        filters.startAnimation(resizeAnimation);
    }

    @Override
    public void showResults(List<SearchResult> results) {
        if(adapter!=null){
            adapter.updateValues(results);
        }
    }
}
