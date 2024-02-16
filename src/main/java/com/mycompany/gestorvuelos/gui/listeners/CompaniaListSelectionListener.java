package com.mycompany.gestorvuelos.gui.listeners;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.gui.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.gui.CompaniasManagerFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Escuchador que rellena los campos de la compañía seleccionada.
 * Presenta alto grado de acoplamiento con la implementación específica
 * de la clase CompaniasManagerFrame.
 * @see CompaniasManagerFrame
 */
public class CompaniaListSelectionListener implements ListSelectionListener
{
    private final CompaniasManagerFrame companiasManagerFrame;

    /**
     * Escuchador que rellena los campos de la compañía seleccionada.
     * @param companiasManagerFrame Implementación específica de CompaniasManagerFrame.
     */
    public CompaniaListSelectionListener(CompaniasManagerFrame companiasManagerFrame)
    {
        this.companiasManagerFrame = companiasManagerFrame;
    }

    /**
     * Cuando cambia el valor de la selección, obtiene la compañía seleccionada
     * y llama al método fillCompaniaDetails() de CompaniasManagerFrame.
     * <br> En caso de no haber selección, la compañía a rellenar es nula.
     * @param e El evento que caracteriza el cambio
     * @see CompaniasManagerFrame#fillCompaniaDetails(com.mycompany.gestorvuelos.dto.Compania) 
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting()) {
            return;
        }
        
        var tCompaniaResults = companiasManagerFrame.getTableCompaniaResults();
        var model = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania compania;
        
        try {
            compania = model.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            compania = null;
        }
        
        companiasManagerFrame.fillCompaniaDetails(compania);
    }

}
