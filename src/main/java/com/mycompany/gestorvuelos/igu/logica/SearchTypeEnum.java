package com.mycompany.gestorvuelos.igu.logica;

/**
 *
 * @author PVita
 */
public enum SearchTypeEnum
{
    PREFIJO, 
    NOMBRE,;
    
    public static String[] valuesToString()
    {
        SearchTypeEnum[] enumValues = SearchTypeEnum.values();
        String[] enumStringValues = new String[enumValues.length];

        for (int i = 0; i < enumValues.length; i++) {
            enumStringValues[i] = enumValues[i].toString();
        }
        
        return enumStringValues;
    }
    
//    public static SearchTypeEnum stringToEnum(String str)
//    {
//        String[] values = valuesToString();
//        for (int i = 0; i < values.length; i++)
//        {
//            if (str.equals(values[i]))
//                return SearchTypeEnum.values()[i];
//        }
//        
//        return null;
//    }
}
