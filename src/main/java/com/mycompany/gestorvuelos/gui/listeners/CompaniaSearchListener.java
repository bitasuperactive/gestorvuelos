package com.mycompany.gestorvuelos.gui.listeners;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.gui.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.gui.enums.CompaniaSearchTypeEnum;
import com.mycompany.gestorvuelos.business.logic.ListManager;
import com.mycompany.gestorvuelos.business.logic.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Escuchador para el filtrado de la tabla de compañías.
 * @see CompaniaTableModel
 */
public class CompaniaSearchListener implements DocumentListener
{
    private final JComboBox CB_SEARCH_TYPE;
    private final JTable TABLE;
    private final boolean SHORTER_MODEL;

    /**
     * Constructor por defecto.
     * @param cbSearchType Tipo de campo a filtrar (Prefijo/Nombre).
     * @param table Tabla a actualizar.
     * @param shorterModel Versión de la tabla.
     */
    public CompaniaSearchListener(JComboBox cbSearchType, JTable table, boolean shorterModel)
    {
        this.CB_SEARCH_TYPE = cbSearchType;
        this.TABLE = table;
        this.SHORTER_MODEL = shorterModel;
    }

    /**
     * @see  updateTableModel(DocumentEvent e)
     */
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        updateTableModel(e);
    }

    /**
     * @see  updateTableModel(DocumentEvent e)
     */
    @Override
    public void removeUpdate(DocumentEvent e)
    {
        updateTableModel(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {

    }
    
    /**
     * Actualiza la tabla de compañías en base al tipo de filtrado seleccionado 
 en el JComboBox CB_SEARCH_TYPE y al valor introducido en el documento del JTextField.
     * @param e Evento del documento (insert/remove).
     * @see ListManager#getListCompaniaByPrefijo(int)
     * @see ListManager#getListCompaniaByNombre(java.lang.String)
     */
    private void updateTableModel(DocumentEvent e)
    {
        Document document = e.getDocument();
        String text = "";
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(CompaniaSearchListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (text.isEmpty()) {
            TABLE.setModel(new CompaniaTableModel(Util.getListCompania(), SHORTER_MODEL));
            return;
        }
        
        String searchTypeString = CB_SEARCH_TYPE.getSelectedItem().toString();
        CompaniaSearchTypeEnum searchType = CompaniaSearchTypeEnum.valueOf(searchTypeString);
        List<Compania> listCompania = new ArrayList<>();
        
        switch (searchType) {
            case PREFIJO -> {
                try {
                    short prefijo = Short.parseShort(text);
                    listCompania = ListManager.getListCompaniaByPrefijo(prefijo);
                } catch (NumberFormatException ex) {
                    System.err.println("NumberFormatException " + ex.getMessage());
                }
            }
            case NOMBRE ->
                listCompania = ListManager.getListCompaniaByNombre(text);
        }
        
        TABLE.setModel(new CompaniaTableModel(listCompania, SHORTER_MODEL));
    }
}
