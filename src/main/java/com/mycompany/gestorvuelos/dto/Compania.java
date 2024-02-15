package com.mycompany.gestorvuelos.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import com.mycompany.gestorvuelos.igu.validation.Unique;

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
     *      - Único.
     * </pre>
     */
    @NotNull(message = "Campo obligatorio.")
    @Digits(integer = 3, fraction = 0)
    @Min(value = 1, message = "Debe ser positivo mayor a 0.")
    @Max(value = 999, message = "No puede ser mayor a 999.")
    @Unique
    private Short prefijo;
    
    /**
     * Identificador único inscrito en la IATA.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Debe ser una cadena de dos caracteres mayúsculas de tamaño fijo, puede contener dos letras mayúsculas o una mayúscula y un número (en este orden).
     *      - Único.
     * </pre>
     */
    @NotBlank(message = "Campo obligatorio")
    @Pattern(regexp = "^([A-Z]{2}|[A-Z][0-9])$",
            message = "Debe ser una cadena de dos caracteres en mayúsculas o"
                    + " un caracter en mayúscula y un número.")
    @Size(max = 2)
    @Unique
    private String codigo;
    
    /**
     * Nombre de la compañía.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Máximo 40 caracteres.
     * </pre>
     */
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 40, message = "Limitado a 40 caracteres.")
    private String nombre;
    
    /**
     * Dirección de la sede central de la compañía.
     * <pre>Restricciones:
     *      - Máximo 60 caracteres.
     * </pre>
     */
    @Size(max = 60, message = "Limitada a 60 caracteres.")
    private String direccionSedeCentral;
    
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
            message = "Debe ser un número internacional siguiendo la estructura: "
                    + "+000 1020304...")
    @Size(max = 17)
    private String telefonoATA;
    
    /**
     * Teléfono de atención al pasajero.
     * <pre>Restricciones:
     *      - Debe estar compuesto de un código de país con tres dígitos, un espacio y el resto de teléfono contendrá un máximo de 12 dígitos más.
     * </pre>
     */
    @Pattern(regexp = "^(\\+[0-9]{3}\\x20[0-9]{7,12})|$",
            message = "Debe ser un número interncional siguiendo la estructura: "
                    + "+000 1020304...")
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

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Compania companiaToCompare = (Compania) obj;
        return prefijo == companiaToCompare.prefijo &&
               Objects.equals(codigo, companiaToCompare.codigo) &&
               Objects.equals(nombre, companiaToCompare.nombre) &&
               Objects.equals(direccionSedeCentral, companiaToCompare.direccionSedeCentral) &&
               Objects.equals(municipioSedeCentral, companiaToCompare.municipioSedeCentral) &&
               Objects.equals(telefonoATA, companiaToCompare.telefonoATA) &&
               Objects.equals(telefonoATC, companiaToCompare.telefonoATC);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(prefijo, codigo, nombre, direccionSedeCentral, municipioSedeCentral, telefonoATA, telefonoATC);
    }
    
    /**
     * Sobrescribe todos los campos de la compañía.
     * @param newCompania Compañía a implementar.
     */
    public void override(Compania newCompania) {
        this.prefijo = newCompania.prefijo;
        this.codigo = newCompania.codigo;
        this.nombre = newCompania.nombre;
        this.direccionSedeCentral = newCompania.direccionSedeCentral;
        this.municipioSedeCentral = newCompania.municipioSedeCentral;
        this.telefonoATA = newCompania.telefonoATA;
        this.telefonoATC = newCompania.telefonoATC;
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
