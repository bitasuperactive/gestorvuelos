package com.mycompany.gestorvuelos.dto;

import jakarta.validation.constraints.*;



/**
 * Almacena todos los datos referentes a la compañia aerea con sus
 * correspondientes etiquetas de validación.
 */
public class Compania
{
    /**
     * Identificador único de la compañía.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Comprendido entre 1 y 999.
     * </pre>
     */
    @NotNull(message = "El prefijo es un campo obligatorio.")
    @Digits(integer = 3, fraction = 0)
    @Min(value = 1, message = "El prefijo debe ser positivo mayor a 0.")
    @Max(value = 999, message = "El prefijo no puede ser mayor a 999.")
    private Short prefijo;
    
    /**
     * Identificador único inscrito en la IATA.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Debe ser una cadena de dos caracteres mayúsculas de tamaño fijo, puede contener dos letras mayúsculas o una mayúscula y un número (en este orden).
     * </pre>
     */
    @NotBlank(message = "El código es un campo obligatorio")
    @Pattern(regexp = "^([A-Z]{2}|[A-Z][0-9])$",
            message = "El código debe ser una cadena de dos caracteres de tamaño fijo, "
                    + "puede contener dos letras mayúsculas o una mayúscula y un número (en este orden)")
    @Size(max = 2)
    private String codigo;
    
    /**
     * Nombre de la compañía.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Máximo 40 caracteres.
     * </pre>
     */
    @NotBlank(message = "El nombre es un campo obligatorio")
    @Size(max = 40, message = "El nombre está limitado a 40 caracteres.")
    private String nombre;
    
    /**
     * Dirección de la sede central de la compañía.
     * <pre>Restricciones:
     *      - Máximo 60 caracteres.
     * </pre>
     */
    @Size(max = 60, message = "La dirección está limitada a 60 caracteres.")
    private String direccionSedeCentral;
    
    // TODO - Implementar restricción personalizada: Debe estar contemplado en el archivo CSV de municipios.
    /**
     * Municipio de la sede central de la compañía.
     */
    private String municipioSedeCentral;
    
    // Se compone de un código de país con tres dígitos, un espacio a continuación
    // y el resto de teléfono contendrá entre 7 y 12 dígitos más.
    /**
     * Teléfono de atención a aeropuertos.
     * <pre>Restricciones:
     *      - Debe estar compuesto de un código de país con tres dígitos, un espacio y el resto de teléfono contendrá un máximo de 12 dígitos más.
     * </pre>
     */
    @Pattern(regexp = "^(\\+[0-9]{3}\\x20[0-9]{7,12})|$", 
            message = "TelefonoATA debe ser un número interncional siguiendo la estructura: +000 1020304")
    @Size(max = 17)
    private String telefonoATA;
    
    /**
     * Teléfono de atención al pasajero.
     * <pre>Restricciones:
     *      - Debe estar compuesto de un código de país con tres dígitos, un espacio y el resto de teléfono contendrá un máximo de 12 dígitos más.
     * </pre>
     */
    @Pattern(regexp = "^(\\+[0-9]{3}\\x20[0-9]{7,12})|$",
            message = "TelefonoATC debe ser un número interncional siguiendo la estructura: +000 1020304")
    @Size(max = 17)
    private String telefonoATC;
    
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
        this.telefonoATC = "";
        this.telefonoATA = "";
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
    public Compania(short prefijo, String codigo, String nombre, String direccionSedeCentral, String municipioSedeCentral, String telefonoATA, String telefonoATC)
    {
        this.prefijo = prefijo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccionSedeCentral = direccionSedeCentral;
        this.municipioSedeCentral = municipioSedeCentral;
        this.telefonoATA = telefonoATA;
        this.telefonoATC = telefonoATC;
    }

    public short getPrefijo()
    {
        return prefijo;
    }

    public void setPrefijo(short prefijo)
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

    public String getTelefonoATA()
    {
        return telefonoATA;
    }

    public void setTelefonoATA(String telefonoATA)
    {
        this.telefonoATA = telefonoATA;
    }

    public String getTelefonoATC()
    {
        return telefonoATC;
    }

    public void setTelefonoATC(String telefonoATC)
    {
        this.telefonoATC = telefonoATC;
    }
}
