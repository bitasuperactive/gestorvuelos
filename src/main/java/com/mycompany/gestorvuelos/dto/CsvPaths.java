package com.mycompany.gestorvuelos.dto;

/**
 * Clase encargada de almacenar los valores correspondientes a las rutas de los archivos csv.
 * @author PVita
 */
public class CsvPaths
{
    private String aeropuertos;
    private String companias;
    private String municipios;

    public CsvPaths(String aeropuertos, String companias, String municipios)
    {
        this.aeropuertos = aeropuertos;
        this.companias = companias;
        this.municipios = municipios;
    }

    public String getAeropuertos()
    {
        return aeropuertos;
    }

    public String getCompanias()
    {
        return companias;
    }

    public String getMunicipios()
    {
        return municipios;
    }
}
