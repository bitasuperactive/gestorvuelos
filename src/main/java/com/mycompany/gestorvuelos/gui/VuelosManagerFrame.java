package com.mycompany.gestorvuelos.gui;

import com.mycompany.gestorvuelos.business.logic.ListManager;
import com.mycompany.gestorvuelos.business.logic.Util;
import com.mycompany.gestorvuelos.dto.VueloBase;
import com.mycompany.gestorvuelos.dto.VueloDiario;
import com.mycompany.gestorvuelos.gui.models.VueloBaseTableModel;
import com.mycompany.gestorvuelos.gui.models.VueloDiarioTableModel;
import java.util.List;
import javax.swing.event.ListSelectionEvent;

/**
 * Gestor de vuelos base y diarios.
 */
public class VuelosManagerFrame extends javax.swing.JFrame
{

    /**
     * Crea un nuevo formulario VuelosManagerFrame.
     */
    public VuelosManagerFrame()
    {
        Util.initUtils("ABC");
        initComponents();
        tVuelosBase.setModel(new VueloBaseTableModel(Util.getListVueloBase()));
        
        tVuelosBase.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            var model = (VueloBaseTableModel) tVuelosBase.getModel();
            int selectedRowIndex = tVuelosBase.getSelectedRow();
            VueloBase selectedVueloBase = model.getVueloBaseAt(selectedRowIndex);
            
            // Recuperar los vuelos diarios del vuelo base.
            List<VueloDiario> listVueloDiario = ListManager.getListVueloDiarioByVueloBase(selectedVueloBase.getCodigo());
            tVuelosDiarios.setModel(new VueloDiarioTableModel(listVueloDiario));
            
            // Habilitar opciones.
            mVuelosBase.setEnabled(selectedRowIndex != -1);
            miVBModify.setEnabled(!ListManager.isThereVueloDiarioForToday(listVueloDiario));
        });
        
        tVuelosDiarios.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int selectedRowIndex = tVuelosDiarios.getSelectedRow();
            mVuelosDiarios.setEnabled(selectedRowIndex != - 1);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pVuelosBase = new javax.swing.JPanel();
        jcpVuelosBase = new javax.swing.JScrollPane();
        tVuelosBase = new javax.swing.JTable();
        pVuelosDiarios = new javax.swing.JPanel();
        jspVuelosDiarios = new javax.swing.JScrollPane();
        tVuelosDiarios = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mVuelosBase = new javax.swing.JMenu();
        miVBNew = new javax.swing.JMenuItem();
        miVBModify = new javax.swing.JMenuItem();
        mVuelosDiarios = new javax.swing.JMenu();
        miVDNew = new javax.swing.JMenuItem();
        miVDModify = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestor de vuelos");

        pVuelosBase.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vuelos base", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pVuelosBase.setLayout(new javax.swing.BoxLayout(pVuelosBase, javax.swing.BoxLayout.Y_AXIS));

        tVuelosBase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tVuelosBase.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jcpVuelosBase.setViewportView(tVuelosBase);

        pVuelosBase.add(jcpVuelosBase);

        pVuelosDiarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vuelos diarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pVuelosDiarios.setLayout(new javax.swing.BoxLayout(pVuelosDiarios, javax.swing.BoxLayout.Y_AXIS));

        tVuelosDiarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tVuelosDiarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspVuelosDiarios.setViewportView(tVuelosDiarios);

        pVuelosDiarios.add(jspVuelosDiarios);

        jMenu1.setText("Opciones");

        mVuelosBase.setText("Vuelos base");
        mVuelosBase.setEnabled(false);

        miVBNew.setText("Alta...");
        mVuelosBase.add(miVBNew);

        miVBModify.setText("Modificar...");
        miVBModify.setEnabled(false);
        mVuelosBase.add(miVBModify);

        jMenu1.add(mVuelosBase);

        mVuelosDiarios.setText("Vuelos diarios");
        mVuelosDiarios.setEnabled(false);

        miVDNew.setText("Alta..");
        mVuelosDiarios.add(miVDNew);

        miVDModify.setText("Modificar...");
        mVuelosDiarios.add(miVDModify);

        jMenu1.add(mVuelosDiarios);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pVuelosDiarios, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addComponent(pVuelosBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pVuelosBase, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pVuelosDiarios, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VuelosManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VuelosManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VuelosManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VuelosManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VuelosManagerFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jcpVuelosBase;
    private javax.swing.JScrollPane jspVuelosDiarios;
    private javax.swing.JMenu mVuelosBase;
    private javax.swing.JMenu mVuelosDiarios;
    private javax.swing.JMenuItem miVBModify;
    private javax.swing.JMenuItem miVBNew;
    private javax.swing.JMenuItem miVDModify;
    private javax.swing.JMenuItem miVDNew;
    private javax.swing.JPanel pVuelosBase;
    private javax.swing.JPanel pVuelosDiarios;
    private javax.swing.JTable tVuelosBase;
    private javax.swing.JTable tVuelosDiarios;
    // End of variables declaration//GEN-END:variables
}
