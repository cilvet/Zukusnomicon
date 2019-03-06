package cilveti.inigo.cbmobile2.models.filters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.R;

public class Filter {

    private List<String> contieneEscuelas;
    public static final int CONTIENE_ESCUELAS = 0;

    private List<String> contieneSubEscuelas;
    public static final int CONTIENE_SUBESCUELAS = 1;

    private List<String> contieneDescriptores;
    public static final int CONTIENE_DESCRIPTORES = 2;

    private int nivelIgualOMayorQue;
    public static final int NIVEL_IGUAL_O_MAYOR_QUE = 3;

    private int nivelIgualOMenorQue;
    public static final int NIVEL_IGUAL_O_MENOR_QUE = 4;

    private List<String> enUnaDeEstasClases;
    public static final int EN_UNA_DE_ESTAS_CLASES = 5;

    private List<String> enNingunaDeEstasClases;
    public static final int EN_NINGUNA_DE_ESTAS_CLASES = 6;

    private List<String> tieneAlgunComponente;
    public static final int TIENE_ALGUN_COMPONENTE = 7;

    private List<String> tieneTodosComponentes;
    public static final int TIENE_TODOS_LOS_COMPONENTES = 20;

    private List<String> noTieneComponentes;
    public static final int NO_TIENE_COMPONENTES = 8;

    private List<String> tieneTiemposDeCasteo;
    public static final int TIENE_TIEMPOS_DE_CASTEO = 9;

    private List<String> tieneTiemposDeRecarga;
    public static final int TIENE_TIEMPOS_DE_REGARGA = 10;

    private List<String> tieneRangos;
    public static final int TIENE_RANGOS = 11;

    private List<String> tieneEfectos;
    public static final int TIENE_EFECTOS = 12;

    private List<String> tieneDuraciones;
    public static final int TIENE_DURACIONES = 13;

    private List<String> tsContieneAlguno;
    public static final int TS_CONTIENE_ALGUNO = 14;

    private List<String> tsContieneTodos;
    public static final int TS_CONTIENE_TODOS = 15;

    private List<String> tsNoContiene;
    public static final int TS_NO_CONTIENE = 16;

    private List<String> reContieneAlguno;
    public static final int RE_CONTIENE_ALGUNO = 17;

    private List<String> reContieneTodos;
    public static final int RE_CONTIENE_TODOS = 18;

    private List<String> reNoContiene;
    public static final int RE_NO_CONTIENE = 19;


    public void setValue(int key, Object value) throws Exception{
        switch (key){
            case CONTIENE_ESCUELAS:
                setContieneEscuelas((List<String>) value);
                break;
            case CONTIENE_SUBESCUELAS:
                setContieneEscuelas((List<String>) value);
                break;
            case CONTIENE_DESCRIPTORES:
                setContieneDescriptores((List<String>) value);
                break;
            case NIVEL_IGUAL_O_MAYOR_QUE:
                setNivelIgualOMayorQue((Integer) value);
                break;
            case NIVEL_IGUAL_O_MENOR_QUE:
                setNivelIgualOMenorQue((Integer) value);
                break;
            case EN_UNA_DE_ESTAS_CLASES:
                setEnUnaDeEstasClases((List<String>) value);
                break;
            case EN_NINGUNA_DE_ESTAS_CLASES:
                setEnNingunaDeEstasClases((List<String>) value);
                break;
            case TIENE_ALGUN_COMPONENTE:
                setTieneAlgunComponente((List<String>) value);
                break;
            case TIENE_TODOS_LOS_COMPONENTES:
                setTieneTodosComponentes((List<String>) value);
                break;
            case NO_TIENE_COMPONENTES:
                setNoTieneComponentes((List<String>) value);
                break;
            case TIENE_TIEMPOS_DE_CASTEO:
                setTieneTiemposDeCasteo((List<String>) value);
                break;
            case TIENE_TIEMPOS_DE_REGARGA:
                setTieneTiemposDeRecarga((List<String>) value);
                break;
            case TIENE_RANGOS:
                setTieneRangos((List<String>) value);
                break;
            case TIENE_EFECTOS:
                setTieneEfectos((List<String>) value);
                break;
            case TIENE_DURACIONES:
                setTieneDuraciones((List<String>) value);
                break;
            case TS_CONTIENE_ALGUNO:
                setTsContieneAlguno((List<String>) value);
                break;
            case TS_CONTIENE_TODOS:
                setTsContieneTodos((List<String>) value);
                break;
            case TS_NO_CONTIENE:
                setTsNoContiene((List<String>) value);
                break;
            case RE_CONTIENE_ALGUNO:
                setReContieneAlguno((List<String>) value);
                break;
            case RE_CONTIENE_TODOS:
                setReContieneTodos((List<String>) value);
                break;
            case RE_NO_CONTIENE:
                setReNoContiene((List<String>) value);
                break;
        }
    }

    public static int getParameterNameId(int key) throws Exception{
        switch (key){
            case CONTIENE_ESCUELAS:
                return R.string.contiene_escuelas;
            case CONTIENE_SUBESCUELAS:
                return R.string.contiene_subescuelas;
            case CONTIENE_DESCRIPTORES:
                return R.string.contiene_descriptores;
            case NIVEL_IGUAL_O_MAYOR_QUE:
                return R.string.nivel_igual_o_mayor_que;
            case NIVEL_IGUAL_O_MENOR_QUE:
                return R.string.nivel_igual_o_menor_que;
            case EN_UNA_DE_ESTAS_CLASES:
                return R.string.en_una_de_estas_clases;
            case EN_NINGUNA_DE_ESTAS_CLASES:
                return R.string.en_ninguna_de_estas_clases;
            case TIENE_ALGUN_COMPONENTE:
                return R.string.tiene_algunos_componentes;
            case TIENE_TODOS_LOS_COMPONENTES:
                return R.string.tiene_todos_los_componentes;
            case NO_TIENE_COMPONENTES:
                return R.string.contiene_escuelas;
            case TIENE_TIEMPOS_DE_CASTEO:
                return R.string.tiene_tiempos_de_casteo;
            case TIENE_TIEMPOS_DE_REGARGA:
                return R.string.tiene_tiempos_de_recarga;
            case TIENE_RANGOS:
                return R.string.tiene_rangos;
            case TIENE_EFECTOS:
                return R.string.tiene_efectos;
            case TIENE_DURACIONES:
                return R.string.tiene_duraciones;
            case TS_CONTIENE_ALGUNO:
                return R.string.ts_contiene_alguno;
            case TS_CONTIENE_TODOS:
                return R.string.ts_contiene_todos;
            case TS_NO_CONTIENE:
                return R.string.ts_no_contiene;
            case RE_CONTIENE_ALGUNO:
                return R.string.re_contiene_alguno;
            case RE_CONTIENE_TODOS:
                return R.string.re_contiene_todos;
            case RE_NO_CONTIENE:
                return R.string.re_no_contiene;
        }
        return 999;
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


    public static Filter documentMapToFilter(Map map){
        Gson gson = new Gson();
        String jsonString = gson.toJson(map, Map.class);
        Filter filter = gson.fromJson(jsonString, Filter.class);
        return filter;
    }

    public List<String> getTieneTodosComponentes() {
        return tieneTodosComponentes;
    }

    public void setTieneTodosComponentes(List<String> tieneTodosComponentes) {
        this.tieneTodosComponentes = tieneTodosComponentes;
    }

    public List<String> getContieneEscuelas() {
        return contieneEscuelas;
    }

    public void setContieneEscuelas(List<String> contieneEscuelas) {
        this.contieneEscuelas = contieneEscuelas;
    }

    public List<String> getContieneSubEscuelas() {
        return contieneSubEscuelas;
    }

    public void setContieneSubEscuelas(List<String> contieneSubEscuelas) {
        this.contieneSubEscuelas = contieneSubEscuelas;
    }

    public List<String> getContieneDescriptores() {
        return contieneDescriptores;
    }

    public void setContieneDescriptores(List<String> contieneDescriptores) {
        this.contieneDescriptores = contieneDescriptores;
    }

    public int getNivelIgualOMayorQue() {
        return nivelIgualOMayorQue;
    }

    public void setNivelIgualOMayorQue(int nivelIgualOMayorQue) {
        this.nivelIgualOMayorQue = nivelIgualOMayorQue;
    }

    public int getNivelIgualOMenorQue() {
        return nivelIgualOMenorQue;
    }

    public void setNivelIgualOMenorQue(int nivelIgualOMenorQue) {
        this.nivelIgualOMenorQue = nivelIgualOMenorQue;
    }

    public List<String> getEnUnaDeEstasClases() {
        return enUnaDeEstasClases;
    }

    public void setEnUnaDeEstasClases(List<String> enUnaDeEstasClases) {
        this.enUnaDeEstasClases = enUnaDeEstasClases;
    }

    public List<String> getEnNingunaDeEstasClases() {
        return enNingunaDeEstasClases;
    }

    public void setEnNingunaDeEstasClases(List<String> enNingunaDeEstasClases) {
        this.enNingunaDeEstasClases = enNingunaDeEstasClases;
    }

    public List<String> getTieneAlgunComponente() {
        return tieneAlgunComponente;
    }

    public void setTieneAlgunComponente(List<String> tieneAlgunComponente) {
        this.tieneAlgunComponente = tieneAlgunComponente;
    }

    public List<String> getNoTieneComponentes() {
        return noTieneComponentes;
    }

    public void setNoTieneComponentes(List<String> noTieneComponentes) {
        this.noTieneComponentes = noTieneComponentes;
    }

    public List<String> getTieneTiemposDeCasteo() {
        return tieneTiemposDeCasteo;
    }

    public void setTieneTiemposDeCasteo(List<String> tieneTiemposDeCasteo) {
        this.tieneTiemposDeCasteo = tieneTiemposDeCasteo;
    }

    public List<String> getTieneTiemposDeRecarga() {
        return tieneTiemposDeRecarga;
    }

    public void setTieneTiemposDeRecarga(List<String> tieneTiemposDeRecarga) {
        this.tieneTiemposDeRecarga = tieneTiemposDeRecarga;
    }

    public List<String> getTieneRangos() {
        return tieneRangos;
    }

    public void setTieneRangos(List<String> tieneRangos) {
        this.tieneRangos = tieneRangos;
    }

    public List<String> getTieneEfectos() {
        return tieneEfectos;
    }

    public void setTieneEfectos(List<String> tieneEfectos) {
        this.tieneEfectos = tieneEfectos;
    }

    public List<String> getTieneDuraciones() {
        return tieneDuraciones;
    }

    public void setTieneDuraciones(List<String> tieneDuraciones) {
        this.tieneDuraciones = tieneDuraciones;
    }

    public List<String> getTsContieneAlguno() {
        return tsContieneAlguno;
    }

    public void setTsContieneAlguno(List<String> tsContieneAlguno) {
        this.tsContieneAlguno = tsContieneAlguno;
    }

    public List<String> getTsContieneTodos() {
        return tsContieneTodos;
    }

    public void setTsContieneTodos(List<String> tsContieneTodos) {
        this.tsContieneTodos = tsContieneTodos;
    }

    public List<String> getTsNoContiene() {
        return tsNoContiene;
    }

    public void setTsNoContiene(List<String> tsNoContiene) {
        this.tsNoContiene = tsNoContiene;
    }

    public List<String> getReContieneAlguno() {
        return reContieneAlguno;
    }

    public void setReContieneAlguno(List<String> reContieneAlguno) {
        this.reContieneAlguno = reContieneAlguno;
    }

    public List<String> getReContieneTodos() {
        return reContieneTodos;
    }

    public void setReContieneTodos(List<String> reContieneTodos) {
        this.reContieneTodos = reContieneTodos;
    }

    public List<String> getReNoContiene() {
        return reNoContiene;
    }

    public void setReNoContiene(List<String> reNoContiene) {
        this.reNoContiene = reNoContiene;
    }
}
