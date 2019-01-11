package cilveti.inigo.cbmobile2;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Expression;
import com.couchbase.lite.FullTextExpression;
import com.couchbase.lite.FullTextIndex;
import com.couchbase.lite.FullTextIndexItem;
import com.couchbase.lite.IndexBuilder;
import com.couchbase.lite.Meta;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.couchbase.lite.internal.utils.FileUtils;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cilveti.inigo.cbmobile2.models.SearchResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainSearchFragment extends Fragment {

    private SearchResultsAdapter adapter;
    MainProcess mainProcess;
    MaterialSearchBar searchBar;
    RelativeLayout filters;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if(searchBar!=null){

        }
    }

    boolean filtros = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainProcess = (MainProcess) getActivity();
        final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());


        View rootview =  inflater.inflate(R.layout.fragment_main_search, container, false);
        searchBar = rootview.findViewById(R.id.searchBar);
        filters = rootview.findViewById(R.id.filters);
        RecyclerView recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchResultsAdapter(new ArrayList<SearchResult>(), getActivity(), mainProcess);
        recyclerView.setAdapter(adapter);




        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                if(filtros){
                    resizeFiltersAnimation(0, searchBar.getMinimumHeight() + Functions.getStatusBarHeight(getContext()) + (int) px, Constants.ANIMATION_TYPE_DESCENDENT);
                    filtros = !filtros;
                }else{
                    resizeFiltersAnimation(searchBar.getMinimumHeight() + Functions.getStatusBarHeight(getContext()) + (int) px, 0, Constants.ANIMATION_TYPE_ASCENDENT);
                    filtros = !filtros;

                }

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

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
                adapter.updateValues(mainProcess.getResults(s.toString()));
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

}
