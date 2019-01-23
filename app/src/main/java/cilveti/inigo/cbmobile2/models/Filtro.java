package cilveti.inigo.cbmobile2.models;

import java.util.ArrayList;
import java.util.List;

public class Filtro {

    public static final int UNIQUE_STRING = 1;
    public static final int STRING_LIST = 2;

    public static String NOMBRE = "nombre";
    public static String NIVEL = "nivel";
    public static String ESCUELA = "escuela";
    public static String COMPONENTES = "componentes";
    public static String DESCRIPTORES = "descriptores";
    public static String TIEMPO_LANZAMIENTO = "tiempoLanzamiento";
    public static String ALCANCE = "alcance";
    public static String OBJETIVO = "objetivo";
    public static String EFECTO = "efecto";
    public static String AREA = "area";
    public static String DURACION = "duracion";
    public static String TIRADA_SALVACION = "tiradaSalvacion";
    public static String RESISTENCIA_CONJUROS = "resistenciaConjuros";
    public static String MANUAL = "manual";


//    private String conjuroRaw = null;
//    private String nombre;
//    private List<String> nivel;
//    private String escuela;
//    private List<String> componentes;
//    private List<String> descriptores;
//    private String tiempoLanzamiento;
//    private String alcance;
//    private String objetivo;
//    private String efecto = null;
//    private String area = null;
//    private String duracion;
//    private String tiradaSalvacion;
//    private String resistenciaConjuros;
//    private String manual;

    public Filtro ByName(List<String> name){
        return new Filtro(UNIQUE_STRING, NOMBRE, name);
    }

    public Filtro ByName(String name){
        List<String> list = new ArrayList<>();
        list.add(name);
        return ByName(stringToList(name));
    }

    public Filtro ByLevel(List<String> level){
        return new Filtro(STRING_LIST, NIVEL, level);
    }

    public Filtro ByLevel(String name){
        List<String> list = new ArrayList<>();
        list.add(name);
        return ByLevel(stringToList(name));
    }

    public Filtro ByEscuela(List<String> Escuela){
        return new Filtro(UNIQUE_STRING, ESCUELA, Escuela);
    }

    public Filtro ByEscuela(String Escuela){
        List<String> list = new ArrayList<>();
        list.add(Escuela);
        return ByEscuela(stringToList(Escuela));
    }

    public Filtro ByComponentes(List<String> Componentes){
        return new Filtro(STRING_LIST, COMPONENTES, Componentes);
    }

    public Filtro ByComponentes(String Componentes){
        List<String> list = new ArrayList<>();
        list.add(Componentes);
        return ByComponentes(stringToList(Componentes));
    }

    public Filtro ByDescriptores(List<String> Descriptores){
        return new Filtro(STRING_LIST, DESCRIPTORES, Descriptores);
    }

    public Filtro ByDescriptores(String Descriptores){
        List<String> list = new ArrayList<>();
        list.add(Descriptores);
        return ByDescriptores(stringToList(Descriptores));
    }

    public Filtro ByTiempoLanzamiento(List<String> TiempoLanzamiento){
        return new Filtro(UNIQUE_STRING, TIEMPO_LANZAMIENTO, TiempoLanzamiento);
    }

    public Filtro ByTiempoLanzamiento(String TiempoLanzamiento){
        List<String> list = new ArrayList<>();
        list.add(TiempoLanzamiento);
        return ByTiempoLanzamiento(stringToList(TiempoLanzamiento));
    }

    public Filtro ByAlcance(List<String> Alcance){
        return new Filtro(UNIQUE_STRING, ALCANCE, Alcance);
    }

    public Filtro ByAlcance(String Alcance){
        List<String> list = new ArrayList<>();
        list.add(Alcance);
        return ByAlcance(stringToList(Alcance));
    }

    public Filtro ByObjetivo(List<String> objetivo){
        return new Filtro(UNIQUE_STRING, OBJETIVO, objetivo );
    }

    public Filtro ByObjetivo(String Objetivo){
        List<String> list = new ArrayList<>();
        list.add(Objetivo);
        return ByObjetivo(stringToList(Objetivo));
    }

    public Filtro ByEfecto(List<String> Efecto){
        return new Filtro(UNIQUE_STRING, EFECTO, Efecto );
    }

    public Filtro ByEfecto(String Efecto){
        List<String> list = new ArrayList<>();
        list.add(Efecto);
        return ByEfecto(stringToList(Efecto));
    }


    public Filtro ByArea(List<String> Area){
        return new Filtro(UNIQUE_STRING, AREA, Area );
    }

    public Filtro ByArea(String Area){
        List<String> list = new ArrayList<>();
        list.add(Area);
        return ByArea(stringToList(Area));
    }

    public Filtro ByDuracion(List<String> Duracion){
        return new Filtro(UNIQUE_STRING, DURACION, Duracion );
    }

    public Filtro ByDuracion(String Duracion){
        List<String> list = new ArrayList<>();
        list.add(Duracion);
        return ByDuracion(stringToList(Duracion));
    }

    public Filtro ByTiradaSalvacion(List<String> TiradaSalvacion){
        return new Filtro(UNIQUE_STRING, TIRADA_SALVACION, TiradaSalvacion );
    }

    public Filtro ByTiradaSalvacion(String TiradaSalvacion){
        List<String> list = new ArrayList<>();
        list.add(TiradaSalvacion);
        return ByTiradaSalvacion(stringToList(TiradaSalvacion));
    }

    public Filtro ByResistenciaConjuros(List<String> ResistenciaConjuros){
        return new Filtro(UNIQUE_STRING, RESISTENCIA_CONJUROS, ResistenciaConjuros );
    }

    public Filtro ByResistenciaConjuros(String ResistenciaConjuros){
        List<String> list = new ArrayList<>();
        list.add(ResistenciaConjuros);
        return ByResistenciaConjuros(stringToList(ResistenciaConjuros));
    }

    public Filtro ByManual(List<String> Manual){
        return new Filtro(UNIQUE_STRING, MANUAL, Manual );
    }

    public Filtro ByManual(String Manual){
        List<String> list = new ArrayList<>();
        list.add(Manual);
        return ByManual(stringToList(Manual));
    }
    
    private List<String> stringToList(String string){
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }

//    private String conjuroRaw = null;
//    private String nombre;
//    private List<String> nivel;
//    private String escuela;
//    private List<String> componentes;
//    private List<String> descriptores;
//    private String tiempoLanzamiento;
//    private String alcance;
//    private String objetivo;
//    private String efecto = null;
//    private String area = null;
//    private String duracion;
//    private String tiradaSalvacion;
//    private String resistenciaConjuros;
//    private String manual;
//

    private int type;
    private String key;
    private List<String> values;

    public Filtro(int type, String key, List<String> values) {
        this.type = type;
        this.key = key;
        this.values = values;
    }
}
