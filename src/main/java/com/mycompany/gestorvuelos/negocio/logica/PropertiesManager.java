package com.mycompany.gestorvuelos.negocio.logica;

import com.mycompany.gestorvuelos.dto.CsvPaths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de recuperar los valores almacenados en archivos de propiedades.
 * @author PVita
 */
public class PropertiesManager
{
    public static CsvPaths getCsvPaths() throws IOException, IllegalArgumentException
    {
        try (var csvPathsFile = new FileInputStream(CSVPATHS_PROPERTIES_PATH))
        {
            Properties props = new Properties();
            props.load(csvPathsFile);
            
            String aeropuertos, companias, municipios;
            aeropuertos = props.getProperty("aeropuertos");
            companias = props.getProperty("companias");
            municipios = props.getProperty("municipios");
            
            System.err.print(aeropuertos);
            
            if (aeropuertos == null || aeropuertos.isEmpty() ||
            companias == null || companias.isEmpty() ||
            municipios == null || municipios.isEmpty()) 
            {
                throw new IllegalArgumentException("Los valores en el archivo {CSVPATHS_PROPERTIES_PATH} están vacíos.");
            }
            
            return new CsvPaths(aeropuertos, companias, municipios);
        }
    }
    
    // ---> ACCESS MODIFIER: CLASS-PRIVATE <---
    /**
     * Ruta al archivo de propiedades que almacena las rutas de los csvs.
     */
    private static final String CSVPATHS_PROPERTIES_PATH = "src\\main\\java\\com\\mycompany\\gestorvuelos\\CsvPaths.properties";
}
