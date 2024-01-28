/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorvuelos.logica_negocio;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.CsvPaths;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author PVita
 */
public class Util
{
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public static CsvPaths getCsvPaths()
    {
        return csvPaths;
    }

    public static List<Aeropuerto> getListAeropuertos()
    {
        return listAeropuertos;
    }

    public static List<Compania> getListCompania()
    {
        return listCompania;
    }

    public static Aeropuerto getAeropuertoGestion()
    {
        return aeropuertoGestion;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public static void setAeropuertoGestion(Aeropuerto aeropuertoGestion)
    {
        Util.aeropuertoGestion = aeropuertoGestion;
    }
    // </editor-fold> 
    
    public static void initUtils(String aeropuertoBaseCodigoIATA) throws IOException, IllegalArgumentException
    {
        csvPaths = PropertiesManager.getCsvPaths();
        listAeropuertos = CsvManager.retrieveListAeropuerto();
        listCompania = CsvManager.retrieveListCompania();
        aeropuertoGestion = ListManager.getAeropuertoByCodigoIATA(aeropuertoBaseCodigoIATA);
    }  
    
    // <editor-fold defaultstate="expanded" desc="Class-private">
    /**
     * Objeto que almacena las rutas a los archivos csv.
     */
    private static CsvPaths csvPaths;
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
