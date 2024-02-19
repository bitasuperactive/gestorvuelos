package com.mycompany.gestorvuelos.dto;

/**
 * Almacena los datos del aeropuerto.
 */
public class Aeropuerto
{
    /**
     * Crea un aeropuerto sin datos.
     */
    public Aeropuerto()
    {
        this.codigoIATA = "";
        this.nombre = "";
        this.codigoMunicipio = 0;
    }

    /**
     * Crea un aeropuerto.
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

    private String codigoIATA;
    private String nombre;
    // TODO - FORMATO: Se trata de una cadena de cinco caracteres numéricos. Si es internacional, el código será 00000.
    private int codigoMunicipio;
}
