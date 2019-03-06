package cilveti.inigo.cbmobile2.ui.fragments.filters;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.models.Option;
import cilveti.inigo.cbmobile2.models.filters.Filter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterContainerFragment extends DialogFragment implements IFiltersParentFragment{

    private int selectedCode;
    private List<String> selectedValues;

    public FilterContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filters_container_fragment, container, false);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadFragment();

    }

    private void loadFragment(){
        // Create fragment and give it an argument specifying the article it should show
        FilterSelectionFragment newFragment = new FilterSelectionFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container_filters, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onItemClicked(int code) {

        switch (code){

        }

        // Create fragment and give it an argument specifying the article it should show
        FilterSelectionFragment newFragment = new FilterSelectionFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right,0);
        transaction.replace(R.id.fragment_container_filters, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void selectedValue(List<String> values) {

    }

    @Override
    public List<Option> getOptions() {

        List<Option> list = new ArrayList<>();
        list.add(new Option(1, "Nivel"));
        list.add(new Option(2, "Clase"));
        list.add(new Option(3, "Descriptor"));
        return list;

    }
}
