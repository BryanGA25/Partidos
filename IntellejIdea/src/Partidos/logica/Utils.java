package Partidos.logica;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {


    public static Date convertirToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    public static LocalDate convertirToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


        /*Este metodo lo cree para poder facilitar la comprobación de si era un numero los datos que se encontraban
            en los campos de los resultados de cada equipo */
        public static boolean esUnNumero(String cadena) {

            boolean resultado;

            try {
                Integer.parseInt(cadena);
                resultado = true;
            } catch (NumberFormatException excepcion) {
                resultado = false;
            }

            return resultado;
        }
    }
