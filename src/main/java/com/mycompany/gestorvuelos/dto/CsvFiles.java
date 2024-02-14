package com.mycompany.gestorvuelos.dto;

import java.io.File;

/**
 * Clase encargada de almacenar las representaciones de los archivos csv 
 * requeridos por la aplicación.
 */
public class CsvFiles
{
    private File aeropuertos;
    private File companias;
    private File municipios;

    public CsvFiles(File aeropuertos, File companias, File municipios)
    {
        this.aeropuertos = aeropuertos;
        this.companias = companias;
        this.municipios = municipios;
    }
    
    public File getAeropuertos()
    {
        return aeropuertos;
    }

    public void setAeropuertos(File aeropuertos)
    {
        this.aeropuertos = aeropuertos;
    }

    public File getCompanias()
    {
        return companias;
    }

    public void setCompanias(File companias)
    {
        this.companias = companias;
    }

    public File getMunicipios()
    {
        return municipios;
    }

    public void setMunicipios(File municipios)
    {
        this.municipios = municipios;
    }
}
