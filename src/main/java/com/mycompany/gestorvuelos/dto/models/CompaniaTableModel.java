package com.mycompany.gestorvuelos.dto.models;

import com.mycompany.gestorvuelos.dto.Compania;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PVita
 */
public class CompaniaTableModel extends AbstractTableModel
{
    private List<Compania> listCompania;
    private String[] columnNames = {"Prefijo", "Código", "Nombre", "Dirección Sede Central", "Municipio Sede Central", "Teléfono ATC", "Teléfono ATA"};
    private boolean shorterTable;
    
    public CompaniaTableModel(List<Compania> listCompania)
    {
        this.listCompania = listCompania;
    }

    public CompaniaTableModel(List<Compania> listCompania, boolean shorterTable)
    {
        this.listCompania = listCompania;
        if (this.shorterTable = shorterTable) {
            columnNames = new String[]{"Prefijo", "Nombre"};
        }
    }

    @Override
    public int getRowCount()
    {
        return listCompania.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException
    {
        Compania compania = listCompania.get(rowIndex);
        switch(columnIndex)
        {
            case 0 -> {
                return compania.getPrefijo();
            }
            case 1 -> {
                if (shorterTable)
                    return compania.getNombre();
                return compania.getCodigo();
            }
            case 2 -> {
                return compania.getNombre();
            }
            case 3 -> {
                return compania.getDireccionSedeCentral();
            }
            case 4 -> {
                return compania.getMunicipioSedeCentral();
            }
            case 5 -> {
                return compania.getTelefonoATC();
            }
            case 6 -> {
                return compania.getTelefonoATA();
            }
            default -> throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
}
