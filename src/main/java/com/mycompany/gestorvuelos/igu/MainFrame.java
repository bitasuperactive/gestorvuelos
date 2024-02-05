/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestorvuelos.igu;

import com.mycompany.gestorvuelos.dto.models.CompaniaTableModel;
import com.mycompany.gestorvuelos.igu.logica.CompaniaSearcherListener;
import com.mycompany.gestorvuelos.igu.logica.SearchTypeEnum;
import com.mycompany.gestorvuelos.negocio.logica.ListManager;
import com.mycompany.gestorvuelos.negocio.logica.Util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
        initComponents();
        
        try {
            Util.initUtils("ABC");
            tResults.setModel(new CompaniaTableModel(Util.getListCompania(), true));
            tfCompaniaSearcher.getDocument().addDocumentListener(new CompaniaSearcherListener(cbSearchType, tResults));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        tResults = new javax.swing.JTable();
        pBuscador = new javax.swing.JPanel();
        cbSearchType = new javax.swing.JComboBox<>();
        tfCompaniaSearcher = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pCompanias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compañías", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        pCompanias.setLayout(new java.awt.BorderLayout(0, 10));

        pResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados de búsqueda"));
        pResults.setLayout(new javax.swing.BoxLayout(pResults, javax.swing.BoxLayout.LINE_AXIS));

        tResults.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tResults);

        pResults.add(jScrollPane1);

        pCompanias.add(pResults, java.awt.BorderLayout.CENTER);

        pBuscador.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscador"));
        pBuscador.setLayout(new java.awt.BorderLayout(10, 0));

        cbSearchType.setModel(new DefaultComboBoxModel<>(SearchTypeEnum.valuesToString()));
        pBuscador.add(cbSearchType, java.awt.BorderLayout.LINE_START);
        pBuscador.add(tfCompaniaSearcher, java.awt.BorderLayout.CENTER);

        pCompanias.add(pBuscador, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCompanias, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(356, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCompanias, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
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
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbSearchType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pBuscador;
    private javax.swing.JPanel pCompanias;
    private javax.swing.JPanel pResults;
    private javax.swing.JTable tResults;
    private javax.swing.JTextField tfCompaniaSearcher;
    // End of variables declaration//GEN-END:variables
}