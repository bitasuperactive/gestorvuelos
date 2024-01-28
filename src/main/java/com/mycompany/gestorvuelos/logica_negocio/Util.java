/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorvuelos.logica_negocio;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.CsvPaths;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author PVita
 */
public class Util
{
    /**
     * Objeto que almacena las rutas a los archivos csv.
     */
    public static CsvPaths csvPaths;
    /**
     * Aeropuerto de partida.
     */
    public static Aeropuerto aeropuertoBase;
    /**
     * Lista de los aeropuertos disponibles.
     */
    public static List<Aeropuerto> listAeropuertos;
    
    public static void initUtils(String aeropuertoBaseCodigoIATA) throws IOException, IllegalArgumentException
    {
        csvPaths = PropertiesManager.getCsvPaths();
        listAeropuertos = CsvManager.retrieveListAeropuertos();
        aeropuertoBase = ListManager.getAeropuertoByCodigoIATA(aeropuertoBaseCodigoIATA);
    }
}
