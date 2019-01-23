package cilveti.inigo.cbmobile2.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class SearchResult {
    private String originalName = null;
    private String name;
    private String id;
    private ArrayList<String> description = new ArrayList<String>();

    public SearchResult(Map<String, Object> map){
        Gson gson = new Gson();
        String jsonString = gson.toJson(map, Map.class);
        SearchResult searchResult = gson.fromJson(jsonString, SearchResult.class);
        this.originalName = searchResult.originalName;
        this.name = searchResult.name;
        this.description = searchResult.description;
        this.id = searchResult.id;
    }

    public SearchResult(String originalName, String name, String id, ArrayList<String> description) {
        this.originalName = originalName;
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }
}
