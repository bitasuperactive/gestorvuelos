package com.mycompany.gestorvuelos.igu.interfaces;

import javax.swing.JButton;

/**
 * Los formularios que requieran ser validados deben implementar la 
 * recuperación del botón hace uso inmediato de sus campos.
 */
public interface ValidationFormulary
{
    /**
     * Recupera el botón que hace uso directo de los campos del formulario.
     * @return Botón principal del formulario.
     */
    public JButton getSubmitButton();
}
