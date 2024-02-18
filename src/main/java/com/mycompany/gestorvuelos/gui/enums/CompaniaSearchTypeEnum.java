package com.mycompany.gestorvuelos.gui.enums;

/**
 * Campos de filtrado permitidos para una lista de compañías.
 */
public enum CompaniaSearchTypeEnum
{
    PREFIJO, 
    NOMBRE,;
    
    /**
     * Transforma los valores del enumerador en un String Array, permitiéndo su uso en un JComboBox.
     * @return String Array de los valores del enumerador.
     */
    public static String[] valuesToString()
    {
        CompaniaSearchTypeEnum[] enumValues = CompaniaSearchTypeEnum.values();
        String[] enumStringValues = new String[enumValues.length];

        for (int i = 0; i < enumValues.length; i++) {
            enumStringValues[i] = enumValues[i].toString();
        }
        
        return enumStringValues;
    }
}
