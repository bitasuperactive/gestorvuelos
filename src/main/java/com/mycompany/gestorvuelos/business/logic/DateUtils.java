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
     * Transforma una cadena de caracteres representativa de una hora ("HH:mm")
     * en la clase Date, con la fecha actual y la hora especificada 
     * ("yyyy-MM-dd HH:mm").
     * @param hour Hora a convertir.
     * @return Fecha del día actual con la hora especificada.
     * @throws java.text.ParseException Si el formato de la hora no es "HH:mm".
     */
    public static Date parseHour(String hour) throws ParseException
    {
        // Convertir hora en Date.
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        Date hora = formatoHora.parse(hour);

        // Obtener los valores de horas y minutos.
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(hora);
        int hours = calendario.get(Calendar.HOUR_OF_DAY);
        int minutes = calendario.get(Calendar.MINUTE);

        // Restablecer el calendario con la fecha actual.
        calendario.setTime(new Date());
        // Establecer la hora y los minutos.
        calendario.set(Calendar.HOUR_OF_DAY, hours);
        calendario.set(Calendar.MINUTE, minutes);

        return calendario.getTime();
    }
    
    /**
     * Transforma una cadena de caracteres representativa de una 
     * fecha "dd/MM/yyyy" en la clase Date.
     * @param date Fecha a convertir.
     * @return Fecha correspondiente.
     * @throws ParseException Si el formato de la fecha no es "dd/MM/yyyy".
     */
    public static Date getDate(String date) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(date);
    }
}
