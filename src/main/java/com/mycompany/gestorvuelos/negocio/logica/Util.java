package com.mycompany.gestorvuelos.negocio.logica;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.CsvFiles;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Almacena las variables estáticas necesarias para el proyecto. Requiere
 * inicializar estas variables mediante el método initUtils.
 */
public class Util
{
    /**
     * Inicializa las variables estáticas permitiéndo el uso de la clase y
     * establece un aeropuerto base de operación.
     * @param aeropuertoBaseCodigoIATA Código IATA del aeropuerto base de operación.
     * @throws IllegalArgumentException Si las rutas especificadas en el archivo
     * de propiedades no conducen a un archivo CSV válido.
     * @throws IOException Si no es posible acceder a los archivos CSV requeridos.
     * @throws NoSuchElementException Si no encuentra el aeropuerto a establecer como base de operación.
     */
    public static void initUtils(@NotEmpty String aeropuertoBaseCodigoIATA) throws IllegalArgumentException, IOException, NoSuchElementException
    {
        csvPaths = PropertiesManager.getCsvFiles();
        mapMunicipios = CsvManager.retrieveMapMunicipios();
        listAeropuertos = CsvManager.retrieveListAeropuerto();
        listCompania = CsvManager.retrieveListCompania();
        aeropuertoGestion = ListManager.getAeropuertoByCodigoIATA(aeropuertoBaseCodigoIATA);
        initialized = true;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public static CsvFiles getCsvPaths()
    {
        return (CsvFiles) getObject(csvPaths);
    }

    public static List<Aeropuerto> getListAeropuertos()
    {
        return (List<Aeropuerto>) getObject(listAeropuertos);
    }

    public static List<Compania> getListCompania()
    {
        return (List<Compania>) getObject(listCompania);
    }

    public static Aeropuerto getAeropuertoGestion()
    {
        return (Aeropuerto) getObject(aeropuertoGestion);
    }

    public static Map<String, Integer> getMapMunicipios()
    {
        return (Map<String, Integer>) getObject(mapMunicipios);
    }

    public static boolean isInitialized()
    {
        return initialized;
    }
    
    /**
     * Devuelve el objeto especificado solo si este no es nulo.
     * @param obj Objeto a recuperar.
     * @return Objeto no nulo a recuperar.
     * @throws IllegalArgumentException Si el objeto es nulo porque no se han 
     * inicializado los útiles necesarios.
     * @see initUtils
     */
    private static Object getObject(Object obj) throws IllegalArgumentException
    {
        if (obj == null) {
            throw new IllegalArgumentException("No se han inicializado los útiles requeridos.");
        }
        
        return obj;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Establece el aeropuerto base.
     * @param aeropuertoGestion Aeropuerto a establecer.
     * @throws NullPointerException Si aeropuertoGestion es nulo.
     */
    public static void setAeropuertoGestion(@NotNull Aeropuerto aeropuertoGestion) throws NullPointerException
    {
        if (aeropuertoGestion == null) {
            throw new NullPointerException("El aeropuerto base no puede ser nulo.");
        }
        
        Util.aeropuertoGestion = aeropuertoGestion;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="expanded" desc="Class-private">
    private static boolean initialized;
    /**
     * Objeto que almacena las rutas a los archivos csv.
     */
    private static CsvFiles csvPaths;
    /**
     * Mapa Municipio-Código.
     */
    private static Map<String, Integer> mapMunicipios;
    /**
     * Lista de los aeropuertos disponibles.
     */
    private static List<Aeropuerto> listAeropuertos;
    /**
     * Lista de las compañias disponibles.
     */
    private static List<Compania> listCompania;
    /**
     * Aeropuerto de partida.
     */
    private static Aeropuerto aeropuertoGestion;
    // </editor-fold> 
}
