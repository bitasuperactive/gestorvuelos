package com.mycompany.gestorvuelos.dto;

/**
 * Almacena los datos del aeropuerto.
 * @author PVita
 */
public class Aeropuerto
{
    // TODO - IMPLEMENTAR: Servirá para el servicio meteorológico.
    private String codigoIATA;
    private String nombre;
    // TODO - FORMATO: Se trata de una cadena de cinco caracteres numéricos. Si es internacional, el código será 00000.
    private int codigoMunicipio;

    /**
     * Crea un objeto del aeropuerto.
     * @param codigoIATA Código identificador del aeropuerto.
     * @param nombre Nombre del aeropuerto.
     * @param codigoMunicipio Código numérico del municipio sede del aeropuerto.
     */
    public Aeropuerto(String codigoIATA, String nombre, int codigoMunicipio)
    {
        this.codigoIATA = codigoIATA;
        this.nombre = nombre;
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getCodigoIATA()
    {
        return codigoIATA;
    }

    public void setCodigoIATA(String codigoIATA)
    {
        this.codigoIATA = codigoIATA;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int getCodigoMunicipio()
    {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(int codigoMunicipio)
    {
        this.codigoMunicipio = codigoMunicipio;
    }
}
