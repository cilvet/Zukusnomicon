package cilveti.inigo.cbmobile2.models;

import android.graphics.Color;

import java.util.List;

public class FiltroComposite {
    private List<Filtro> filters;
    private String name;

    public FiltroComposite(List<Filtro> filters, String name) {
        this.filters = filters;
        this.name = name;
    }

}
