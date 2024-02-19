package com.mycompany.gestorvuelos.dto;

import com.mycompany.gestorvuelos.gui.interfaces.MonoChecks;
import com.mycompany.gestorvuelos.gui.validation.compania.NonOrAllOptionalFields;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import com.mycompany.gestorvuelos.gui.validation.compania.Prefijo;
import com.mycompany.gestorvuelos.gui.validation.compania.Codigo;
import jakarta.validation.constraints.Digits;

/**
 * Almacena todos los datos referentes a la compañia aerea con sus
 * correspondientes etiquetas de validación.
 */
@NonOrAllOptionalFields
public class Compania
{
    /**
     * Identificador único de la compañía.
     * @see com.mycompany.gestorvuelos.gui.validation.compania.Prefijo
     */
    @Digits(integer = 3, fraction = 0, groups = MonoChecks.class)
    @Prefijo(groups = MonoChecks.class)
    private Short prefijo;
    
    /**
     * Identificador único inscrito en la IATA.
     * @see com.mycompany.gestorvuelos.gui.validation.compania.Codigo
     */
    @Size(max = 2, groups = MonoChecks.class)
    @Codigo(groups = MonoChecks.class)
    private String codigo;
    
    /**
     * Nombre de la compañía.
     * <pre>Restricciones:
     *      - Obligatorio.
     *      - Máximo 40 caracteres.
     * </pre>
     */
    @NotBlank(message = "Campo obligatorio", groups = MonoChecks.class)
    @Size(max = 40, message = "Limitado a 40 caracteres", groups = MonoChecks.class)
    private String nombre;
    
    /**
     * Dirección de la sede central de la compañía.
     * <pre>Restricciones:
     *      - Máximo 60 caracteres.
     * </pre>
     */
    @Size(max = 60, message = "Limitado a 60 caracteres", groups = MonoChecks.class)
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
            message = "Debe ser un número internacional: +000 1020304...", 
            groups = MonoChecks.class)
    @Size(max = 17, groups = MonoChecks.class)
    private String telefonoATA;
    
    /**
     * Teléfono de atención al pasajero.
     * <pre>Restricciones:
     *      - Debe estar compuesto de un código de país con tres dígitos, un espacio y el resto de teléfono contendrá un máximo de 12 dígitos más.
     * </pre>
     */
    @Pattern(regexp = "^(\\+[0-9]{3}\\x20[0-9]{7,12})|$",
            message = "Debe ser un número interncional: +000 1020304...",
            groups = MonoChecks.class)
    @Size(max = 17, groups = MonoChecks.class)
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
        return Objects.equals(prefijo, companiaToCompare.prefijo) &&
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
