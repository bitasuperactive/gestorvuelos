package com.mycompany.gestorvuelos.logica_negocio;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<Aeropuerto> aeropuerto = Util.listAeropuertos.stream()
                .filter(p -> p.getCodigoIATA().equals(codigoIATA))
                .findFirst();
        return aeropuerto.orElseThrow();
    }
}
