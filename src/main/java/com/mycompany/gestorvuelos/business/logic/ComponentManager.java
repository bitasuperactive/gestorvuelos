package com.mycompany.gestorvuelos.business.logic;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * Compendio de funciones que facilita tratar con los componentes de la igu.
 */
public class ComponentManager
{
    /**
     * Recupera todos los componentes presentes en el contenedor especificado.
     * @param container Contenedor con los componentes a recuperar.
     * @return Todos los componentes presentes en el contenedor especificado.
     */
    public static List<Component> getAllComponents(final Container container)
    {
        Component[] components = container.getComponents();
        List<Component> listComponent = new ArrayList<>();
        
        for (Component comp : components) {
            listComponent.add(comp);
            if (comp instanceof Container subcontainer) {
                listComponent.addAll(getAllComponents(subcontainer));
            }
        }
        
        return listComponent;
    }
}
