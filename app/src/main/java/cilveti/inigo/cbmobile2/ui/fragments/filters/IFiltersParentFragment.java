package cilveti.inigo.cbmobile2.ui.fragments.filters;

import java.util.List;

import cilveti.inigo.cbmobile2.models.Option;

public interface IFiltersParentFragment {

    void onItemClicked(int code);
    void selectedValue(List<String> values);
    List<Option> getOptions();


}
