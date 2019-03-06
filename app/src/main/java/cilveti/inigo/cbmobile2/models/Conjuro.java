package cilveti.inigo.cbmobile2.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conjuro {

    boolean checked = true;

    private String conjuroRaw = null;
    private String nombre;
    private List<String> nivel;
    private String escuela;
    private List<String> componentes;
    private List<String> descriptores;
    private String tiempoLanzamiento;
    private String alcance;
    private String objetivo;
    private String efecto = null;
    private String area = null;
    private String duracion;
    private String tiradaSalvacion;
    private String resistenciaConjuros;
    private String manual;
    ArrayList<String> descripcion = new ArrayList<String>();

    public Conjuro(String nombre, ArrayList<String> descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    // Getter Methods
    public Map<String, Object> getPropertiesForUpdate(){
        Map<String, Object> result = new HashMap<>();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Gson gson = new Gson();
        String json = gson.toJson(this);
        Map<String, Object> myMap = gson.fromJson(json, type);
        return myMap;
    }

    public static Conjuro documentMapToConjuro(Map map){
        Gson gson = new Gson();
        String jsonString = gson.toJson(map, Map.class);
        Conjuro conjuro = gson.fromJson(jsonString, Conjuro.class);
        return conjuro;
    }

    public String getConjuroRaw() {
        return conjuroRaw;
    }

    public List<String> getNivel() {
        return nivel;
    }

    public String getNiveles() {
        String result = "";
        if(nivel!=null)
        for(String descripcion: nivel){
            if(result==""){
                result = descripcion;
            }else{
                result += ", " +  descripcion;
            }
        }
        return result;
    }


    public void setNivel(List<String> nivel) {
        this.nivel = nivel;
    }

    public List<String> getComponentes() {
        return componentes;
    }

    public String getComponentesString(){
        String result = "";
        if(componentes!=null)
        for(String descripcion: componentes){
            result += ", " +  descripcion;
        }
        return result;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes;
    }

    public List<String> getDescriptores() {
        return descriptores;
    }

    public void setDescriptores(List<String> descriptores) {
        this.descriptores = descriptores;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEscuela() {
        return escuela;
    }

    public String getTiempoLanzamiento() {
        return tiempoLanzamiento;
    }

    public String getAlcance() {
        return alcance;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getEfecto() {
        return efecto;
    }

    public String getArea() {
        return area;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getTiradaSalvacion() {
        return tiradaSalvacion;
    }

    public String getResistenciaConjuros() {
        return resistenciaConjuros;
    }

    public String getManual() {
        return manual;
    }

    // Setter Methods

    public void setConjuroRaw( String conjuroRaw ) {
        this.conjuroRaw = conjuroRaw;
    }

    public void setNombre( String nombre ) {
        this.nombre = nombre;
    }

    public void setEscuela( String escuela ) {
        this.escuela = escuela;
    }

    public void setTiempoLanzamiento( String tiempoLanzamiento ) {
        this.tiempoLanzamiento = tiempoLanzamiento;
    }

    public void setAlcance( String alcance ) {
        this.alcance = alcance;
    }

    public void setObjetivo( String objetivo ) {
        this.objetivo = objetivo;
    }

    public void setEfecto( String efecto ) {
        this.efecto = efecto;
    }

    public void setArea( String area ) {
        this.area = area;
    }

    public void setDuracion( String duracion ) {
        this.duracion = duracion;
    }

    public void setTiradaSalvacion( String tiradaSalvacion ) {
        this.tiradaSalvacion = tiradaSalvacion;
    }

    public void setResistenciaConjuros( String resistenciaConjuros ) {
        this.resistenciaConjuros = resistenciaConjuros;
    }

    public void setManual( String manual ) {
        this.manual = manual;
    }

    public ArrayList<String> getDescripcion() {
        return descripcion;
    }

    public String getDescripciones(){
        String result = "";
        if(descripcion!=null)
        for(String descripcion: descripcion){
            result += "\n\n" +  descripcion;
        }
        return result;
    }

    public void setDescripcion(ArrayList<String> descripcion) {
        this.descripcion = descripcion;
    }
}