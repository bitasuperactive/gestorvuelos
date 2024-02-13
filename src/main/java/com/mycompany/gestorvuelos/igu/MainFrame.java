package com.mycompany.gestorvuelos.igu;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.igu.logica.CompaniaListSelectionListener;
import com.mycompany.gestorvuelos.igu.logica.CompaniaSearchListener;
import com.mycompany.gestorvuelos.igu.logica.CompaniaSearchTypeEnum;
import com.mycompany.gestorvuelos.igu.logica.MaxCharsDocumentFilter;
import com.mycompany.gestorvuelos.negocio.logica.ListManager;
import com.mycompany.gestorvuelos.negocio.logica.Util;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.text.AbstractDocument;

/**
 * Formulario principal de la aplicación.
 */
public class MainFrame extends javax.swing.JFrame
{
    private Validator validator;
    
    /**
     * Crea un nuevo formulario MainFrame inicializando los Utils correspondientes.
     * @see Util
     */
    public MainFrame()
    {
        initUtils();
        initComponents();
    }
    
    private void initUtils()
    {
        try {
            // TODO - Obtener el codigo IATA del aeropuerto base almacenado por el usuario.
            Util.initUtils("ABC");
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            this.dispose();
            System.exit(1);
        }
    }
    
    // TODO - Evitar modificador público.
    /**
     * Guarda la compañía especificada para su modificación o baja, 
     * rellena los campos correspondientes a sus detalles
     * y, solo si esta no es nula, habilita los botones correspondientes.
     * @param compania Compañía a detallar
     */
    public void fillCompaniaDetails(Compania compania)
    {
        setEnabledCompaniaDetailsButtons(compania != null);
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
     * Establece el valor Enabled de los botones correpondientes al panel
     * pCompaniaDetails.
     * @param enabled Des/Habilitados.
     */
    private void setEnabledCompaniaDetailsButtons(boolean enabled)
    {
        bSaveChangesCompania.setEnabled(enabled);
        bShutdownCompania.setEnabled(enabled);
    }
    
    /**
     * Crea una nueva compañía a partir de los campos rellenados en el panel
     * pCompaniaDetails.
     * @return Compañía sin validar.
     */
    private Compania getCompaniaFromFields()
    {
        Short prefijo = null;
        try {
            prefijo = Short.valueOf(tfPrefijo.getText());
        } catch (NumberFormatException ex) {
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
     * Guarda los cámbios realizados sobre la compañía seleccionada.
     */
    // TODO - Implementar validación del formulario.
    private void saveChangesToCompania() throws IllegalArgumentException
    {
        // Obtener compañía seleccionada.
        var companiaTableModel = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania selectedCompania;
        try {
            selectedCompania = companiaTableModel.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            // En caso de no haber una compañía seleccionada, 
            // los botones deberían estar deshabilitados.
            throw new IllegalArgumentException("No se ha seleccionado ninguna compañía del listado.");
        }
        
        // Generamos una nueva compañía a partir de los datos modificados.
        Compania newCompania = getCompaniaFromFields();
        
        // Comprobamos si han habido cambios.
        if (selectedCompania.equals(newCompania))
        {
            JOptionPane.showMessageDialog(this, "No han habido cambios.",
                    this.getName(), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Validamos la nueva compañía.
        Set<ConstraintViolation<Compania>> violations = validator.validate(newCompania);
        if (!violations.isEmpty())
        {
            // Mostramos las violaciones de validación al usuario y
            // cancelamos el proceso.
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Compania> violation : violations)
            {
                builder.append(violation.getMessage()).append("\n");
            }
            JOptionPane.showMessageDialog(this, new String(builder),
                    this.getName(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Guardamos los datos validados en la compañía seleccionada.
        selectedCompania.override(newCompania);
        
        String message = String.format("Compañía [ %s ] modificada con éxito.", 
                selectedCompania.getNombre());
        JOptionPane.showMessageDialog(this, message,
                this.getName(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Elimina al compañía seleccionada del listado de compañías.
     * @see CompaniaTableModel#removeCompania(com.mycompany.gestorvuelos.dto.Compania)
     */
    private void shutdownCompania()
    {
        // Obtener compañía seleccionada.
        var companiaTableModel = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania compania;
        try {
            compania = companiaTableModel.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            // En caso de no haber una compañía seleccionada, 
            // los botones deberían estar deshabilitados.
            throw new IllegalArgumentException("No se ha seleccionado ninguna compañía del listado.");
        }
        
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
                this.getName(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ---> GETTERS&SETTERS <---
    /**
     * Obtiene el JTable que lista el registro de compañías.
     * @return JTable de compañías.
     */
    public JTable getTableCompaniaResults()
    {
        return tCompaniaResults;
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

        pCompanias = new javax.swing.JPanel();
        pResults = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tCompaniaResults = new javax.swing.JTable();
        pBuscador = new javax.swing.JPanel();
        cbCompaniaSearchType = new javax.swing.JComboBox<>();
        tfCompaniaSearcher = new javax.swing.JTextField();
        pCompaniaDetails = new javax.swing.JPanel();
        pData = new javax.swing.JPanel();
        pCriticalData = new javax.swing.JPanel();
        lPrefijo = new javax.swing.JLabel();
        tfPrefijo = new javax.swing.JTextField();
        lCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        lNombre = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        pDireccionSedeCentral = new javax.swing.JPanel();
        lDireccionSedeCentral = new javax.swing.JLabel();
        tfDireccionSedeCentral = new javax.swing.JTextField();
        pMunicipioSedeCentral = new javax.swing.JPanel();
        lMunicipioSedeCentral = new javax.swing.JLabel();
        cbMunicipioSedeCentral = new javax.swing.JComboBox<>();
        pTelefonoATC = new javax.swing.JPanel();
        lTelefonoATA = new javax.swing.JLabel();
        tfTelefonoATA = new javax.swing.JTextField();
        lTelefonoATC = new javax.swing.JLabel();
        tfTelefonoATC = new javax.swing.JTextField();
        pActions = new javax.swing.JPanel();
        bShutdownCompania = new javax.swing.JButton();
        bSaveChangesCompania = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pCompanias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compañías", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompanias.setLayout(new java.awt.BorderLayout(0, 10));

        pResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados de búsqueda"));
        pResults.setLayout(new javax.swing.BoxLayout(pResults, javax.swing.BoxLayout.LINE_AXIS));

        tCompaniaResults.setModel(new CompaniaTableModel(Util.getListCompania(), true));
        tCompaniaResults.getSelectionModel().addListSelectionListener(new CompaniaListSelectionListener(this));
        jScrollPane1.setViewportView(tCompaniaResults);

        pResults.add(jScrollPane1);

        pCompanias.add(pResults, java.awt.BorderLayout.CENTER);

        pBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscador"));
        pBuscador.setLayout(new java.awt.BorderLayout(10, 0));

        cbCompaniaSearchType.setModel(new DefaultComboBoxModel<>(com.mycompany.gestorvuelos.igu.logica.CompaniaSearchTypeEnum.valuesToString()));
        cbCompaniaSearchType.setSelectedItem(CompaniaSearchTypeEnum.NOMBRE.toString());
        cbCompaniaSearchType.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbCompaniaSearchTypeActionPerformed(evt);
            }
        });
        pBuscador.add(cbCompaniaSearchType, java.awt.BorderLayout.LINE_START);

        tfCompaniaSearcher.getDocument().addDocumentListener(new com.mycompany.gestorvuelos.igu.logica.CompaniaSearchListener(cbCompaniaSearchType, tCompaniaResults, true));
        pBuscador.add(tfCompaniaSearcher, java.awt.BorderLayout.CENTER);

        pCompanias.add(pBuscador, java.awt.BorderLayout.PAGE_START);

        pCompaniaDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la compañía", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompaniaDetails.setLayout(new java.awt.BorderLayout());

        pData.setMinimumSize(new java.awt.Dimension(540, 168));
        pData.setPreferredSize(new java.awt.Dimension(540, 168));
        pData.setLayout(new java.awt.GridLayout(4, 0, 0, 5));

        pCriticalData.setBackground(new java.awt.Color(232, 232, 232));
        pCriticalData.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        pCriticalData.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        lPrefijo.setText("Prefijo:");
        pCriticalData.add(lPrefijo);

        tfPrefijo.setBackground(new java.awt.Color(232, 232, 232));
        tfPrefijo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfPrefijo.setText("999");
        tfPrefijo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfPrefijo.setMinimumSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setPreferredSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setEditable(false);
        ((AbstractDocument) tfPrefijo.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "prefijo"));
        pCriticalData.add(tfPrefijo);

        lCodigo.setText("Código:");
        pCriticalData.add(lCodigo);

        tfCodigo.setBackground(new java.awt.Color(232, 232, 232));
        tfCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfCodigo.setText("WW");
        tfCodigo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfCodigo.setMinimumSize(new java.awt.Dimension(38, 22));
        tfCodigo.setPreferredSize(new java.awt.Dimension(38, 22));
        tfCodigo.setEditable(false);
        ((AbstractDocument) tfCodigo.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "codigo"));
        pCriticalData.add(tfCodigo);

        lNombre.setText("Nombre:");
        pCriticalData.add(lNombre);

        tfNombre.setBackground(new java.awt.Color(232, 232, 232));
        tfNombre.setText(" SAETA Soc Ecuatoriana de Ttes Aereos");
        tfNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfNombre.setMinimumSize(new java.awt.Dimension(272, 26));
        tfNombre.setPreferredSize(new java.awt.Dimension(272, 26));
        tfNombre.setEditable(false);
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "nombre"));
        pCriticalData.add(tfNombre);

        pData.add(pCriticalData);

        pDireccionSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        lDireccionSedeCentral.setText("Dirección sede central:");
        pDireccionSedeCentral.add(lDireccionSedeCentral);

        tfDireccionSedeCentral.setText("CALLE MARIA DE MOLINA, 54 , 2ª PLANTA. 28006, MADRID, MADRID");
        tfDireccionSedeCentral.setMinimumSize(new java.awt.Dimension(392, 26));
        tfDireccionSedeCentral.setPreferredSize(new java.awt.Dimension(392, 26));
        ((AbstractDocument) tfDireccionSedeCentral.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "direccionSedeCentral"));
        pDireccionSedeCentral.add(tfDireccionSedeCentral);

        pData.add(pDireccionSedeCentral);

        pMunicipioSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        lMunicipioSedeCentral.setText("Municipio sede central:");
        pMunicipioSedeCentral.add(lMunicipioSedeCentral);

        cbMunicipioSedeCentral.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "San Vicente del Raspeig/Sant Vicent del Raspeig" }));
        String[] municipioSedeCentralItems = Util.getMapMunicipios().keySet().toArray(new String[0]);
        cbMunicipioSedeCentral.setModel(new DefaultComboBoxModel<>(municipioSedeCentralItems));
        pMunicipioSedeCentral.add(cbMunicipioSedeCentral);

        pData.add(pMunicipioSedeCentral);

        pTelefonoATC.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        lTelefonoATA.setText("Teléfono ATA:");
        pTelefonoATC.add(lTelefonoATA);

        tfTelefonoATA.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATA.setText("+001 404555017077");
        tfTelefonoATA.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATA.setPreferredSize(new java.awt.Dimension(130, 22));
        ((AbstractDocument) tfTelefonoATA.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "telefonoATA"));
        pTelefonoATC.add(tfTelefonoATA);

        lTelefonoATC.setText("Teléfono ATC:");
        pTelefonoATC.add(lTelefonoATC);

        tfTelefonoATC.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATC.setText("+001 404555017066");
        tfTelefonoATC.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATC.setPreferredSize(new java.awt.Dimension(130, 22));
        ((AbstractDocument) tfTelefonoATC.getDocument()).setDocumentFilter(
            new MaxCharsDocumentFilter(this, Compania.class, "telefonoATC"));
        pTelefonoATC.add(tfTelefonoATC);

        pData.add(pTelefonoATC);

        pCompaniaDetails.add(pData, java.awt.BorderLayout.CENTER);

        pActions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        bShutdownCompania.setText("Dar de baja");
        bShutdownCompania.setEnabled(false);
        bShutdownCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bShutdownCompaniaActionPerformed(evt);
            }
        });

        bSaveChangesCompania.setText("Guardar cambios");
        bSaveChangesCompania.setEnabled(false);
        bSaveChangesCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bSaveChangesCompaniaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pActionsLayout = new javax.swing.GroupLayout(pActions);
        pActions.setLayout(pActionsLayout);
        pActionsLayout.setHorizontalGroup(
            pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bShutdownCompania)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bSaveChangesCompania, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pActionsLayout.setVerticalGroup(
            pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bShutdownCompania, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bSaveChangesCompania, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pCompaniaDetails.add(pActions, java.awt.BorderLayout.PAGE_END);

        fillCompaniaDetails(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pCompaniaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pCompaniaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSaveChangesCompania;
    private javax.swing.JButton bShutdownCompania;
    private javax.swing.JComboBox<String> cbCompaniaSearchType;
    private javax.swing.JComboBox<String> cbMunicipioSedeCentral;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lDireccionSedeCentral;
    private javax.swing.JLabel lMunicipioSedeCentral;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lPrefijo;
    private javax.swing.JLabel lTelefonoATA;
    private javax.swing.JLabel lTelefonoATC;
    private javax.swing.JPanel pActions;
    private javax.swing.JPanel pBuscador;
    private javax.swing.JPanel pCompaniaDetails;
    private javax.swing.JPanel pCompanias;
    private javax.swing.JPanel pCriticalData;
    private javax.swing.JPanel pData;
    private javax.swing.JPanel pDireccionSedeCentral;
    private javax.swing.JPanel pMunicipioSedeCentral;
    private javax.swing.JPanel pResults;
    private javax.swing.JPanel pTelefonoATC;
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
