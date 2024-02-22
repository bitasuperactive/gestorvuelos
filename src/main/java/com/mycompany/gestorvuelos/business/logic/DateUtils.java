package com.mycompany.gestorvuelos.business.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Compendio de funciones que facilitan el tratamiento de fechas.
 */
public class DateUtils
{
    /**
     * Formatea una cadena de caracteres representativa de una 
     * hora "HH:mm" en la clase Date.
     * @param hour Hora a convertir.
     * @return Fecha por defecto con la hora especificada.
     * @throws java.text.ParseException Si el formato de la hora no es "HH:mm".
     */
    public static Date parseHour(String hour) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.parse(hour);

//        // Obtener los valores de horas y minutos.
//        Calendar calendario = Calendar.getInstance();
//        calendario.setTime(hora);
//        int hours = calendario.get(Calendar.HOUR_OF_DAY);
//        int minutes = calendario.get(Calendar.MINUTE);
//
//        // Restablecer el calendario con la fecha actual.
//        calendario.setTime(date);
//        // Establecer la hora y los minutos.
//        calendario.set(Calendar.HOUR_OF_DAY, hours);
//        calendario.set(Calendar.MINUTE, minutes);
//        calendario.set(Calendar.SECOND, 0);
//
//        return calendario.getTime();
    }
    
    /**
     * Formatea una cadena de caracteres representativa de una 
     * fecha "dd/MM/yyyy" en la clase Date.
     * @param date Fecha a convertir.
     * @return Fecha correspondiente.
     * @throws ParseException Si el formato de la fecha no es "dd/MM/yyyy".
     */
    public static Date parseDate(String date) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(date);
    }
    
    /**
     * Formatea una fecha obteniéndo solo la hora.
     * @param date Fecha a formatear.
     * @return Hora de la fecha.
     */
    public static String dateToHour(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    
    /**
     * Formatea una fecha completa a "dd/MM/yyyy".
     * @param date Fecha a formatear.
     * @return Fecha formateada.
     */
    public static String shortDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
    /**
     * Compara dos fecha sin tener en cuenta la hora.
     * @param date1 Primera fecha de comparación.
     * @param date2 Segunda fecha de comparación.
     * @return Verdadero si las fechas son equivalentes, falso en su defecto.
     */
    public static boolean compareDates(Date date1, Date date2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date1Fromatted = sdf.format(date1);
        String date2Formatted = sdf.format(date2);
        return date1Fromatted.equals(date2Formatted);
    }
}
