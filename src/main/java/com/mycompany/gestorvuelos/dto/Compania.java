package com.mycompany.gestorvuelos.dto;

/**
 * Almacena todos los datos referentes a la compañia aerea.
 *
 * @author PVita
 */
public class Compania
{
    /**
     * Crea una compañia aerea sin datos.
     */
    public Compania()
    {
        this.prefijo = 0;
        this.codigo = "";
        this.nombre = "";
        this.direccionSedeCentral = "";
        this.municipioSedeCentral = "";
        this.telefonoATC = 0;
        this.telefonoATA = 0;
    }

    /**
     * Crea un objeto de la compañia aerea.
     *
     * @param prefijo Código numérico entero positivo cuyo máximo es 999.
     * @param codigo Cadena de dos caracteres de tamaño fijo.
     * @param nombre Nombre de la compañia.
     * @param direccionSedeCentral Dirección de la sede central.
     * @param municipioSedeCentral Municipio de la sede central.
     * @param telefonoATC Teléfono de información al pasajero.
     * @param telefonoATA Teléfono de información a aeropuertos.
     */
    public Compania(int prefijo, String codigo, String nombre, String direccionSedeCentral, String municipioSedeCentral, int telefonoATC, int telefonoATA)
    {
        this.prefijo = prefijo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccionSedeCentral = direccionSedeCentral;
        this.municipioSedeCentral = municipioSedeCentral;
        this.telefonoATC = telefonoATC;
        this.telefonoATA = telefonoATA;
    }

    public int getPrefijo()
    {
        return prefijo;
    }

    public void setPrefijo(int prefijo)
    {
        this.prefijo = prefijo;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDireccionSedeCentral()
    {
        return direccionSedeCentral;
    }

    public void setDireccionSedeCentral(String direccionSedeCentral)
    {
        this.direccionSedeCentral = direccionSedeCentral;
    }

    public String getMunicipioSedeCentral()
    {
        return municipioSedeCentral;
    }

    public void setMunicipioSedeCentral(String municipioSedeCentral)
    {
        this.municipioSedeCentral = municipioSedeCentral;
    }

    public int getTelefonoATC()
    {
        return telefonoATC;
    }

    public void setTelefonoATC(int telefonoATC)
    {
        this.telefonoATC = telefonoATC;
    }

    public int getTelefonoATA()
    {
        return telefonoATA;
    }

    public void setTelefonoATA(int telefonoATA)
    {
        this.telefonoATA = telefonoATA;
    }

    // TODO - FORMATO: Código numérico entero positivo cuyo máximo es 999 (identificador).
    private int prefijo;
    // TODO - FORMATO: Puede contener dos letras mayúsculas o una mayúscula y un número (en este orden).
    // Ejemplos válidos: IB, V7
    // Ejemplos no válidos: 7V, ib, Ib, iB, 8a, 7-
    private String codigo;
    private String nombre;
    private String direccionSedeCentral;
    private String municipioSedeCentral;
    // TODO - FORMATO:  Se componen de un código de país con tres dígitos y el resto de teléfono contendrá un máximo de 12 dígitos más.
    private int telefonoATC;
    // TODO - FORMATO: Se componen de un código de país con tres dígitos y el resto de teléfono contendrá un máximo de 12 dígitos más.
    private int telefonoATA;
}
