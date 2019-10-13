package Partidos.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Partidos implements Serializable,Comparable<Partidos> {

        private SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");  ;
        private String local;
        private String visitante;
        private Date date=new Date();
        private Resultado resul;
        private Division division;


        public String toString() {
            return "Para el partido con fecha " + sdf.format(date )+" .Jugaron como local " + local + " y como visitante "
                    + visitante +" teniendo un resultado de " + resul.toString() +" en la division : " +getDivision();
        }








        public Partidos(String visitante, String local,  Resultado resul, Date fecha,Division div) {
            this.visitante = visitante;
            this.local = local;
            this.date=fecha;
            this.resul = resul;
            this.division=div;
        }



            public int compareTo(Partidos o) {
               return getDate().compareTo(o.getDate());
            }



   public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getVisitante() {
            return visitante;
        }

        public void setVisitante(String visitante) {
            this.visitante = visitante;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }

        public Resultado getResul() {
            return resul;
        }

        public void setResul(Resultado resul) {
            this.resul = resul;
        }

        public Division getDivision() {
            return division;
        }

        public void setDivision(Division division) {
            this.division = division;
        }

}
