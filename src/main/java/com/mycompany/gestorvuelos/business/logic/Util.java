package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.CsvFiles;
import com.mycompany.gestorvuelos.dto.VueloBase;
import com.mycompany.gestorvuelos.dto.VueloDiario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Almacena las variables estáticas necesarias para el proyecto. Requiere
 * inicializar estas variables mediante la función initUtils.
 * @see initUtils
 */
public class Util
{
    /**
     * Inicializa las variables estáticas permitiéndo el uso de la clase y
     * establece un aeropuerto base de operación.
     * @param aeropuertoBaseCodigoIATA Código IATA del aeropuerto base de operación.
     * @throws RuntimeException Propaga las excepciones de las funciones utilizadas.
     * @see PropertiesManager#getCsvFiles
     * @see CsvManager#retrieveMapMunicipios
     * @see CsvManager#retrieveListAeropuerto
     * @see CsvManager#retrieveListCompania
     * @see CsvManager#retrieveListVueloBase
     * @see CsvManager#retrieveListVueloDiario
     * @see ListManager#getAeropuertoByCodigoIATA(java.lang.String)
     */
    public static void initUtils(@NotEmpty String aeropuertoBaseCodigoIATA) throws RuntimeException
    {
        try {
            csvFiles = PropertiesManager.getCsvFiles();
            mapMunicipios = CsvManager.retrieveMapMunicipios();
            listAeropuertos = CsvManager.retrieveListAeropuerto();
            listCompania = CsvManager.retrieveListCompania();
            listVueloBase = CsvManager.retrieveListVueloBase();
            listVueloDiario = CsvManager.retrieveListVueloDiario();
            aeropuertoGestion = ListManager.getAeropuertoByCodigoIATA(aeropuertoBaseCodigoIATA);
            initialized = true;
        } catch (IOException | IllegalArgumentException | ArrayIndexOutOfBoundsException | NoSuchElementException ex) {
            throw new RuntimeException("No ha sido posible inicializar las utilidades requeridas.", ex);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public static boolean isInitialized()
    {
        return initialized;
    }
    
    public static CsvFiles getCsvFiles()
    {
        return (CsvFiles) getObject(csvFiles);
    }

    public static List<Aeropuerto> getListAeropuertos()
    {
        return (List<Aeropuerto>) getObject(listAeropuertos);
    }

    public static List<Compania> getListCompania()
    {
        return (List<Compania>) getObject(listCompania);
    }

    public static List<VueloBase> getListVueloBase()
    {
        return (List<VueloBase>) getObject(listVueloBase);
    }

    public static List<VueloDiario> getListVueloDiario()
    {
        return (List<VueloDiario>) getObject(listVueloDiario);
    }

    public static Aeropuerto getAeropuertoGestion()
    {
        return (Aeropuerto) getObject(aeropuertoGestion);
    }

    public static Map<String, Integer> getMapMunicipios()
    {
        return (Map<String, Integer>) getObject(mapMunicipios);
    }
    
    /**
     * Devuelve el objeto especificado solo si este no es nulo.
     * @param obj Objeto a recuperar.
     * @return Objeto no nulo a recuperar.
     * @throws NullPointerException Si el objeto es nulo porque no se han 
     * inicializado los útiles necesarios.
     * @see initUtils
     */
    private static Object getObject(Object obj) throws NullPointerException
    {
        if (obj == null) {
            throw new NullPointerException("No se han inicializado los útiles requeridos.");
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
    private static CsvFiles csvFiles;
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
     * Lista de los vuelos base.
     */
    private static List<VueloBase> listVueloBase;
    /**
     * Lista de los vuelos diarios.
     */
    private static List<VueloDiario> listVueloDiario;
    /**
     * Aeropuerto de partida.
     */
    private static Aeropuerto aeropuertoGestion;
    // </editor-fold> 
}
