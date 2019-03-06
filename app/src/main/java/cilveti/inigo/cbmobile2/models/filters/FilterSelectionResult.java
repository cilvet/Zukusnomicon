package cilveti.inigo.cbmobile2.models.filters;

import java.util.List;

public class FilterSelectionResult {

    private int code;
    private List<String> values;

    public FilterSelectionResult(int code, List<String> values) {
        this.code = code;
        this.values = values;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
