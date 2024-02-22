package com.mycompany.gestorvuelos.dto;

import java.io.File;

/**
 * Clase encargada de almacenar las representaciones de los archivos csv 
 * requeridos por la aplicación.
 */
public class CsvFiles
{
    private final File AEROPUERTOS;
    private final File COMPANIAS;
    private final File VUELOSBASE;
    private final File VUELOSDIARIOS;
    private final File MUNICIPIOS;

    public CsvFiles(File aeropuertos, File companias, File vuelosBase, File vuelosDiarios, File municipios)
    {
        this.AEROPUERTOS = aeropuertos;
        this.COMPANIAS = companias;
        this.VUELOSBASE = vuelosBase;
        this.VUELOSDIARIOS = vuelosDiarios;
        this.MUNICIPIOS = municipios;
    }

    public File getAEROPUERTOS()
    {
        return AEROPUERTOS;
    }

    public File getCOMPANIAS()
    {
        return COMPANIAS;
    }

    public File getVUELOSBASE()
    {
        return VUELOSBASE;
    }

    public File getVUELOSDIARIOS()
    {
        return VUELOSDIARIOS;
    }

    public File getMUNICIPIOS()
    {
        return MUNICIPIOS;
    }
}
