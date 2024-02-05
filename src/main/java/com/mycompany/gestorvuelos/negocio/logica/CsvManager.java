package com.mycompany.gestorvuelos.negocio.logica;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
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
    static List<Aeropuerto> retrieveListAeropuerto() throws IOException
    {
        ArrayList<Aeropuerto> listAeropuerto = new ArrayList();
        
        String aeropuertosCsvPath = Util.getCsvPaths().getAeropuertos();
        
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
            int codigoMunicipio = Util.getMapMunicipios().getOrDefault(municipio, 0);

            Aeropuerto aeropuerto = new Aeropuerto(codigoIATA, nombre, codigoMunicipio);
            listAeropuerto.add(aeropuerto);
        }

        return listAeropuerto;
    }
    /**
     * Crea la lista de compañias disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del archivo (.csv).</pre>
     * @return Lista de compañias.
     * @throws java.io.IOException Si el archivo (.csv) es inaccesible.
     */
    static List<Compania> retrieveListCompania() throws IOException
    {
        ArrayList<Compania> listCompania = new ArrayList();
        
        String companiasCsvPath = Util.getCsvPaths().getCompanias();
        
        // Codificación UTF-8 con BOM.
        BufferedReader reader = new BufferedReader(new FileReader(companiasCsvPath, StandardCharsets.UTF_8));
        String row;
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        while ((row = reader.readLine()) != null) {
            String[] companiaData = row.split(";");

            // Must-have values.
            int prefijo = Integer.parseInt(companiaData[0].trim());
            String codigo = companiaData[1].trim();
            String nombre = companiaData[2].trim();
            // Optional values.
            String direccionSedeCentral = "not-registered";
            String municipioSedeCentral = "not-registered";
            int telefonoATC = 0;
            int telefonoATA = 0;
            if (companiaData.length > 3) // TODO - Mejorar la recuperación de los datos de las compaías.
            {
                if (companiaData.length < 7)
                    throw new ArrayIndexOutOfBoundsException("Los valores opcionales estan incompletos.");
                
                direccionSedeCentral = companiaData[3].trim();
                municipioSedeCentral = companiaData[4].trim();
                telefonoATC = Integer.parseInt(companiaData[5].trim());
                telefonoATA = Integer.parseInt(companiaData[6].trim());
            }
            
            Compania compania = new Compania(prefijo, codigo, nombre, direccionSedeCentral, municipioSedeCentral, telefonoATC, telefonoATA);
            listCompania.add(compania);
        }

        return listCompania;
    }
    
    static Map<String, Integer> retrieveMapMunicipios() throws IOException
    {
        Map<String, Integer> mapMunicipios = new HashMap();
        String municipiosCsvPath = Util.getCsvPaths().getMunicipios();
        
        // Codificación ANSI.
        BufferedReader reader = new BufferedReader(new FileReader(municipiosCsvPath, Charset.forName("Cp1252")));
        String row;
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        while ((row = reader.readLine()) != null) {
            String[] rowVals = row.split(";");
            int codigoMunicipio = Integer.parseInt(rowVals[0].trim());
            String municipio = rowVals[1].trim();
            mapMunicipios.put(municipio, codigoMunicipio);
        }
        
        return mapMunicipios;
    }
    
    // ---> ACCESS MODIFIER: CLASS-PRIVATE <---
    
}
