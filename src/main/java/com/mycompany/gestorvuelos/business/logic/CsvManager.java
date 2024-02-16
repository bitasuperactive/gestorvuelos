package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compendio de funciones relativas al guardado, recuperación y actualización de archivos CSV.
 */
public class CsvManager
{
    /**
     * Crea la lista de aeropuertos disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @return Lista de aeropuertos.
     * @see Aeropuerto
     */
    protected static List<Aeropuerto> retrieveListAeropuerto() throws IOException
    {
        ArrayList<Aeropuerto> listAeropuerto = new ArrayList();
        
        File aeropuertosCsvPath = Util.getCsvPaths().getAeropuertos();
        
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
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Lista de compañias.
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @see Compania
     */
    protected static List<Compania> retrieveListCompania() throws IOException
    {
        ArrayList<Compania> listCompania = new ArrayList();
        
        File companiasCsvPath = Util.getCsvPaths().getCompanias();
        
        // Codificación UTF-8 con BOM.
        BufferedReader reader = new BufferedReader(new FileReader(companiasCsvPath, StandardCharsets.UTF_8));
        String row;
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        while ((row = reader.readLine()) != null) {
            String[] companiaData = row.split(";");

            // Must-have values.
            short prefijo = Short.parseShort(companiaData[0].trim());
            String codigo = companiaData[1].trim();
            String nombre = companiaData[2].trim();
            // Optional values.
            String direccionSedeCentral = "";
            String municipioSedeCentral = "";
            String telefonoATC = "";
            String telefonoATA = "";
            if (companiaData.length > 3) // TODO - Mejorar la recuperación de los datos de las compaías.
            {
                if (companiaData.length < 7)
                    throw new ArrayIndexOutOfBoundsException("Los valores opcionales estan incompletos.");
                
                direccionSedeCentral = companiaData[3].trim();
                municipioSedeCentral = companiaData[4].trim();
                telefonoATC = companiaData[5].trim();
                telefonoATA = companiaData[6].trim();
            }
            
            Compania compania = new Compania(prefijo, codigo, nombre, direccionSedeCentral, municipioSedeCentral, telefonoATA, telefonoATC);
            listCompania.add(compania);
        }

        return listCompania;
    }
    
    /**
     * Crea un mapa de municipios. 
     * <br> Clave: Nombre del municipio.
     * <br> Valor: Código identificativo del municipio.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Map<Municipio, Código>
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     */
    protected static Map<String, Integer> retrieveMapMunicipios() throws IOException
    {
        Map<String, Integer> mapMunicipios = new HashMap();
        File municipiosCsvPath = Util.getCsvPaths().getMunicipios();
        
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
}
