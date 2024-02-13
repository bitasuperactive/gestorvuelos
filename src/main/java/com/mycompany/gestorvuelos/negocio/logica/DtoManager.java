package com.mycompany.gestorvuelos.negocio.logica;

import com.mycompany.gestorvuelos.dto.Compania;

/**
 * Compendio de funciones útiles para trabajar con objetos DTO.
 */
public class DtoManager
{
    /**
     * Sobrescribe los datos de la compañía origin con los de la compañía newSample.
     * @param origin Compañía a modificar.
     * @param newSample Compañía a implementar.
     */
    public static void overrideCompania(Compania origin, Compania newSample)
    {
        origin.setPrefijo(newSample.getPrefijo());
        origin.setCodigo(newSample.getCodigo());
        origin.setNombre(newSample.getNombre());
        origin.setDireccionSedeCentral(newSample.getDireccionSedeCentral());
        origin.setMunicipioSedeCentral(newSample.getMunicipioSedeCentral());
        origin.setTelefonoATA(newSample.getTelefonoATA());
        origin.setTelefonoATC(newSample.getTelefonoATC());
    }
}
