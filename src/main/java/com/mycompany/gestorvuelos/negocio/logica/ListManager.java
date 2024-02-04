package com.mycompany.gestorvuelos.negocio.logica;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Compendio de funciones que administran las listas del aeropuerto.
 * @author PVita
 */
public class ListManager
{
    /**
     * Obtiene el objeto (Aeropuerto) correspondiente al codigo IATA.
     * @param codigoIATA Código identificador del aeropuerto.
     * @return Objeto (Aeropuerto)
     * @see Aeropuerto
     * @throws NoSuchElementException Si no existe aeropuerto con tal códigoIATA.
     */
    public static Aeropuerto getAeropuertoByCodigoIATA(String codigoIATA) throws NoSuchElementException
    {
        Optional<Aeropuerto> aeropuerto = Util.getListAeropuertos().stream()
                .filter(a -> a.getCodigoIATA().equals(codigoIATA))
                .findFirst();
        return aeropuerto.orElseThrow();
    }
    
    public static List<Compania> getListCompaniaByNombre(String nombre)
    {
        return Util.getListCompania().stream()
                .filter(c -> normalizeString(c.getNombre()).contains(normalizeString(nombre)))
                .collect(Collectors.toList());
    }
    
    public static List<Compania> getListCompaniaByPrefijo(int prefijo)
    {
        return  Util.getListCompania().stream()
                .filter(c -> containsNumber(String.valueOf(c.getPrefijo()), String.valueOf(prefijo)))
                .collect(Collectors.toList());
    }
    
    private static boolean containsNumber(String str, String sub) {
        int matches = 0;
        
        for (int i = 0; i < Math.min(str.length(), sub.length()); i++)
        {
            if (str.charAt(i) == sub.charAt(i))
                matches++;
        }
        return matches == sub.length();
    }
    
    private static String normalizeString(String input) {
        // Elimina los diacríticos de la cadena y transfora mayúsculas en minúsculas.
        return Normalizer.normalize(input.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
