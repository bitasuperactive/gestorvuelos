package com.mycompany.gestorvuelos.dto.models;

import com.mycompany.gestorvuelos.dto.Compania;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabla personalizado para mostrar los campos del objeto Compania.
 * @see Compania
 */
public class CompaniaTableModel extends AbstractTableModel
{
    private List<Compania> listCompania;
    private String[] columnNames = {"Prefijo", "Código", "Nombre", "Dirección Sede Central", "Municipio Sede Central", "Teléfono ATC", "Teléfono ATA"};
    private boolean shorterModel;
    
    /**
     * Crea un modelo completo de la lista de compañías.
     * @param listCompania Lista de compañías a mostrar.
     */
    public CompaniaTableModel(List<Compania> listCompania)
    {
        this.listCompania = listCompania;
    }

    /**
     * Crea un modelo completo o acortado de la lista de compañías.
     * @param listCompania Lista de compañías a mostrar.
     * @param shorterModel Si es verdadero, crea un modelo acortado solo con las columnas "Prefijo" y "Nombre".
     * Si es falso, crea un modelo completo.
     */
    public CompaniaTableModel(List<Compania> listCompania, boolean shorterModel)
    {
        this.listCompania = listCompania;
        // La versión acortada de la tabla solo tiene las columnas "Prefijo" y "Nombre".
        if (this.shorterModel = shorterModel) {
            columnNames = new String[] {columnNames[0], columnNames[2]};
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

    /**
     * @throws IndexOutOfBoundsException Si el objeto correspondiente al 
     * rowIndex y al columnIndex no existe
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException
    {
        Compania compania = listCompania.get(rowIndex);
        // Para obtener el prefijo (0) y el nombre (2).
        if (shorterModel)
            columnIndex *= 2;
        
        switch(columnIndex)
        {
            case 0 -> {
                return compania.getPrefijo();
            }
            case 1 -> {
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
        if (column >= columnNames.length)
            return "";
        
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
}
