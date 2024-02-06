package com.mycompany.gestorvuelos.igu.logica;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.igu.MainFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Escuchador que rellena los campos de la compañía seleccionada.
 */
public class CompaniaListSelectionListener implements ListSelectionListener
{
    private final MainFrame frame;

    public CompaniaListSelectionListener(MainFrame frame)
    {
        this.frame = frame;
    }

    /**
     * Cuando cambia el valor de la selección, obtiene la compañía seleccionada
     * y llama al método MainFrame.fillCompaniaDetails().
     * <br> En caso de no haber selección, la compañía a rellenar es nula.
     * @param e El evento que caracteriza el cambio
     * @see MainFrame#fillCompaniaDetails(com.mycompany.gestorvuelos.dto.Compania) 
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JTable tCompaniaResults = frame.getTableCompaniaResults();
        var model = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania compania = null;
        try {
            compania = model.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            // En caso de excepción, la compañía a rellenar será nula.
        }
        frame.fillCompaniaDetails(compania);
    }

}
