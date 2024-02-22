package com.mycompany.gestorvuelos.gui.models;

import com.mycompany.gestorvuelos.business.logic.DateUtils;
import com.mycompany.gestorvuelos.dto.VueloDiario;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabla para los vuelos diarios.
 */
public class VueloDiarioTableModel extends AbstractTableModel
{
    private List<VueloDiario> listVueloDiario;
    private String[] columnNames = {"Vuelo Base", "Fecha Salida", "Hora Salida", "Hora Llegada", "Plazas Ocupadas", "Precio Plaza"};

    public VueloDiarioTableModel(List<VueloDiario> listVueloDiario)
    {
        this.listVueloDiario = listVueloDiario;
    }
    
    @Override
    public int getRowCount()
    {
        return listVueloDiario.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        VueloDiario vueloDiario = listVueloDiario.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return vueloDiario.getVueloBase().getCodigo();
            case 1:
                return DateUtils.shortDate(vueloDiario.getFechaSalida());
            case 2:
                return DateUtils.dateToHour(vueloDiario.getHoraSalida());
            case 3:
                return DateUtils.dateToHour(vueloDiario.getHoraLlegada());
            case 4:
                return vueloDiario.getPlazasOcupadas();
            case 5:
                return vueloDiario.getPrecioPlaza();
            default:
                throw new IndexOutOfBoundsException(
                        String.format(
                                "La columna %d no existe en este modelo.",
                                columnIndex)
                );
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
