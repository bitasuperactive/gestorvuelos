package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.CsvFiles;
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
    // <editor-fold defaultstate="collapsed" desc="Propiedades privadas">
    /**
     * Ruta al archivo de propiedades que almacena las rutas de los csvs.
     */
    private static final String CSVPATHS_PROPERTIES_PATH = "src\\main\\java\\com\\mycompany\\gestorvuelos\\CsvPaths.properties";
    /**
     * Clave de la propiedad que almacena la ruta al archivo CSV de aeropuertos.
     */
    private static final String AEROPUERTOS_PROPERTY = "aeropuertos";
    /**
     * Clave de la propiedad que almacena la ruta al archivo CSV de compañías.
     */
    private static final String COMPANIAS_PROPERTY = "companias";
    /**
     * Clave de la propiedad que almacena la ruta al archivo CSV de municipios.
     */
    private static final String MUNICIPIOS_PROPERTY = "municipios";
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
        // Obtenemos las propiedades de CsvPaths.
        Properties props = loadProperties();

        // Obtenemos las rutas correspondientes a cada CSV requerido.
        String aeropuertosCsvPath = props.getProperty(AEROPUERTOS_PROPERTY);
        String companiasCsvPath = props.getProperty(COMPANIAS_PROPERTY);
        String municipiosCsvPath = props.getProperty(MUNICIPIOS_PROPERTY);

        // Comprobamos que sean valores válidos.
        validatePaths(aeropuertosCsvPath, companiasCsvPath, municipiosCsvPath);

        File fileAeropuertos = new File(aeropuertosCsvPath);
        File fileCompanias = new File(companiasCsvPath);
        File fileMunicipios = new File(municipiosCsvPath);

        // Comprobamos que las rutas conduzcan a archivos existentes y legibles.
        checkFileReadability(fileAeropuertos, AEROPUERTOS_PROPERTY);
        checkFileReadability(fileCompanias, COMPANIAS_PROPERTY);
        checkFileReadability(fileMunicipios, MUNICIPIOS_PROPERTY);

        return new CsvFiles(fileAeropuertos, fileCompanias, fileMunicipios);
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
     * @param paths Rutas a validar.
     * @throws IllegalArgumentException Si la ruta especificada es nula o está vacía.
     */
    private static void validatePaths(String... paths) throws IllegalArgumentException
    {
        for (String path : paths) {
            if (path == null || path.isEmpty()) {
                throw new IllegalArgumentException(
                        "Existen rutas nulas o vacías en el archivo CsvPaths.");
            }
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
