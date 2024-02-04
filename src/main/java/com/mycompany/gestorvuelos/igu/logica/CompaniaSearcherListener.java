/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestorvuelos.igu.logica;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.models.CompaniaTableModel;
import static com.mycompany.gestorvuelos.igu.logica.SearchTypeEnum.NOMBRE;
import static com.mycompany.gestorvuelos.igu.logica.SearchTypeEnum.PREFIJO;
import com.mycompany.gestorvuelos.negocio.logica.ListManager;
import com.mycompany.gestorvuelos.negocio.logica.Util;
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
 *
 * @author PVita
 */
public class CompaniaSearcherListener implements DocumentListener
{
    private final JComboBox comboBox;
    private final JTable table;

    public CompaniaSearcherListener(JComboBox comboBox, JTable table)
    {
        this.comboBox = comboBox;
        this.table = table;
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        updateTableModel(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        updateTableModel(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {

    }
    
    private void updateTableModel(DocumentEvent e)
    {
        Document document = e.getDocument();
        String text = "";
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(CompaniaSearcherListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (text.isEmpty()) {
            table.setModel(new CompaniaTableModel(Util.getListCompania(), true));
            return;
        }
        
        String searchTypeString = comboBox.getSelectedItem().toString();
        SearchTypeEnum searchType = SearchTypeEnum.valueOf(searchTypeString);
        List<Compania> listCompania = Util.getListCompania();
        
        switch (searchType) {
            case PREFIJO -> {
                try {
                    int prefijo = Integer.parseInt(text);
                    listCompania = ListManager.getListCompaniaByPrefijo(prefijo);
                } catch (NumberFormatException numberFormatException) {
                    System.err.println("Error de formato en la búsqueda.");
                }
            }
            case NOMBRE ->
                listCompania = ListManager.getListCompaniaByNombre(text);
        }
        
        table.setModel(new CompaniaTableModel(listCompania, true));
    }
}
