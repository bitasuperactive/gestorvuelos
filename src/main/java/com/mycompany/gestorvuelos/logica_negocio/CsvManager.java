package com.mycompany.gestorvuelos.logica_negocio;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Compendio de funciones relativas al guardado, recuperación y actualización de archivos (.csv).
 * @author PVita
 */
public class CsvManager
{
    // ---> ACCESS MODIFIER: PACKAGE-PRIVATE <---
    /**
     * Crea la lista de aeropuertos disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del archivo (.csv).</pre>
     * @return Lista de aeropuertos.
     * @throws java.io.IOException Si el archivo (.csv) es inaccesible.
     */
    static List<Aeropuerto> retrieveListAeropuertos() throws IOException
    {
        ArrayList<Aeropuerto> listAeropuertos = new ArrayList();
        
        String aeropuertosCsvPath = Util.csvPaths.getAeropuertos();
        
        // Codificación UTF-8 con BOM.
        BufferedReader reader = new BufferedReader(new FileReader(aeropuertosCsvPath, StandardCharsets.UTF_8));
        String row;
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        while ((row = reader.readLine()) != null) {
            String[] aeropuertoData = row.split(";");

            String codigoIATA = aeropuertoData[0].trim();
            String nombre = aeropuertoData[1].trim();
            String municipio = aeropuertoData[2].trim();
            int codigoMunicipio = retrieveCodigoMunicipio(municipio);

            Aeropuerto aeropuerto = new Aeropuerto(codigoIATA, nombre, codigoMunicipio);
            listAeropuertos.add(aeropuerto);
        }

        return listAeropuertos;
    }
    
    // ---> ACCESS MODIFIER: CLASS-PRIVATE <---
    private static Map<String, Integer> municipios;
    
    private static int retrieveCodigoMunicipio(String municipio) throws IOException
    {
        municipios = (municipios == null) ? getMunicipios() : municipios;
        
        // Valor por defecto para municipios internacionales.
        int codigoMunicipio = municipios.getOrDefault(municipio, 0);
        
        return codigoMunicipio;
    }
    
    
    private static Map<String, Integer> getMunicipios() throws IOException
    {
        Map<String, Integer> municipios = new HashMap();
        String municipiosCsvPath = Util.csvPaths.getMunicipios();
        
        // Codificación ANSI.
        BufferedReader reader = new BufferedReader(new FileReader(municipiosCsvPath, Charset.forName("Cp1252")));
        String row;
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        while ((row = reader.readLine()) != null) {
            String[] rowVals = row.split(";");
            int codigoMunicipio = Integer.parseInt(rowVals[0].trim());
            String municipio = rowVals[1].trim();
            municipios.put(municipio, codigoMunicipio);
        }
        
        return municipios;
    }
}
