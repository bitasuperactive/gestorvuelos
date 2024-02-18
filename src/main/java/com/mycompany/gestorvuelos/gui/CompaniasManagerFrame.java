package com.mycompany.gestorvuelos.gui;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.gui.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.gui.listeners.CompaniaListSelectionListener;
import com.mycompany.gestorvuelos.gui.logic.CompaniaSearchTypeEnum;
import com.mycompany.gestorvuelos.gui.logic.MaxCharsDocumentFilter;
import com.mycompany.gestorvuelos.business.logic.Util;
import com.mycompany.gestorvuelos.gui.interfaces.ValidationFormulary;
import com.mycompany.gestorvuelos.gui.listeners.CompaniaValidatorDocumentListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.text.AbstractDocument;

/**
 * Formulario de gestión de compañías aéreas.
 */
public class CompaniasManagerFrame extends javax.swing.JFrame implements ValidationFormulary
{
    /**
     * Crea un nuevo formulario CompaniasManagerFrame.
     */
    public CompaniasManagerFrame()
    {
        initUtils();
        initComponents();
        setupDocumentListeners();
    }
    
    @Override
    public void checkIfFormularyIsValid()
    {
        // Habilitamos el botón guardar cambios si hay una compañía seleccionada
        // y todos los campos son válidos.
        try {
            getSelectedCompania();
            bSaveChangesCompania.setEnabled(allFieldsAreValid());
        } catch (NullPointerException ex) {
            // No se ha seleccionado ninguna compañía.
        }
    }
    
    // TODO - Evitar modificador público.
    /**
     * Rellena los campos correspondientes del panel pCompaniaDetails con
     * los datos de la compañías especificada.
     * @param compania Compañía a mostrar.
     */
    public void fillCompaniaDetails(Compania compania)
    {
        // Habilitamos los botones del panel pCompaniaDetails si compania no es null.
        bSaveChangesCompania.setEnabled(compania != null);
        bShutdownCompania.setEnabled(compania != null);
        
        // Si compania es null utilizamos el constructor por defecto.
        compania = (compania == null) ? new Compania() : compania;
        
        tfPrefijo.setText(String.valueOf(compania.getPrefijo()));
        tfCodigo.setText(compania.getCodigo());
        tfNombre.setText(compania.getNombre());
        tfDireccionSedeCentral.setText(compania.getDireccionSedeCentral());
        
        Object municipio = compania.getMunicipioSedeCentral().isEmpty() ? null : compania.getMunicipioSedeCentral();
        cbMunicipioSedeCentral.setSelectedItem(municipio);
        
        tfTelefonoATC.setText(compania.getTelefonoATC());
        tfTelefonoATA.setText(compania.getTelefonoATA());
    }
    
    /**
     * Obtiene el JTable que lista el registro de compañías.
     * @return JTable de compañías.
     */
    public JTable getTableCompaniaResults()
    {
        return tCompaniaResults;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Constructor methods">
    private void initUtils()
    {
        try {
            // TODO - Obtener el codigo IATA del aeropuerto base almacenado por el usuario.
            Util.initUtils("ABC");
        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(CompaniasManagerFrame.class.getName()).log(Level.SEVERE, 
                    "Excepción de inicialización.", 
                    ex);
            this.dispose();
            System.exit(1);
        }
    }
    
    /**
     * Establece los listeners MaxCharsDocumentFilter y 
     * CompaniaValidatorDocumentListener para los campos modificables del panel
     * pCompaniaDetails.
     * @see MaxCharsDocumentFilter
     * @see CompaniaValidatorDocumentListener
     */
    private void setupDocumentListeners()
    {
        // Establecer límites de caracteres.
        ((AbstractDocument) tfDireccionSedeCentral.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(Compania.class, "direccionSedeCentral", lDireccionSedeCentralWarning));
        ((AbstractDocument) tfTelefonoATA.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(Compania.class, "telefonoATA", lTelefonoATAWarning));
        ((AbstractDocument) tfTelefonoATC.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(Compania.class, "telefonoATC", lTelefonoATCWarning));

        // Establecer validadores.
        tfDireccionSedeCentral.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "direccionSedeCentral", lDireccionSedeCentralWarning));
        tfTelefonoATA.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "telefonoATA", lTelefonoATAWarning));
        tfTelefonoATC.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "telefonoATC", lTelefonoATCWarning));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Button methods">
    /**
     * Guarda los cámbios realizados sobre la compañía seleccionada.
     * @throws NullPointerException Si no se ha seleccionado ninguna compañía del listado.
     * @see getSelectedCompania
     * @see getCompaniaFromFields
     * @see Compania#override(Compania)
     */
    private void saveChangesToCompania() throws NullPointerException
    {
        // Obtenemos la compañía seleccionada.
        Compania selectedCompania = getSelectedCompania();
        
        // Generamos una nueva compañía validada a partir de los datos modificados.
        Compania newCompania;
        try {
            newCompania = getCompaniaFromFields();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, 
                    ex.getMessage(), 
                    this.getTitle(), 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Comprobamos si han habido cambios.
        if (selectedCompania.equals(newCompania))
        {
            JOptionPane.showMessageDialog(this, "No se ha realizado ningún cambio.",
                    this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Guardamos los datos validados en la compañía seleccionada.
        selectedCompania.override(newCompania);
        
        String message = String.format("Compañía [ %s ] modificada con éxito.", 
                selectedCompania.getNombre());
        JOptionPane.showMessageDialog(this, message,
                this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Elimina al compañía seleccionada del listado de compañías.
     * @throws NullPointerException Si no se ha seleccionado ninguna compañía del listado.
     * @see getSelectedCompania
     * @see CompaniaTableModel#removeCompania(com.mycompany.gestorvuelos.dto.Compania)
     */
    private void shutdownCompania() throws NullPointerException
    {
        // Obtenemos compañía seleccionada.
        Compania compania = getSelectedCompania();
        
        String nombreCompania = compania.getNombre();
        
        var model = (CompaniaTableModel) tCompaniaResults.getModel();
        try {
            model.removeCompania(compania);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }

        // Vaciar los detalles de la compañía.
        fillCompaniaDetails(null);
        
        String message = String.format("Compañía [ %s ] dada de baja con éxito.", 
                nombreCompania);
        JOptionPane.showMessageDialog(this, message, 
                this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }
    // </editor-fold>

    /**
     * Recupera la compañía seleccionada de la tabla de compañías disponibles.
     * @return Compañía seleccionada.
     * @throws NullPointerException Si no se ha seleccionado ninguna compañía del listado.
     */
    private Compania getSelectedCompania() throws NullPointerException
    {
        var companiaTableModel = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania compania;
        
        try {
            compania = companiaTableModel.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            // En caso de no haber una compañía seleccionada, 
            // los botones deberían estar deshabilitados.
            throw new NullPointerException("No se ha seleccionado ninguna compañía de la tabla.");
        }
        
        return compania;
    }
    
    /**
     * Comprueba que todas las etiquetas correspondientes a los mensajes de
     * validación esten vacíos, confirmando que los campos del formulario son válidos.
     * @return Verdadero si el formulario es valido, falso en su defecto.
     */
    private boolean allFieldsAreValid()
    {
        return lDireccionSedeCentralWarning.getText().isEmpty() &&
                lTelefonoATAWarning.getText().isEmpty() &&
                lTelefonoATCWarning.getText().isEmpty();
    }
    
    /**
     * Crea una nueva compañía a partir de los campos rellenados en el panel
     * pCompaniaDetails. Los campos deben ser válidos.
     * @return Compañía validada.
     * @throws IllegalArgumentException Si existen violaciones de validación en el formulario.
     */
    private Compania getCompaniaFromFields() throws IllegalArgumentException
    {
        if (!allFieldsAreValid()) {
            throw new IllegalArgumentException("Existen violaciones de validación en el formulario.");
        }
        
        Short prefijo;
        try {
            prefijo = Short.valueOf(tfPrefijo.getText());
        } catch (NumberFormatException ex) {
            prefijo = null;
        }
        
        String codigo = tfCodigo.getText();
        String nombre = tfNombre.getText();
        String direccionSedeCentral = tfDireccionSedeCentral.getText();
        
        Object municipio = cbMunicipioSedeCentral.getSelectedItem();
        String municipioSedeCentral = municipio == null ? "" : (String) municipio;
        
        String telefonoATA = tfTelefonoATA.getText();
        String telefonoATC = tfTelefonoATC.getText();
        
        return new Compania(prefijo, codigo, nombre, direccionSedeCentral, municipioSedeCentral, telefonoATA, telefonoATC);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pCompaniaDetails = new javax.swing.JPanel();
        pCriticalData = new javax.swing.JPanel();
        lPrefijo = new javax.swing.JLabel();
        tfPrefijo = new javax.swing.JTextField();
        lCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        lNombre = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        pData = new javax.swing.JPanel();
        pDireccionSedeCentral = new javax.swing.JPanel();
        pDireccionSedeCentralInput = new javax.swing.JPanel();
        lDireccionSedeCentral = new javax.swing.JLabel();
        tfDireccionSedeCentral = new javax.swing.JTextField();
        pDireccionSedeCentralWarning = new javax.swing.JPanel();
        lDireccionSedeCentralWarning = new javax.swing.JLabel();
        pMunicipioSedeCentral = new javax.swing.JPanel();
        lMunicipioSedeCentral = new javax.swing.JLabel();
        cbMunicipioSedeCentral = new javax.swing.JComboBox<>();
        pTelefonoATA = new javax.swing.JPanel();
        pTelefonoATASub = new javax.swing.JPanel();
        lTelefonoATA = new javax.swing.JLabel();
        tfTelefonoATA = new javax.swing.JTextField();
        lTelefonoATAWarning = new javax.swing.JLabel();
        pTelefonoATC = new javax.swing.JPanel();
        pTelefonoATCSub = new javax.swing.JPanel();
        lTelefonoATC = new javax.swing.JLabel();
        tfTelefonoATC = new javax.swing.JTextField();
        lTelefonoATCWarning = new javax.swing.JLabel();
        pActions = new javax.swing.JPanel();
        bShutdownCompania = new javax.swing.JButton();
        bSaveChangesCompania = new javax.swing.JButton();
        pCompanias = new javax.swing.JPanel();
        pResults = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tCompaniaResults = new javax.swing.JTable();
        pBuscador = new javax.swing.JPanel();
        cbCompaniaSearchType = new javax.swing.JComboBox<>();
        tfCompaniaSearcher = new javax.swing.JTextField();
        bRegisterCompania = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compañías aéreas");
        setMinimumSize(new java.awt.Dimension(600, 880));
        setPreferredSize(new java.awt.Dimension(600, 880));

        pCompaniaDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la compañía", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompaniaDetails.setLayout(new javax.swing.BoxLayout(pCompaniaDetails, javax.swing.BoxLayout.Y_AXIS));

        pCriticalData.setBackground(new java.awt.Color(232, 232, 232));
        pCriticalData.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        pCriticalData.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        lPrefijo.setText("Prefijo:");
        pCriticalData.add(lPrefijo);

        tfPrefijo.setBackground(new java.awt.Color(232, 232, 232));
        tfPrefijo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfPrefijo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfPrefijo.setMinimumSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setPreferredSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setEditable(false);
        pCriticalData.add(tfPrefijo);

        lCodigo.setText("Código:");
        pCriticalData.add(lCodigo);

        tfCodigo.setBackground(new java.awt.Color(232, 232, 232));
        tfCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfCodigo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfCodigo.setMinimumSize(new java.awt.Dimension(38, 22));
        tfCodigo.setPreferredSize(new java.awt.Dimension(38, 22));
        tfCodigo.setEditable(false);
        pCriticalData.add(tfCodigo);

        lNombre.setText("Nombre:");
        pCriticalData.add(lNombre);

        tfNombre.setBackground(new java.awt.Color(232, 232, 232));
        tfNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfNombre.setMinimumSize(new java.awt.Dimension(272, 26));
        tfNombre.setPreferredSize(new java.awt.Dimension(272, 26));
        tfNombre.setEditable(false);
        pCriticalData.add(tfNombre);

        pCompaniaDetails.add(pCriticalData);

        pData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        pData.setMinimumSize(new java.awt.Dimension(540, 140));
        pData.setPreferredSize(new java.awt.Dimension(540, 168));
        pData.setLayout(new javax.swing.BoxLayout(pData, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentral.setLayout(new javax.swing.BoxLayout(pDireccionSedeCentral, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentralInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        pDireccionSedeCentralInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 5));

        lDireccionSedeCentral.setText("Dirección sede central:");
        lDireccionSedeCentral.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        pDireccionSedeCentralInput.add(lDireccionSedeCentral);

        tfDireccionSedeCentral.setMinimumSize(new java.awt.Dimension(392, 26));
        tfDireccionSedeCentral.setPreferredSize(new java.awt.Dimension(392, 26));
        pDireccionSedeCentralInput.add(tfDireccionSedeCentral);

        pDireccionSedeCentral.add(pDireccionSedeCentralInput);

        pDireccionSedeCentralWarning.setLayout(new java.awt.CardLayout());

        lDireccionSedeCentralWarning.setForeground(new java.awt.Color(204, 51, 0));
        lDireccionSedeCentralWarning.setText("Warning message");
        lDireccionSedeCentralWarning.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lDireccionSedeCentralWarning.setText("");
        pDireccionSedeCentralWarning.add(lDireccionSedeCentralWarning, "card2");

        pDireccionSedeCentral.add(pDireccionSedeCentralWarning);

        pData.add(pDireccionSedeCentral);

        pMunicipioSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lMunicipioSedeCentral.setText("Municipio sede central:");
        pMunicipioSedeCentral.add(lMunicipioSedeCentral);

        String[] municipioSedeCentralItems = Util.getMapMunicipios().keySet().toArray(new String[0]);
        cbMunicipioSedeCentral.setModel(new DefaultComboBoxModel<>(municipioSedeCentralItems));
        cbMunicipioSedeCentral.setSelectedItem(null);
        pMunicipioSedeCentral.add(cbMunicipioSedeCentral);

        pData.add(pMunicipioSedeCentral);

        pTelefonoATA.setLayout(new javax.swing.BoxLayout(pTelefonoATA, javax.swing.BoxLayout.LINE_AXIS));

        pTelefonoATASub.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lTelefonoATA.setText("Teléfono ATA:");
        pTelefonoATASub.add(lTelefonoATA);

        tfTelefonoATA.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATA.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATA.setPreferredSize(new java.awt.Dimension(130, 22));
        pTelefonoATASub.add(tfTelefonoATA);

        lTelefonoATAWarning.setForeground(new java.awt.Color(204, 51, 0));
        lTelefonoATAWarning.setText("Warning message");
        lTelefonoATAWarning.setText("");
        pTelefonoATASub.add(lTelefonoATAWarning);

        pTelefonoATA.add(pTelefonoATASub);

        pData.add(pTelefonoATA);

        pTelefonoATC.setLayout(new javax.swing.BoxLayout(pTelefonoATC, javax.swing.BoxLayout.LINE_AXIS));

        pTelefonoATCSub.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lTelefonoATC.setText("Teléfono ATC:");
        pTelefonoATCSub.add(lTelefonoATC);

        tfTelefonoATC.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATC.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATC.setPreferredSize(new java.awt.Dimension(130, 22));
        pTelefonoATCSub.add(tfTelefonoATC);

        lTelefonoATCWarning.setForeground(new java.awt.Color(204, 51, 0));
        lTelefonoATCWarning.setText("Warning message");
        lTelefonoATCWarning.setText("");
        pTelefonoATCSub.add(lTelefonoATCWarning);

        pTelefonoATC.add(pTelefonoATCSub);

        pData.add(pTelefonoATC);

        pCompaniaDetails.add(pData);

        pActions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pActions.setLayout(new java.awt.BorderLayout(20, 0));

        bShutdownCompania.setText("Dar de baja");
        bShutdownCompania.setEnabled(false);
        bShutdownCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bShutdownCompaniaActionPerformed(evt);
            }
        });
        pActions.add(bShutdownCompania, java.awt.BorderLayout.LINE_START);

        bSaveChangesCompania.setText("Guardar cambios");
        bSaveChangesCompania.setEnabled(false);
        bSaveChangesCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bSaveChangesCompaniaActionPerformed(evt);
            }
        });
        pActions.add(bSaveChangesCompania, java.awt.BorderLayout.CENTER);

        pCompaniaDetails.add(pActions);

        fillCompaniaDetails(null);

        pCompanias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compañías", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompanias.setLayout(new java.awt.BorderLayout(0, 10));

        pResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados de búsqueda"));
        pResults.setLayout(new javax.swing.BoxLayout(pResults, javax.swing.BoxLayout.LINE_AXIS));

        tCompaniaResults.setModel(new CompaniaTableModel(Util.getListCompania(), true));
        tCompaniaResults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tCompaniaResults.getSelectionModel().addListSelectionListener(new CompaniaListSelectionListener(this));
        jScrollPane1.setViewportView(tCompaniaResults);

        pResults.add(jScrollPane1);

        pCompanias.add(pResults, java.awt.BorderLayout.CENTER);

        pBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscador"));
        pBuscador.setLayout(new java.awt.BorderLayout(10, 0));

        cbCompaniaSearchType.setModel(new DefaultComboBoxModel<>(com.mycompany.gestorvuelos.gui.logic.CompaniaSearchTypeEnum.valuesToString()));
        cbCompaniaSearchType.setSelectedItem(CompaniaSearchTypeEnum.NOMBRE.toString());
        cbCompaniaSearchType.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbCompaniaSearchTypeActionPerformed(evt);
            }
        });
        pBuscador.add(cbCompaniaSearchType, java.awt.BorderLayout.LINE_START);

        tfCompaniaSearcher.getDocument().addDocumentListener(new com.mycompany.gestorvuelos.gui.listeners.CompaniaSearchListener(cbCompaniaSearchType, tCompaniaResults, true));
        pBuscador.add(tfCompaniaSearcher, java.awt.BorderLayout.CENTER);

        pCompanias.add(pBuscador, java.awt.BorderLayout.PAGE_START);

        bRegisterCompania.setText("Gestionar alta de nueva compañía");
        bRegisterCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bRegisterCompaniaActionPerformed(evt);
            }
        });
        pCompanias.add(bRegisterCompania, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pCompaniaDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCompaniaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bShutdownCompaniaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bShutdownCompaniaActionPerformed
    {//GEN-HEADEREND:event_bShutdownCompaniaActionPerformed
        shutdownCompania();
    }//GEN-LAST:event_bShutdownCompaniaActionPerformed

    private void bSaveChangesCompaniaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSaveChangesCompaniaActionPerformed
    {//GEN-HEADEREND:event_bSaveChangesCompaniaActionPerformed
        saveChangesToCompania();
    }//GEN-LAST:event_bSaveChangesCompaniaActionPerformed

    private void cbCompaniaSearchTypeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbCompaniaSearchTypeActionPerformed
    {//GEN-HEADEREND:event_cbCompaniaSearchTypeActionPerformed
        tfCompaniaSearcher.setText("");
    }//GEN-LAST:event_cbCompaniaSearchTypeActionPerformed

    private void bRegisterCompaniaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bRegisterCompaniaActionPerformed
    {//GEN-HEADEREND:event_bRegisterCompaniaActionPerformed
        // Limpiar selección de la tabla.
        tCompaniaResults.clearSelection();
        
        RegisterNewCompaniaDialog registerNewCompaniaDialog = 
                new RegisterNewCompaniaDialog(this, 
                        true, 
                        (CompaniaTableModel) tCompaniaResults.getModel());
        registerNewCompaniaDialog.setVisible(true);
    }//GEN-LAST:event_bRegisterCompaniaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompaniasManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompaniasManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompaniasManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompaniasManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CompaniasManagerFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bRegisterCompania;
    private javax.swing.JButton bSaveChangesCompania;
    private javax.swing.JButton bShutdownCompania;
    private javax.swing.JComboBox<String> cbCompaniaSearchType;
    private javax.swing.JComboBox<String> cbMunicipioSedeCentral;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lDireccionSedeCentral;
    private javax.swing.JLabel lDireccionSedeCentralWarning;
    private javax.swing.JLabel lMunicipioSedeCentral;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lPrefijo;
    private javax.swing.JLabel lTelefonoATA;
    private javax.swing.JLabel lTelefonoATAWarning;
    private javax.swing.JLabel lTelefonoATC;
    private javax.swing.JLabel lTelefonoATCWarning;
    private javax.swing.JPanel pActions;
    private javax.swing.JPanel pBuscador;
    private javax.swing.JPanel pCompaniaDetails;
    private javax.swing.JPanel pCompanias;
    private javax.swing.JPanel pCriticalData;
    private javax.swing.JPanel pData;
    private javax.swing.JPanel pDireccionSedeCentral;
    private javax.swing.JPanel pDireccionSedeCentralInput;
    private javax.swing.JPanel pDireccionSedeCentralWarning;
    private javax.swing.JPanel pMunicipioSedeCentral;
    private javax.swing.JPanel pResults;
    private javax.swing.JPanel pTelefonoATA;
    private javax.swing.JPanel pTelefonoATASub;
    private javax.swing.JPanel pTelefonoATC;
    private javax.swing.JPanel pTelefonoATCSub;
    private javax.swing.JTable tCompaniaResults;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCompaniaSearcher;
    private javax.swing.JTextField tfDireccionSedeCentral;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfPrefijo;
    private javax.swing.JTextField tfTelefonoATA;
    private javax.swing.JTextField tfTelefonoATC;
    // End of variables declaration//GEN-END:variables
}
