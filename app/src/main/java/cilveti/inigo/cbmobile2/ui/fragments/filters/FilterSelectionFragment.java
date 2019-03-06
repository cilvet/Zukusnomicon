package cilveti.inigo.cbmobile2.ui.fragments.filters;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.ui.adapters.FilterSelectionAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterSelectionFragment extends Fragment {


    public FilterSelectionFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.rv_filters);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        IFiltersParentFragment parentFragment = (IFiltersParentFragment) getParentFragment();
        FilterSelectionAdapter adapter = new FilterSelectionAdapter(parentFragment, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
