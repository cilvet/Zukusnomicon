package cilveti.inigo.cbmobile2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConjuroContainer {
    @SerializedName("conjuros")
    @Expose
    private List<Conjuro> conjuros;

    public ConjuroContainer(List<Conjuro> conjuros) {
        this.conjuros = conjuros;
    }

    public List<Conjuro> getConjuros() {
        return conjuros;
    }

    public void setConjuros(List<Conjuro> conjuros) {
        this.conjuros = conjuros;
    }
}
