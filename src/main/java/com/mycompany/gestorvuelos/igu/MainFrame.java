package com.mycompany.gestorvuelos.igu;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.igu.logica.CompaniaListSelectionListener;
import com.mycompany.gestorvuelos.igu.logica.CompaniaSearchListener;
import com.mycompany.gestorvuelos.igu.logica.CompaniaSearchTypeEnum;
import com.mycompany.gestorvuelos.negocio.logica.ListManager;
import com.mycompany.gestorvuelos.negocio.logica.Util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.IOException;
import java.text.ParseException;
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

/**
 *
 * @author PVita
 */
public class MainFrame extends javax.swing.JFrame
{
    /**
     * Creates new form MainFrame
     */
    public MainFrame()
    {
        initUtils();
        initComponents();
        
        tCompaniaResults.getSelectionModel().addListSelectionListener(new CompaniaListSelectionListener(this));
    }
    
    private void initUtils()
    {
        try {
            // TODO - Obtener el codigo IATA del aeropuerto base almacenado por el usuario.
            Util.initUtils("ABC");
        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            this.dispose();
            System.exit(1);
        }
    }
    
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
        
        ftfPrefijo.setText(String.valueOf(compania.getPrefijo()));
        tfCodigo.setText(String.valueOf(compania.getCodigo()));
        tfNombre.setText(compania.getNombre());
        tfDireccionSedeCentral.setText(compania.getDireccionSedeCentral());
        
        if (!compania.getMunicipioSedeCentral().isEmpty()) {
            cbMunicipioSedeCentral.setSelectedItem(compania.getMunicipioSedeCentral());
        } else {
            cbMunicipioSedeCentral.setSelectedIndex(-1);
        }
        
        ftfTelefonoATC.setText(String.valueOf(compania.getTelefonoATC()));
        ftfTelefonoATA.setText(String.valueOf(compania.getTelefonoATA()));
    }
    
    /**
     * Establece el valor habilitado de los botones correpondientes al panel
     * CompaniaDetails.
     * @param enabled Des/Habilitados.
     */
    private void setEnabledCompaniaDetailsButtons(boolean enabled)
    {
        bSaveChangesCompania.setEnabled(enabled);
        bShutdownCompania.setEnabled(enabled);
    }
    
    /**
     * Guarda los cámbios realizados sobre la compañía seleccionada.
     */
    // TODO - Implementar validación del formulario.
    private void saveChangesToCompania()
    {
        // Obtener compañía seleccionada.
        var companiaTableModel = (CompaniaTableModel) tCompaniaResults.getModel();
        Compania workingCompania;
        try {
            workingCompania = companiaTableModel.getCompaniaAt(tCompaniaResults.getSelectedRow());
        } catch (IndexOutOfBoundsException ex) {
            // En caso de no haber una compañía seleccionada, retornar.
            return;
        }

        //workingCompania.setPrefijo(Integer.parseInt(ftfPrefijo.getText()));
        //workingCompania.setCodigo(tfCodigo.getText());
        //workingCompania.setNombre(tfNombre.getText());
        workingCompania.setDireccionSedeCentral(tfDireccionSedeCentral.getText());

        Object municipio = cbMunicipioSedeCentral.getSelectedItem();
        workingCompania.setMunicipioSedeCentral(municipio == null ? "" : municipio.toString());

        // Validar los campos antes de continuar.
        try {
            ftfTelefonoATC.commitEdit();
            ftfTelefonoATA.commitEdit();
        } catch (ParseException parseException) {
            // Esta excepción es inalcanzable.
        }
        
        workingCompania.setTelefonoATC(Integer.parseInt(ftfTelefonoATC.getText()));
        workingCompania.setTelefonoATA(Integer.parseInt(ftfTelefonoATA.getText()));
        
        JOptionPane.showMessageDialog(this,
                "Compañía " + workingCompania.getNombre() + " modificada con éxito.",
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
            // En caso de no haber una compañía seleccionada, retornar.
            return;
        }
        
        String nombreCompania = compania.getNombre();
        var model = (CompaniaTableModel) tCompaniaResults.getModel();
        
        try {
            model.removeCompania(compania);
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this,
                    "No ha sido posible cursar la baja de " + nombreCompania + ".",
                    this.getName(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vaciar los detalles de la compañía.
        fillCompaniaDetails(null);

        JOptionPane.showMessageDialog(this,
                "Compañía " + nombreCompania + " dada de baja con éxito.",
                this.getName(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ---> GETTERS&SETTERS <---
    /**
     * Obtiene el JTable que lista el registro de compañías.
     * @return JTable de compañías
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
        pPrefijo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ftfPrefijo = new javax.swing.JFormattedTextField();
        pCodigo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        pNombre = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        pDireccionSedeCentral = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfDireccionSedeCentral = new javax.swing.JTextField();
        pMunicipioSedeCentral = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbMunicipioSedeCentral = new javax.swing.JComboBox<>();
        pTelefonoATC = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ftfTelefonoATC = new javax.swing.JFormattedTextField();
        pTelefonoATA = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        ftfTelefonoATA = new javax.swing.JFormattedTextField();
        pActions = new javax.swing.JPanel();
        bShutdownCompania = new javax.swing.JButton();
        bSaveChangesCompania = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pCompanias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compañías", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompanias.setLayout(new java.awt.BorderLayout(0, 10));

        pResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados de búsqueda"));
        pResults.setLayout(new javax.swing.BoxLayout(pResults, javax.swing.BoxLayout.LINE_AXIS));

        tCompaniaResults.setModel(new CompaniaTableModel(Util.getListCompania(), true));
        jScrollPane1.setViewportView(tCompaniaResults);

        pResults.add(jScrollPane1);

        pCompanias.add(pResults, java.awt.BorderLayout.CENTER);

        pBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscador"));
        pBuscador.setLayout(new java.awt.BorderLayout(10, 0));

        cbCompaniaSearchType.setModel(new DefaultComboBoxModel<>(com.mycompany.gestorvuelos.igu.logica.CompaniaSearchTypeEnum.valuesToString()));
        pBuscador.add(cbCompaniaSearchType, java.awt.BorderLayout.LINE_START);

        tfCompaniaSearcher.getDocument().addDocumentListener(new com.mycompany.gestorvuelos.igu.logica.CompaniaSearchListener(cbCompaniaSearchType, tCompaniaResults, true));
        pBuscador.add(tfCompaniaSearcher, java.awt.BorderLayout.CENTER);

        pCompanias.add(pBuscador, java.awt.BorderLayout.PAGE_START);

        pCompaniaDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la compañía", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompaniaDetails.setLayout(new java.awt.BorderLayout(0, 20));

        pData.setLayout(new java.awt.GridLayout(7, 0, 0, 5));

        pPrefijo.setBackground(new java.awt.Color(232, 232, 232));
        pPrefijo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        pPrefijo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel1.setText("Prefijo:");
        pPrefijo.add(jLabel1);

        ftfPrefijo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftfPrefijo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ftfPrefijo.setEnabled(false);
        pPrefijo.add(ftfPrefijo);

        pData.add(pPrefijo);

        pCodigo.setBackground(new java.awt.Color(232, 232, 232));
        pCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        pCodigo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel2.setText("Código:");
        pCodigo.add(jLabel2);

        tfCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfCodigo.setEnabled(false);
        tfCodigo.setPreferredSize(new java.awt.Dimension(38, 22));
        pCodigo.add(tfCodigo);

        pData.add(pCodigo);

        pNombre.setBackground(new java.awt.Color(232, 232, 232));
        pNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        pNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel3.setText("Nombre:");
        pNombre.add(jLabel3);

        tfNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfNombre.setEnabled(false);
        tfNombre.setPreferredSize(new java.awt.Dimension(242, 26));
        pNombre.add(tfNombre);

        pData.add(pNombre);

        pDireccionSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel4.setText("Dirección sede central:");
        pDireccionSedeCentral.add(jLabel4);

        tfDireccionSedeCentral.setPreferredSize(new java.awt.Dimension(367, 26));
        pDireccionSedeCentral.add(tfDireccionSedeCentral);

        pData.add(pDireccionSedeCentral);

        pMunicipioSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel5.setText("Municipio sede central:");
        pMunicipioSedeCentral.add(jLabel5);

        cbMunicipioSedeCentral.setModel(new DefaultComboBoxModel<String>(Util.getMapMunicipios().keySet().toArray(new String[0])));
        cbMunicipioSedeCentral.setPreferredSize(new java.awt.Dimension(144, 22));
        pMunicipioSedeCentral.add(cbMunicipioSedeCentral);

        pData.add(pMunicipioSedeCentral);

        pTelefonoATC.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel6.setText("Teléfono ATC:");
        pTelefonoATC.add(jLabel6);

        ftfTelefonoATC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftfTelefonoATC.setPreferredSize(new java.awt.Dimension(85, 22));
        pTelefonoATC.add(ftfTelefonoATC);

        pData.add(pTelefonoATC);

        pTelefonoATA.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5));

        jLabel7.setText("Teléfono ATA:");
        pTelefonoATA.add(jLabel7);

        ftfTelefonoATA.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftfTelefonoATA.setPreferredSize(new java.awt.Dimension(85, 22));
        pTelefonoATA.add(ftfTelefonoATA);

        pData.add(pTelefonoATA);

        pCompaniaDetails.add(pData, java.awt.BorderLayout.CENTER);

        pActions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pActions.setLayout(new java.awt.BorderLayout());

        bShutdownCompania.setText("Dar de baja");
        bShutdownCompania.setBorder(bSaveChangesCompania.getBorder());
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
        pActions.add(bSaveChangesCompania, java.awt.BorderLayout.LINE_END);

        pCompaniaDetails.add(pActions, java.awt.BorderLayout.PAGE_END);

        fillCompaniaDetails(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCompanias, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pCompaniaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
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
    private javax.swing.JFormattedTextField ftfPrefijo;
    private javax.swing.JFormattedTextField ftfTelefonoATA;
    private javax.swing.JFormattedTextField ftfTelefonoATC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pActions;
    private javax.swing.JPanel pBuscador;
    private javax.swing.JPanel pCodigo;
    private javax.swing.JPanel pCompaniaDetails;
    private javax.swing.JPanel pCompanias;
    private javax.swing.JPanel pData;
    private javax.swing.JPanel pDireccionSedeCentral;
    private javax.swing.JPanel pMunicipioSedeCentral;
    private javax.swing.JPanel pNombre;
    private javax.swing.JPanel pPrefijo;
    private javax.swing.JPanel pResults;
    private javax.swing.JPanel pTelefonoATA;
    private javax.swing.JPanel pTelefonoATC;
    private javax.swing.JTable tCompaniaResults;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCompaniaSearcher;
    private javax.swing.JTextField tfDireccionSedeCentral;
    private javax.swing.JTextField tfNombre;
    // End of variables declaration//GEN-END:variables
}
