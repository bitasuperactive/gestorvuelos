package com.mycompany.gestorvuelos.gui.models;

import com.mycompany.gestorvuelos.business.logic.DateUtils;
import com.mycompany.gestorvuelos.dto.VueloBase;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabla para los vuelos base.
 */
public class VueloBaseTableModel extends AbstractTableModel
{
    private List<VueloBase> listVueloBase;
    private String[] columnNames = {"Código", "Aeropuerto Origen", "Aeropuerto Destino", "Plazas", "Hora Salida", "Hora Llegada", "Dias Operación"};

    /**
     * Crea un modelo completo de la lista de vuelos base.
     * @param listVueloBase Lista de vuelos base a mostrar.
     */
    public VueloBaseTableModel(List<VueloBase> listVueloBase)
    {
        this.listVueloBase = listVueloBase;
    }
    
    /**
     * Obtiene el vuelo base correspondiente a la fila del modelo especificada.
     * @param rowIndex Fila en el modelo.
     * @return Vuelo base correspondiente.
     */
    public VueloBase getVueloBaseAt(int rowIndex)
    {
        return listVueloBase.get(rowIndex);
    }

    @Override
    public int getRowCount()
    {
        return listVueloBase.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        VueloBase vueloBase = listVueloBase.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return vueloBase.getCodigo();
            case 1:
                return vueloBase.getAeropuertoOrigen().getCodigoIATA();
            case 2:
                return vueloBase.getAeropuertoDestino().getCodigoIATA();
            case 3:
                return vueloBase.getPlazas();
            case 4:
                return DateUtils.dateToHour(vueloBase.getHoraSalida());
            case 5:
                return DateUtils.dateToHour(vueloBase.getHoraLlegada());
            case 6:
                return vueloBase.getDiasOperacion();
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
