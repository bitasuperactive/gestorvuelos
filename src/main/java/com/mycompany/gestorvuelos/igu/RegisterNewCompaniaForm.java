package com.mycompany.gestorvuelos.igu;

import com.mycompany.gestorvuelos.negocio.logica.Util;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author PVita
 */
public class RegisterNewCompaniaForm extends javax.swing.JFrame
{

    /**
     * Creates new form RegisterNewCompaniaForm
     */
    public RegisterNewCompaniaForm()
    {
        if (!Util.isInitialized()) {
            try {
                Util.initUtils("ABC");
            } catch (IOException | IllegalArgumentException | NoSuchElementException e) {
                e.printStackTrace();
                this.dispose();
                System.exit(1);
            }
        }
        
        initComponents();
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

        pCriticalData = new javax.swing.JPanel();
        pPrefijo = new javax.swing.JPanel();
        pPrefijoInput = new javax.swing.JPanel();
        lPrefijo = new javax.swing.JLabel();
        tfPrefijo = new javax.swing.JTextField();
        lPrefijoWarning = new javax.swing.JLabel();
        pCodigo = new javax.swing.JPanel();
        pCodigoInput = new javax.swing.JPanel();
        lCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        lCodigoWarning = new javax.swing.JLabel();
        pNombre = new javax.swing.JPanel();
        pNombreInput = new javax.swing.JPanel();
        lNombre = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        pNombreWarning = new javax.swing.JPanel();
        lNombreWarning = new javax.swing.JLabel();
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
        pTelefonoATAInput = new javax.swing.JPanel();
        lTelefonoATA = new javax.swing.JLabel();
        tfTelefonoATA = new javax.swing.JTextField();
        lTelefonoATAWarning = new javax.swing.JLabel();
        pTelefonoATC = new javax.swing.JPanel();
        pTelefonoATCInput = new javax.swing.JPanel();
        lTelefonoATC = new javax.swing.JLabel();
        tfTelefonoATC = new javax.swing.JTextField();
        lTelefonoATCWarning = new javax.swing.JLabel();
        pActions = new javax.swing.JPanel();
        bCancel = new javax.swing.JButton();
        bRegisterCompania = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pCriticalData.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos obligatorios"));
        pCriticalData.setLayout(new javax.swing.BoxLayout(pCriticalData, javax.swing.BoxLayout.Y_AXIS));

        pPrefijo.setLayout(new java.awt.BorderLayout(5, 0));

        pPrefijoInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lPrefijo.setText("Prefijo:");
        pPrefijoInput.add(lPrefijo);

        tfPrefijo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfPrefijo.setText("999");
        tfPrefijo.setMinimumSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setPreferredSize(new java.awt.Dimension(34, 22));
        pPrefijoInput.add(tfPrefijo);

        pPrefijo.add(pPrefijoInput, java.awt.BorderLayout.LINE_START);

        lPrefijoWarning.setForeground(new java.awt.Color(255, 102, 0));
        lPrefijoWarning.setText("Warning message");
        lPrefijoWarning.setText("");
        pPrefijo.add(lPrefijoWarning, java.awt.BorderLayout.CENTER);

        pCriticalData.add(pPrefijo);

        pCodigo.setLayout(new java.awt.BorderLayout(5, 0));

        pCodigoInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lCodigo.setText("Código:");
        pCodigoInput.add(lCodigo);

        tfCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfCodigo.setText("WW");
        tfCodigo.setMinimumSize(new java.awt.Dimension(38, 22));
        tfCodigo.setPreferredSize(new java.awt.Dimension(38, 22));
        pCodigoInput.add(tfCodigo);

        pCodigo.add(pCodigoInput, java.awt.BorderLayout.LINE_START);

        lCodigoWarning.setForeground(new java.awt.Color(255, 102, 0));
        lCodigoWarning.setText("Warning message");
        lCodigoWarning.setText("");
        pCodigo.add(lCodigoWarning, java.awt.BorderLayout.CENTER);

        pCriticalData.add(pCodigo);

        pNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        pNombre.setLayout(new javax.swing.BoxLayout(pNombre, javax.swing.BoxLayout.Y_AXIS));

        pNombreInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lNombre.setText("Nombre:");
        pNombreInput.add(lNombre);

        tfNombre.setText(" SAETA Soc Ecuatoriana de Ttes Aereos");
        tfNombre.setMinimumSize(new java.awt.Dimension(272, 26));
        tfNombre.setPreferredSize(new java.awt.Dimension(272, 26));
        pNombreInput.add(tfNombre);

        pNombre.add(pNombreInput);

        pNombreWarning.setLayout(new java.awt.CardLayout(4, 0));

        lNombreWarning.setForeground(new java.awt.Color(255, 102, 0));
        lNombreWarning.setText("Warning message");
        lNombreWarning.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lNombreWarning.setText("");
        pNombreWarning.add(lNombreWarning, "card2");

        pNombre.add(pNombreWarning);

        pCriticalData.add(pNombre);

        getContentPane().add(pCriticalData, java.awt.BorderLayout.PAGE_START);

        pData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        pData.setMinimumSize(new java.awt.Dimension(540, 140));
        pData.setPreferredSize(new java.awt.Dimension(540, 168));
        pData.setLayout(new javax.swing.BoxLayout(pData, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentral.setLayout(new javax.swing.BoxLayout(pDireccionSedeCentral, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentralInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        pDireccionSedeCentralInput.setLayout(new java.awt.BorderLayout(5, 0));

        lDireccionSedeCentral.setText("Dirección sede central:");
        pDireccionSedeCentralInput.add(lDireccionSedeCentral, java.awt.BorderLayout.LINE_START);

        tfDireccionSedeCentral.setText("CALLE MARIA DE MOLINA, 54 , 2ª PLANTA. 28006, MADRID, MADRID");
        tfDireccionSedeCentral.setMinimumSize(new java.awt.Dimension(392, 26));
        tfDireccionSedeCentral.setPreferredSize(new java.awt.Dimension(392, 26));
        pDireccionSedeCentralInput.add(tfDireccionSedeCentral, java.awt.BorderLayout.CENTER);

        pDireccionSedeCentral.add(pDireccionSedeCentralInput);

        pDireccionSedeCentralWarning.setLayout(new java.awt.CardLayout(4, 0));

        lDireccionSedeCentralWarning.setForeground(new java.awt.Color(255, 102, 0));
        lDireccionSedeCentralWarning.setText("Warning message");
        lDireccionSedeCentralWarning.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lDireccionSedeCentralWarning.setText("");
        pDireccionSedeCentralWarning.add(lDireccionSedeCentralWarning, "card2");

        pDireccionSedeCentral.add(pDireccionSedeCentralWarning);

        pData.add(pDireccionSedeCentral);

        pMunicipioSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lMunicipioSedeCentral.setText("Municipio sede central:");
        pMunicipioSedeCentral.add(lMunicipioSedeCentral);

        cbMunicipioSedeCentral.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "San Vicente del Raspeig/Sant Vicent del Raspeig" }));
        String[] municipioSedeCentralItems = Util.getMapMunicipios().keySet().toArray(new String[0]);
        cbMunicipioSedeCentral.setModel(new DefaultComboBoxModel<>(municipioSedeCentralItems));
        pMunicipioSedeCentral.add(cbMunicipioSedeCentral);

        pData.add(pMunicipioSedeCentral);

        pTelefonoATA.setLayout(new java.awt.BorderLayout(5, 0));

        lTelefonoATA.setText("Teléfono ATA:");
        pTelefonoATAInput.add(lTelefonoATA);

        tfTelefonoATA.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATA.setText("+001 404555017077");
        tfTelefonoATA.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATA.setPreferredSize(new java.awt.Dimension(130, 22));
        pTelefonoATAInput.add(tfTelefonoATA);

        pTelefonoATA.add(pTelefonoATAInput, java.awt.BorderLayout.LINE_START);

        lTelefonoATAWarning.setForeground(new java.awt.Color(255, 102, 0));
        lTelefonoATAWarning.setText("Warning message");
        lTelefonoATAWarning.setText("");
        pTelefonoATA.add(lTelefonoATAWarning, java.awt.BorderLayout.CENTER);

        pData.add(pTelefonoATA);

        pTelefonoATC.setLayout(new java.awt.BorderLayout(5, 0));

        pTelefonoATCInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lTelefonoATC.setText("Teléfono ATC:");
        pTelefonoATCInput.add(lTelefonoATC);

        tfTelefonoATC.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfTelefonoATC.setText("+001 404555017066");
        tfTelefonoATC.setMinimumSize(new java.awt.Dimension(130, 22));
        tfTelefonoATC.setPreferredSize(new java.awt.Dimension(130, 22));
        pTelefonoATCInput.add(tfTelefonoATC);

        pTelefonoATC.add(pTelefonoATCInput, java.awt.BorderLayout.LINE_START);

        lTelefonoATCWarning.setForeground(new java.awt.Color(255, 102, 0));
        lTelefonoATCWarning.setText("Warning message");
        lTelefonoATCWarning.setText("");
        pTelefonoATC.add(lTelefonoATCWarning, java.awt.BorderLayout.CENTER);

        pData.add(pTelefonoATC);

        getContentPane().add(pData, java.awt.BorderLayout.CENTER);

        pActions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        bCancel.setText("Cancelar");

        bRegisterCompania.setText("Tramitar alta");

        javax.swing.GroupLayout pActionsLayout = new javax.swing.GroupLayout(pActions);
        pActions.setLayout(pActionsLayout);
        pActionsLayout.setHorizontalGroup(
            pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(bRegisterCompania, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pActionsLayout.setVerticalGroup(
            pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bRegisterCompania, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pActions, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 556, 369);
    }// </editor-fold>//GEN-END:initComponents

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
            new RegisterNewCompaniaForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bRegisterCompania;
    private javax.swing.JComboBox<String> cbMunicipioSedeCentral;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lCodigoWarning;
    private javax.swing.JLabel lDireccionSedeCentral;
    private javax.swing.JLabel lDireccionSedeCentralWarning;
    private javax.swing.JLabel lMunicipioSedeCentral;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lNombreWarning;
    private javax.swing.JLabel lPrefijo;
    private javax.swing.JLabel lPrefijoWarning;
    private javax.swing.JLabel lTelefonoATA;
    private javax.swing.JLabel lTelefonoATAWarning;
    private javax.swing.JLabel lTelefonoATC;
    private javax.swing.JLabel lTelefonoATCWarning;
    private javax.swing.JPanel pActions;
    private javax.swing.JPanel pCodigo;
    private javax.swing.JPanel pCodigoInput;
    private javax.swing.JPanel pCriticalData;
    private javax.swing.JPanel pData;
    private javax.swing.JPanel pDireccionSedeCentral;
    private javax.swing.JPanel pDireccionSedeCentralInput;
    private javax.swing.JPanel pDireccionSedeCentralWarning;
    private javax.swing.JPanel pMunicipioSedeCentral;
    private javax.swing.JPanel pNombre;
    private javax.swing.JPanel pNombreInput;
    private javax.swing.JPanel pNombreWarning;
    private javax.swing.JPanel pPrefijo;
    private javax.swing.JPanel pPrefijoInput;
    private javax.swing.JPanel pTelefonoATA;
    private javax.swing.JPanel pTelefonoATAInput;
    private javax.swing.JPanel pTelefonoATC;
    private javax.swing.JPanel pTelefonoATCInput;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfDireccionSedeCentral;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfPrefijo;
    private javax.swing.JTextField tfTelefonoATA;
    private javax.swing.JTextField tfTelefonoATC;
    // End of variables declaration//GEN-END:variables
}