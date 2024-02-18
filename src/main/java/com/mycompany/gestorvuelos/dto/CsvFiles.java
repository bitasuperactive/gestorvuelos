package com.mycompany.gestorvuelos.dto;

import java.io.File;

/**
 * Clase encargada de almacenar las representaciones de los archivos csv 
 * requeridos por la aplicación.
 */
public class CsvFiles
{
    private final File aeropuertos;
    private final File companias;
    private final File vuelosBase;
    private final File vuelosDiarios;
    private final File municipios;

    public CsvFiles(File aeropuertos, File companias, File vuelosBase, File vuelosDiarios, File municipios)
    {
        this.aeropuertos = aeropuertos;
        this.companias = companias;
        this.vuelosBase = vuelosBase;
        this.vuelosDiarios = vuelosDiarios;
        this.municipios = municipios;
    }

    public File getAeropuertos()
    {
        return aeropuertos;
    }

    public File getCompanias()
    {
        return companias;
    }

    public File getVuelosBase()
    {
        return vuelosBase;
    }

    public File getVuelosDiarios()
    {
        return vuelosDiarios;
    }

    public File getMunicipios()
    {
        return municipios;
    }
}
