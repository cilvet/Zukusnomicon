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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public MainSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainProcess = (MainProcess) getActivity();

        View rootview =  inflater.inflate(R.layout.fragment_main_search, container, false);
        MaterialSearchBar searchBar = rootview.findViewById(R.id.searchBar);
        RecyclerView recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchResultsAdapter(new ArrayList<SearchResult>(), getActivity(), mainProcess);
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("copy database")){
                    //copyDatabase(databaseName);
                }
                adapter.updateValues(mainProcess.getResults(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return rootview;
    }

}
