package com.mycompany.gestorvuelos.gui.interfaces;

/**
 * Los formularios que requieran ser validados deben implementar 
 * un método que permita comprobar si existen violaciones de validación.
 */
public interface ValidationFormulary
{
    /**
     * Comprueba si existen violaciones de validación.
     */
    public void checkIfFormularyIsValid();
}
