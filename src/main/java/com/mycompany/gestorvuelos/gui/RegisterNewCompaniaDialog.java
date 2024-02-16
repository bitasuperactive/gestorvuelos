package com.mycompany.gestorvuelos.gui;

import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.gui.listeners.CompaniaValidatorDocumentListener;
import com.mycompany.gestorvuelos.gui.logic.MaxCharsDocumentFilter;
import com.mycompany.gestorvuelos.gui.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.business.logic.Util;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import com.mycompany.gestorvuelos.gui.interfaces.ValidationFormulary;
import com.mycompany.gestorvuelos.business.logic.ComponentManager;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author PVita
 */
public class RegisterNewCompaniaDialog extends javax.swing.JDialog implements ValidationFormulary
{
    private final CompaniaTableModel model;

    /**
     * Crea un nuevo diálogo RegisterNewCompaniaDialog.
     * @param parent Padre del diálogo.
     * @param modal Si es modal.
     * @param model Modelo en que insertar la compañía a crear.
     * @throws IllegalArgumentException Si las utilidades requeridas 
     * no han sido inicializadas.
     */
    public RegisterNewCompaniaDialog(java.awt.Frame parent, boolean modal, CompaniaTableModel model)  throws NullPointerException
    {
        super(parent, modal);
        this.model = model;
        checkUtilsInitialized();
        initComponents();
        setupDocumentListeners();
        triggerDocumentListeners();
    }

    @Override
    public JButton getSubmitButton()
    {
        return bRegisterCompania;
    }
    
    /**
     * Lanza una excepción si las utilidades requeridas no se encuentran disponibles.
     * @throws NullPointerException Si las utilidades requeridas no se 
     * encuentran disponibles.
     * @see Util#initUtils(java.lang.String)
     */
    private void checkUtilsInitialized() throws NullPointerException
    {
        if (!Util.isInitialized()) {
            throw new NullPointerException("Excepción de inicialización:"
                    + " las utilidades requeridas no se encuentran disponibles.");
        }
    }
    
    /**
     * Establece los listeners MaxCharsDocumentFilter y 
     * CompaniaValidatorDocumentListener para los campos del formulario.
     * @see MaxCharsDocumentFilter
     * @see CompaniaValidatorDocumentListener
     */
    private void setupDocumentListeners()
    {
        // Establecer límites de caracteres.
        ((AbstractDocument) tfPrefijo.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "prefijo"));
        ((AbstractDocument) tfCodigo.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "codigo"));
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "nombre"));
        ((AbstractDocument) tfDireccionSedeCentral.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "direccionSedeCentral"));
        ((AbstractDocument) tfTelefonoATA.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "telefonoATA"));
        ((AbstractDocument) tfTelefonoATC.getDocument()).setDocumentFilter(
                new MaxCharsDocumentFilter(this, Compania.class, "telefonoATC"));

        // Establecer validadores.
        tfPrefijo.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "prefijo", lPrefijoWarning));
        tfCodigo.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "codigo", lCodigoWarning));
        tfNombre.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "nombre", lNombreWarning));
        tfDireccionSedeCentral.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "direccionSedeCentral", lDireccionSedeCentralWarning));
        tfTelefonoATA.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "telefonoATA", lTelefonoATAWarning));
        tfTelefonoATC.getDocument().addDocumentListener(new CompaniaValidatorDocumentListener(this, "telefonoATC", lTelefonoATCWarning));
    }

    /**
     * Desencadena el evento de modificación de texto de todos los JTextFields
     * del diálogo.
     */
    private void triggerDocumentListeners()
    {
        // Obtenemos todos los componentes del diálogo.
        List<Component> components = ComponentManager.getAllComponents((Container) getComponents()[0]);

        // Extraemos solo los JTextFields.
        JTextField[] textFields = components.stream()
                .filter(component -> component instanceof JTextField)
                .toArray(JTextField[]::new);

        // Introducimos y eliminamos un caracter en cada uno de ellos.
        for (JTextField textField : textFields) {
            Document document = textField.getDocument();
            try {
                document.insertString(0, "x", null);
                document.remove(0, 1);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Registra una nueva compañía en el listado de compañías disponibles
     * por medio de los campos validados del formulario.
     * @see getCompaniaFromFields
     */
    private void registerCompania()
    {        
        // Generamos una nueva compañía a partir de los datos modificados.
        Compania compania;
        try {
            compania = getCompaniaFromFields();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }
        
        // Actualizamos el modelo de la lista de compañías disponibles con la
        // nueva compañía, actualizando indirectamente el Util.List<Compania> base.
        model.addCompania(compania);
        
        String message = String.format("Compañía [ %s ] registrada con éxito.", 
                compania.getNombre());
        JOptionPane.showMessageDialog(this, message,
                this.getName(), JOptionPane.INFORMATION_MESSAGE);
        
        this.dispose();
    }
    
    /**
     * Crea una nueva compañía a partir de los campos rellenados en el formulario,
     * solo si no existen violaciones de validación.
     * @return Compañía validada.
     * @throws IllegalArgumentException Si existen violaciones de validación.
     * @see allFieldsAreValid
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
        String nombre = tfNombre.getText().trim();
        String direccionSedeCentral = tfDireccionSedeCentral.getText().trim();
        
        Object municipio = cbMunicipioSedeCentral.getSelectedItem();
        String municipioSedeCentral = municipio == null ? "" : (String) municipio;
        
        String telefonoATA = tfTelefonoATA.getText();
        String telefonoATC = tfTelefonoATC.getText();
        
        return new Compania(prefijo, codigo, nombre, direccionSedeCentral, municipioSedeCentral, telefonoATA, telefonoATC);
    }
    
    /**
     * Comprueba que todas las etiquetas correspondientes a los mensajes de
     * validación esten vacíos, confirmando que los campos del formulario son válidos.
     * @return Verdadero si el formulario es valido, falso en su defecto.
     */
    private boolean allFieldsAreValid()
    {
        return lPrefijoWarning.getText().isEmpty() &&
                lCodigoWarning.getText().isEmpty() &&
                lNombreWarning.getText().isEmpty() &&
                lDireccionSedeCentralWarning.getText().isEmpty() &&
                lTelefonoATAWarning.getText().isEmpty() &&
                lTelefonoATCWarning.getText().isEmpty();
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
        pPrefijoSub = new javax.swing.JPanel();
        lPrefijo = new javax.swing.JLabel();
        tfPrefijo = new javax.swing.JTextField();
        lPrefijoWarning = new javax.swing.JLabel();
        pCodigo = new javax.swing.JPanel();
        pCodigoInput = new javax.swing.JPanel();
        lCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        pCodigoWarning = new javax.swing.JPanel();
        lCodigoWarning = new javax.swing.JLabel();
        pNombre = new javax.swing.JPanel();
        pNombreSub = new javax.swing.JPanel();
        lNombre = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
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
        bCancel = new javax.swing.JButton();
        bRegisterCompania = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        pCriticalData.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos obligatorios"));
        pCriticalData.setLayout(new javax.swing.BoxLayout(pCriticalData, javax.swing.BoxLayout.Y_AXIS));

        pPrefijo.setLayout(new java.awt.BorderLayout(5, 0));

        pPrefijoSub.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lPrefijo.setText("Prefijo:");
        pPrefijoSub.add(lPrefijo);

        tfPrefijo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfPrefijo.setMinimumSize(new java.awt.Dimension(34, 22));
        tfPrefijo.setPreferredSize(new java.awt.Dimension(34, 22));
        pPrefijoSub.add(tfPrefijo);

        lPrefijoWarning.setForeground(new java.awt.Color(204, 51, 0));
        lPrefijoWarning.setText("Warning message");
        lPrefijoWarning.setText("");
        pPrefijoSub.add(lPrefijoWarning);

        pPrefijo.add(pPrefijoSub, java.awt.BorderLayout.LINE_START);

        pCriticalData.add(pPrefijo);

        pCodigo.setLayout(new javax.swing.BoxLayout(pCodigo, javax.swing.BoxLayout.Y_AXIS));

        pCodigoInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lCodigo.setText("Código:");
        pCodigoInput.add(lCodigo);

        tfCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        tfCodigo.setMinimumSize(new java.awt.Dimension(38, 22));
        tfCodigo.setPreferredSize(new java.awt.Dimension(38, 22));
        pCodigoInput.add(tfCodigo);

        pCodigo.add(pCodigoInput);

        pCodigoWarning.setLayout(new java.awt.CardLayout());

        lCodigoWarning.setForeground(new java.awt.Color(204, 51, 0));
        lCodigoWarning.setText("Warning message");
        lCodigoWarning.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        lCodigoWarning.setText("");
        pCodigoWarning.add(lCodigoWarning, "card2");

        pCodigo.add(pCodigoWarning);

        pCriticalData.add(pCodigo);

        pNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        pNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 0));

        pNombreSub.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lNombre.setText("Nombre:");
        pNombreSub.add(lNombre);

        tfNombre.setMinimumSize(new java.awt.Dimension(272, 26));
        tfNombre.setPreferredSize(new java.awt.Dimension(272, 26));
        pNombreSub.add(tfNombre);

        lNombreWarning.setForeground(new java.awt.Color(204, 51, 0));
        lNombreWarning.setText("Warning message");
        lNombreWarning.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lNombreWarning.setText("");
        pNombreSub.add(lNombreWarning);

        pNombre.add(pNombreSub);

        pCriticalData.add(pNombre);

        getContentPane().add(pCriticalData);

        pData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        pData.setMinimumSize(new java.awt.Dimension(540, 140));
        pData.setPreferredSize(new java.awt.Dimension(540, 168));
        pData.setLayout(new javax.swing.BoxLayout(pData, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentral.setLayout(new javax.swing.BoxLayout(pDireccionSedeCentral, javax.swing.BoxLayout.Y_AXIS));

        pDireccionSedeCentralInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        pDireccionSedeCentralInput.setLayout(new java.awt.BorderLayout(5, 0));

        lDireccionSedeCentral.setText("Dirección sede central:");
        pDireccionSedeCentralInput.add(lDireccionSedeCentral, java.awt.BorderLayout.LINE_START);

        tfDireccionSedeCentral.setMinimumSize(new java.awt.Dimension(392, 26));
        tfDireccionSedeCentral.setPreferredSize(new java.awt.Dimension(392, 26));
        pDireccionSedeCentralInput.add(tfDireccionSedeCentral, java.awt.BorderLayout.CENTER);

        pDireccionSedeCentral.add(pDireccionSedeCentralInput);

        pDireccionSedeCentralWarning.setLayout(new java.awt.CardLayout());

        lDireccionSedeCentralWarning.setForeground(new java.awt.Color(204, 51, 0));
        lDireccionSedeCentralWarning.setText("Warning message");
        lDireccionSedeCentralWarning.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lDireccionSedeCentralWarning.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        lDireccionSedeCentralWarning.setText("");
        pDireccionSedeCentralWarning.add(lDireccionSedeCentralWarning, "card2");

        pDireccionSedeCentral.add(pDireccionSedeCentralWarning);

        pData.add(pDireccionSedeCentral);

        pMunicipioSedeCentral.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        lMunicipioSedeCentral.setText("Municipio sede central:");
        pMunicipioSedeCentral.add(lMunicipioSedeCentral);

        String[] municipioSedeCentralItems = Util.getMapMunicipios().keySet().toArray(new String[0]);
        cbMunicipioSedeCentral.setModel(new DefaultComboBoxModel<>(municipioSedeCentralItems));
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

        getContentPane().add(pData);

        pActions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        bCancel.setText("Cancelar");
        bCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bCancelActionPerformed(evt);
            }
        });

        bRegisterCompania.setText("Tramitar alta");
        bRegisterCompania.setEnabled(false);
        bRegisterCompania.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bRegisterCompaniaActionPerformed(evt);
            }
        });

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

        getContentPane().add(pActions);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
    {//GEN-HEADEREND:event_bCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_bCancelActionPerformed

    private void bRegisterCompaniaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bRegisterCompaniaActionPerformed
    {//GEN-HEADEREND:event_bRegisterCompaniaActionPerformed
        registerCompania();
    }//GEN-LAST:event_bRegisterCompaniaActionPerformed

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
    private javax.swing.JPanel pCodigoWarning;
    private javax.swing.JPanel pCriticalData;
    private javax.swing.JPanel pData;
    private javax.swing.JPanel pDireccionSedeCentral;
    private javax.swing.JPanel pDireccionSedeCentralInput;
    private javax.swing.JPanel pDireccionSedeCentralWarning;
    private javax.swing.JPanel pMunicipioSedeCentral;
    private javax.swing.JPanel pNombre;
    private javax.swing.JPanel pNombreSub;
    private javax.swing.JPanel pPrefijo;
    private javax.swing.JPanel pPrefijoSub;
    private javax.swing.JPanel pTelefonoATA;
    private javax.swing.JPanel pTelefonoATASub;
    private javax.swing.JPanel pTelefonoATC;
    private javax.swing.JPanel pTelefonoATCSub;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfDireccionSedeCentral;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfPrefijo;
    private javax.swing.JTextField tfTelefonoATA;
    private javax.swing.JTextField tfTelefonoATC;
    // End of variables declaration//GEN-END:variables
}
