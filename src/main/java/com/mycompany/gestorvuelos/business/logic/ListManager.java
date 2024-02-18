package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.VueloBase;
import java.text.Normalizer;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Compendio de funciones útiles para trabajar con listas de objetos.
 * @author PVita
 */
public class ListManager
{
    /**
     * Obtiene el aeropuerto correspondiente al codigo IATA especificado.
     * @param codigoIATA Código identificador del aeropuerto.
     * @return Aeropuerto correspondiente al codigo IATA.
     * @throws NoSuchElementException Si no existe aeropuerto con tal código IATA.
     * @see Aeropuerto
     */
    public static Aeropuerto getAeropuertoByCodigoIATA(String codigoIATA) throws NoSuchElementException
    {
        return Util.getListAeropuertos().stream()
                .filter(a -> a.getCodigoIATA().equals(codigoIATA))
                .findFirst()
                .orElseThrow();
    }
    
    /**
     * Obtiene el vuelo base correspondiente al código de vuelo especificado.
     * @param codigo Código identificador del vuelo base.
     * @return Vuelo base correspondiente al código de vuelo.
     * @throws NoSuchElementException Si no existe vuelo base con tal código de vuelo.
     * @see VueloBase
     */
    public static VueloBase getVueloBaseByCodigo(String codigo) throws NoSuchElementException
    {
        return Util.getListVueloBase().stream()
                .filter(vb -> vb.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow();
    }
    
    /**
     * Obtiene las compañías cuyo nombre coincide total o parcialmente con el especificado.
     * @param nombre Nombre de la compañía a filtrar.
     * @return Lista de compañías que contienen el nombre a filtrar.
     * @see normalizeString
     * @see java.lang.String#contains(CharSequence)
     */
    public static List<Compania> getListCompaniaByNombre(String nombre)
    {
        return Util.getListCompania().stream()
                .filter(c -> normalizeString(c.getNombre()).contains(normalizeString(nombre)))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene las compañías cuyo prefijo coincide total o parcialmente con el especificado.
     * @param prefijo Prefijo identificativo de la compañía a filtrar.
     * @return Lista de compañías que contienen el prefijo filtro.
     * @see numInNum
     */
    public static List<Compania> getListCompaniaByPrefijo(short prefijo)
    {
        return  Util.getListCompania().stream()
                .filter(c -> numInNum(c.getPrefijo(), prefijo))
                .collect(Collectors.toList());
    }
    
    /**
     * Comprueba si el prefijo especificado pertenece a alguna compañía existente.
     * En su defecto, el valor es válido como identificador único de la compañía.
     * @param prefijo Prefijo a validar.
     * @return Verdadero si es único, falso en su defecto.
     */
    public static boolean isCompaniaPrefijoUnique(short prefijo)
    {
        return !Util.getListCompania().stream()
                .anyMatch(c -> c.getPrefijo() == prefijo);
    }
    
    /**
     * Comprueba si el código especificado pertenece a alguna compañía existente.
     * En su defecto, el valor es válido como identificador único de la compañía.
     * @param codigo Código a validar.
     * @return Verdadero si es único, falso en su defecto.
     */
    public static boolean isCompaniaCodigoUnique(String codigo)
    {
        return !Util.getListCompania().stream()
                .anyMatch(c -> c.getCodigo().equals(codigo));
    }
    
    /**
     * Compara dos valores enteros positivos.
     * @param val Número entero que se utilizará como base para la comparación.
     * @param subVal Número entero que se comparará con val.
     * @return Verdadero si todos los dígitos de subVal coinciden en posición y 
     * valor con los dígitos correspondientes de val. Falso en caso contrario.
     */
    private static boolean numInNum(int val, int subVal) {
        String str = String.valueOf(val);
        String sub = String.valueOf(subVal);
        
        if (sub.length() > str.length())
            return false;
        
        for (int i = 0; i < sub.length(); i++)
        {
            if (sub.charAt(i) != str.charAt(i))
                return false;
        }
        return true;
    }
    
    /**
     * Elimina los diacríticos de la cadena y transfora mayúsculas en minúsculas.
     * @param str Cadena de caracteres a normalizar.
     * @return Cadena de caracteres normalizada.
     */
    private static String normalizeString(String str) {
        return Normalizer.normalize(str.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
