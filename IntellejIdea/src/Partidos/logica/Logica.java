package Partidos.logica;

import Partidos.model.Partidos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Logica {

    private static Logica INSTANCE =null;
    private ObservableList<Partidos> listaPartidos;
    private ArrayList<Partidos> arrayPartidos;

    private  Logica(){

    listaPartidos= FXCollections.observableArrayList();


    }
    public static Logica getINSTANCE(){

        if (INSTANCE ==null){
            INSTANCE=new Logica();
        }
        return  INSTANCE;
    }

    public void addPartido(Partidos partidos){

        listaPartidos.add(partidos);

    }
    public void modificarPartido(Partidos partidos ,int id){

        listaPartidos.set(id,partidos);

    }

    public void borraPartido(int id){
       if (id>=0) {
           listaPartidos.remove(id);
       }

    }
    public ObservableList getListaPartidos(){

        return listaPartidos;
    }
    public ArrayList<Partidos> getArray(){
        if(arrayPartidos.isEmpty()!=true) {
            arrayPartidos = new ArrayList<>(listaPartidos);

            return arrayPartidos;
        }else{

            return arrayPartidos;
        }
    }



}
