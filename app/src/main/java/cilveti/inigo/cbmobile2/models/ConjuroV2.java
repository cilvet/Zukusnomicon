package cilveti.inigo.cbmobile2.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConjuroV2 {

    boolean checked = true;

    private String nombre;
    private String escuela;
    private List<String> componentes = new ArrayList<String>();
    private List<String> descriptores = new ArrayList<String>();
    private String tiempoLanzamiento;
    private String alcance;
    private String objetivo;
    private String efecto = null;
    private String area = null;
    ArrayList<String> duracion = new ArrayList<String>();
    private String tiradaSalvacion;
    private String resistenciaConjuros;
    private String manual;
    ArrayList<String> descripcion = new ArrayList<String>();

    private List<String> nivel = new ArrayList<String>();
    ArrayList<String> clases = new ArrayList<String>();
    ArrayList<String> nivelesNumeros = new ArrayList<String>();


    private String originalName;
    private String subEscuela;


    public ConjuroV2(String nombre, ArrayList<String> descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public ConjuroV2(Conjuro conjuro){

        this.nombre = conjuro.getNombre();
        this.nivel = conjuro.getNivel()!=null? conjuro.getNivel(): new ArrayList<String>();
        this.componentes = conjuro.getComponentes()!=null? conjuro.getComponentes(): new ArrayList<String>();
        this.descriptores = conjuro.getDescriptores()!=null? conjuro.getDescriptores(): new ArrayList<String>();
        this.tiempoLanzamiento = conjuro.getTiempoLanzamiento();
        this.alcance = conjuro.getAlcance();
        this.objetivo = conjuro.getObjetivo();
        this.efecto = conjuro.getEfecto();
        this.area = conjuro.getArea();
        this.tiradaSalvacion = conjuro.getTiradaSalvacion();
        this.resistenciaConjuros = conjuro.getResistenciaConjuros();
        this.descripcion = conjuro.getDescripcion();

        ArrayList<String> clases = new ArrayList<>();
        ArrayList<String> numeros = new ArrayList<>();
        if(conjuro.getNivel()!=null){
            for(String nivel: conjuro.getNivel()){
                String clase = nivel.replaceAll("[\\d.]", "");
                String numero = "";
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(nivel);
                while(m.find()) {
                    numero = m.group();
                }

                clases.add(clase.trim());
                numeros.add(numero.trim());
            }
        }

        this.clases = clases;
        this.nivelesNumeros = numeros;

        String originalName = "";
        if(conjuro.getManual()!=null){
            this.manual = conjuro.getManual().replaceAll("\\(.*\\)", "").trim();

            Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(conjuro.getManual());
            while(m.find()) {
                originalName = m.group(1).trim();
            }
        }

        this.originalName = originalName;

        String subescuela = "";
        String escuela = "";
        if(conjuro.getEscuela()!=null){
            Matcher n = Pattern.compile("\\(([^)]+)\\)").matcher(conjuro.getEscuela());
            while(n.find()) {
                subescuela = n.group(1).trim();
            }
            escuela = conjuro.getEscuela().replaceAll("\\(.*\\)", "").replaceAll("\\[.*\\]", "").trim();

        }

        this.subEscuela = subescuela;
        this.escuela = escuela;

        ArrayList<String> duraciones = new ArrayList<>();
        if(conjuro.getDuracion()!=null){
            for(String duracion: Arrays.asList(conjuro.getDuracion().split(","))){
                duraciones.add(duracion.trim());
            }
        }

        this.duracion = duraciones;

    }

    public ArrayList<String> getClases() {
        return clases;
    }

    public void setClases(ArrayList<String> clases) {
        this.clases = clases;
    }

    public ArrayList<String> getNivelesNumeros() {
        return nivelesNumeros;
    }

    public void setNivelesNumeros(ArrayList<String> nivelesNumeros) {
        this.nivelesNumeros = nivelesNumeros;
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

    public static ConjuroV2 documentMapToConjuro(Map map){
        Gson gson = new Gson();
        String jsonString = gson.toJson(map, Map.class);
        ConjuroV2 conjuro = gson.fromJson(jsonString, ConjuroV2.class);
        return conjuro;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSubEscuela() {
        return subEscuela;
    }

    public void setSubEscuela(String subEscuela) {
        this.subEscuela = subEscuela;
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

    public ArrayList<String> getDuracion() {
        return duracion;
    }

    public String getDuraciones() {
        String result = "";
        for(String duracion: duracion){
            if(result.equals("")){
                result += duracion;
            }else{
                result += ", ";
                result += duracion;
            }
        }

        return result;
    }

    public void setDuracion(ArrayList<String> duracion) {
        this.duracion = duracion;
    }

    public void setTiradaSalvacion(String tiradaSalvacion ) {
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