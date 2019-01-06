package cilveti.inigo.cbmobile2.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class SearchResult {
    private String conjuroRaw = null;
    private String nombre;
    private String id;
    private ArrayList<String> descripcion = new ArrayList<String>();

    public SearchResult(Map<String, Object> map){
        Gson gson = new Gson();
        String jsonString = gson.toJson(map, Map.class);
        SearchResult searchResult = gson.fromJson(jsonString, SearchResult.class);
        this.conjuroRaw = searchResult.conjuroRaw;
        this.nombre = searchResult.nombre;
        this.descripcion = searchResult.descripcion;
        this.id = searchResult.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConjuroRaw() {
        return conjuroRaw;
    }

    public void setConjuroRaw(String conjuroRaw) {
        this.conjuroRaw = conjuroRaw;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(ArrayList<String> descripcion) {
        this.descripcion = descripcion;
    }
}
