package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.CsvFiles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Clase encargada de recuperar los valores almacenados en archivos de propiedades.
 */
public class PropertiesManager
{
    // <editor-fold defaultstate="collapsed" desc="Propiedades privadas">
    /**
     * Ruta al archivo de propiedades que almacena las rutas de los CSV requeridos.
     */
    private static final String CSVPATHS_PROPERTIES_PATH = "src\\main\\java\\com\\mycompany\\gestorvuelos\\CsvPaths.properties";
    /**
     * Array de las claves del archivo de propiedades CsvPaths.
     */
    private static final String[] CSVPATHS_PROPERTIES = {
            "aeropuertos",
            "companias",
            "vuelos.base",
            "vuelos.diarios",
            "municipios"
    };
    // </editor-fold>
    
    /**
     * Obtiene las representaciones validadas de los archivos CSV requeridos.
     * @return CsvFiles validado.
     * @throws IOException Si el archivo de propiedades CsvPaths es innacesible.
     * @throws IllegalArgumentException Si las rutas son nulas o están vacías.
     * @throws FileNotFoundException Si los archivos CSV requeridos no existen o no son legibles.
     * @see CsvFiles
     */
    public static CsvFiles getCsvFiles() throws IOException, IllegalArgumentException, FileNotFoundException
    {
        List<File> csvFiles = new ArrayList(CSVPATHS_PROPERTIES.length);
        
        Properties props = loadProperties();
        
        for (String prop : CSVPATHS_PROPERTIES) {
            // Obtenemos la ruta al archivo CSV.
            String csvPath = props.getProperty(prop);
            
            // Comprobamos que sea una ruta válida.
            validatePath(csvPath);
            
            // Comprobamos que la ruta conduzca a un archivo existente y legible.
            File csvFile = new File(csvPath);
            checkFileReadability(csvFile, prop);
            
            csvFiles.add(csvFile);
        }

        return new CsvFiles(csvFiles.get(0), 
                csvFiles.get(1), 
                csvFiles.get(2), 
                csvFiles.get(3), 
                csvFiles.get(4));
    }

    /**
     * Carga el archivo de propiedades CsvPaths.
     * @return Propiedades CsvPaths.
     * @throws IOException Si CsvPaths es inaccesible.
     */
    private static Properties loadProperties() throws IOException
    {
        Properties props = new Properties();
        try (var csvPathsFile = new FileInputStream(CSVPATHS_PROPERTIES_PATH))
        {
            props.load(csvPathsFile);
        }
        return props;
    }
    
    /**
     * Comprueba que la ruta especificada no sea nula o esté vacía.
     * @param paths Ruta a validar.
     * @throws IllegalArgumentException Si la ruta especificada es nula o está vacía.
     */
    private static void validatePath(String path) throws IllegalArgumentException
    {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException(
                    "Existen rutas nulas o vacías en el archivo CsvPaths.");
        }
    }
    
    /**
     * Comprueba que el archivo especificado exista y sea legible.
     * @param file Archivo a validar.
     * @param propertyName Clave de la propiedad de la que se extrae la ruta al archivo.
     * @throws FileNotFoundException Si el archivo no existe.
     * @throws IOException Si el archivo no es legible.
     */
    private static void checkFileReadability(File file, String propertyName) throws FileNotFoundException, IOException
    {
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("El archivo CSV \"%s\" no existe.", propertyName));
        }
        if (!file.canRead()) {
            throw new IOException(String.format("El archivo CSV \"%s\" no es legible.", propertyName));
        }
    }
}
