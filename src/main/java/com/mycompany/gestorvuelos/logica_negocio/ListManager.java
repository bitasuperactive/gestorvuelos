package com.mycompany.gestorvuelos.logica_negocio;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
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
    
    public static List<Compania> getListCompaniaByPrefijo(int prefijo)
    {
        return  Util.getListCompania().stream()
                .filter(c -> containsSubstring(String.valueOf(c.getPrefijo()), String.valueOf(prefijo)))
                .collect(Collectors.toList());
    }
    
    private static boolean containsSubstring(String str, String sub) {
        int lastIndex = -1;

        for (char c : sub.toCharArray()) {
            int index = str.indexOf(c, lastIndex + 1);

            if (index != lastIndex + 1) {
                return false;  // Si no se encuentra el carácter, la subcadena no está presente en orden
            }

            lastIndex = index;
        }

        return true;
    }
}
