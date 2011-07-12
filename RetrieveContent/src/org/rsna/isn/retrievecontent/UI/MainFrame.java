/* Copyright (c) <2011>, <Radiological Society of North America>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <RSNA> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package org.rsna.isn.retrievecontent.UI;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Color;
import org.rsna.isn.retrievecontent.retrieve.*;

/**
 * Main Form
 *
 * @version @author
 * 1.0.0    Wendy Zhu
 *
 */
public class MainFrame extends javax.swing.JFrame {

    private List<DocumentInfo> docInfoList;
    private String tokenID;
    Properties props = new Properties();

    /** Creates new form MainFrame */
    public MainFrame() {
        initComponents();
        this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage("RSNA-Image-Share-icon.jpg"));

        btnRetrieve.setEnabled(false);
        lblProgress.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        lblProgress = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnRetrieve = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblExamID = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtExamID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDOB = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnShow = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbStudies = new javax.swing.JTable();
        lblMsg = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RSNA Image Retrieve Tool");
        setBackground(getBackground());

        jLayeredPane1.setOpaque(true);

        lblProgress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/rsna/isn/retrievecontent/UI/progress.gif"))); // NOI18N
        lblProgress.setOpaque(true);
        lblProgress.setBounds(380, 230, 250, 80);
        jLayeredPane1.add(lblProgress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Simplified Arabic", 1, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Retrieve Studies from Image Sharing Clearing House");
        jLabel3.setBounds(30, 80, 480, 30);
        jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnRetrieve.setText("Retrieve Studies");
        btnRetrieve.setToolTipText("Retrieve studies from ClearingHouse");
        btnRetrieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrieveActionPerformed(evt);
            }
        });
        btnRetrieve.setBounds(310, 260, 130, 23);
        jLayeredPane1.add(btnRetrieve, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel4.setForeground(new java.awt.Color(230, 230, 230));
        jLabel4.setText("(Format: YYYYMMDD)");
        jLabel4.setBounds(240, 180, 120, 20);
        jLayeredPane1.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblExamID.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblExamID.setForeground(new java.awt.Color(230, 230, 230));
        lblExamID.setText("ExamID: ");
        lblExamID.setBounds(30, 150, 50, 14);
        jLayeredPane1.add(lblExamID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtPassword.setBounds(120, 210, 110, 20);
        jLayeredPane1.add(txtPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtExamID.setBounds(120, 150, 223, 20);
        jLayeredPane1.add(txtExamID, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setForeground(new java.awt.Color(230, 230, 230));
        jLabel1.setText("Date of Birth:");
        jLabel1.setBounds(30, 180, 80, 14);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtDOB.setBounds(120, 180, 110, 20);
        jLayeredPane1.add(txtDOB, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setForeground(new java.awt.Color(230, 230, 230));
        jLabel2.setText("Password:");
        jLabel2.setBounds(30, 210, 70, 14);
        jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnShow.setText("Show Studies");
        btnShow.setToolTipText("Query studies from ClearingHouse");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });
        btnShow.setBounds(30, 260, 130, 23);
        jLayeredPane1.add(btnShow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        tbStudies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbStudies.setColumnSelectionAllowed(true);
        tbStudies.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbStudies);
        tbStudies.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jScrollPane2.setBounds(10, 350, 980, 170);
        jLayeredPane1.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblMsg.setForeground(new java.awt.Color(255, 153, 0));
        lblMsg.setText("  ");
        lblMsg.setAutoscrolls(true);
        lblMsg.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblMsg.setBounds(10, 300, 980, 40);
        jLayeredPane1.add(lblMsg, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/rsna/isn/retrievecontent/UI/RSNA-Image-Share-bg.jpg"))); // NOI18N
        jLabel5.setBounds(0, 0, 1010, 610);
        jLayeredPane1.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        lblMsg.setText(null);
        try
        {
            String examID=txtExamID.getText().replace("-","").toLowerCase();
            tokenID=TransHash.gen(examID, txtDOB.getText(), txtPassword.getText());
            lblMsg.setText("Querying study for exam : " + txtExamID.getText());

            (new ShowThread()).start();
        } catch (Exception e) {
            e.printStackTrace();
            lblMsg.setText(e.getMessage());
        }
    }//GEN-LAST:event_btnShowActionPerformed

    private void RetrieveDocs(String tokenID) {
        String msg="";
        try
        {
            CustomTableModel model = new CustomTableModel();
            tbStudies.setModel(model);
            CustomTableCellRenderer render=new CustomTableCellRenderer();
            tbStudies.setDefaultRenderer(Object.class, render);

            TableColumnModel cModel = tbStudies.getColumnModel();
            model.addColumn("Select");
            model.addColumn("PatientName");
            model.addColumn("StudyUID");
            model.addColumn("Study Description");
            model.addColumn("StudyDate");

            JCheckBox checkBox = new javax.swing.JCheckBox();
            tbStudies.getColumn("Select").setCellEditor(new DefaultCellEditor(checkBox));

            cModel.getColumn(0).setMaxWidth(40);
            cModel.getColumn(0).setMinWidth(10);
            cModel.getColumn(0).setWidth(40);
            cModel.getColumn(0).setPreferredWidth(40);
            cModel.getColumn(4).setMaxWidth(100);
            cModel.getColumn(4).setMinWidth(50);
            cModel.getColumn(4).setWidth(100);
            cModel.getColumn(4).setPreferredWidth(100);

            RetrieveDocumentSet act = new RetrieveDocumentSet();
            //docList = act.retrievedocs(tokenID).getDocument();
            docInfoList = act.RetrieveDocuments(tokenID);

            if (act.NumOfDocs > 0)
            {
                btnRetrieve.setEnabled(true);
                msg = act.NumOfDocs + " document(s) were found.";
                if (docInfoList.size()>0)
                {
                    msg += "\n (" + docInfoList.size() + " KOS file(s))";
                    for (int i=0; i<docInfoList.size(); i++)
                    {
                        DocumentInfo docInfo=docInfoList.get(i);
                        model.addRow(new Object[]{
                            new Boolean(true),
                            docInfo.getPatientName(),
                            docInfo.getStudyInstanceUID(),
                            docInfo.getStudyDescription(),
                            docInfo.getStudyDate()});
                    }
                    model.rowColours=model.initRowColour(docInfoList.size());
                }
                else
                {
                    msg += "\n (No KOS file)";
                }
                lblMsg.setText(msg);
            }
            else
            {
                lblMsg.setText("No document was found for " + txtExamID.getText());
            }
        } catch (Exception e) {
            lblMsg.setText(e.getMessage());
        }
    }

    private void btnRetrieveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetrieveActionPerformed
        // TODO add your handling code here:
        lblMsg.setText("Retrieving studies...");

        (new RetrieveThread()).start();
    }//GEN-LAST:event_btnRetrieveActionPerformed

    class ShowThread extends Thread {
        public void run() {
            btnRetrieve.setEnabled(false);
            btnShow.setEnabled(false);
            lblProgress.setVisible(true);

            //SwingUtilities.invokeLater(update);
            RetrieveDocs(tokenID);

            //SwingUtilities.invokeLater(finish);

            btnShow.setEnabled(true);
            lblProgress.setVisible(false);
        }
    }

    class RetrieveThread extends Thread {
        public void run() {
            int numOfDocs = 0 ;
            java.util.Date date= new java.util.Date();
            java.sql.Timestamp startTime=new java.sql.Timestamp(date.getTime());

            btnRetrieve.setEnabled(false);
            btnShow.setEnabled(false);
            lblProgress.setVisible(true);

            try {
                CustomTableModel model = (CustomTableModel)tbStudies.getModel();
                int rowcount = model.getRowCount();
                for(int row = 0; row < rowcount; row++){
                    Boolean selected = (Boolean)tbStudies.getModel().getValueAt(row, 0);
                    if (selected) {
                        String studyUID = (String)tbStudies.getModel().getValueAt(row, 2);
                        for (int i=0; i<docInfoList.size(); i++)
                        {
                            DocumentInfo docInfo=docInfoList.get(i);
                            if (studyUID == docInfo.getStudyInstanceUID()) {
                                lblMsg.setText("Retrieving study [" + docInfo.getStudyInstanceUID() + "]...");
                                numOfDocs += RetrieveDocuments.RetrieveStudy(docInfo);
                                model.setRowColour(i, Color.LIGHT_GRAY);

                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                lblMsg.setText("Error for  " + tokenID + " is " + e.getMessage());
            }

            btnRetrieve.setEnabled(true);
            btnShow.setEnabled(true);
            lblProgress.setVisible(false);

            date= new java.util.Date();
            java.sql.Timestamp endTime=new java.sql.Timestamp(date.getTime());
            long diffSeconds =(endTime.getTime()/1000)-(startTime.getTime()/1000);
            lblMsg.setText(numOfDocs + " images were retrieved [in " + diffSeconds + " sec].");
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        Configuration.init();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private void ClearInput() {
        txtExamID.setText(null);
        txtDOB.setText(null);
        txtPassword.setText(null);
    }

    static class CustomTableModel extends DefaultTableModel {
        public CustomTableModel() {
            super();
        }
        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }
        public ArrayList<Color> rowColours = new ArrayList<Color>();
        public ArrayList<Color> initRowColour(int rowCount){
            ArrayList<Color> list = new ArrayList<Color>();
            for (int i=0; i< rowCount; i++){
                list.add(Color.WHITE);
            }
            return list;
        }
        public void setRowColour(int row, Color c) {
            rowColours.set(row, c);
            fireTableRowsUpdated(row, row);
        }
        public Color getRowColour(int row) {
            return rowColours.get(row);
        }
        public Class getColumnClass(int col) {
            Vector v = (Vector)dataVector.elementAt(0);
            return v.elementAt(col).getClass();
        }
        public boolean isCellEditable(int row, int col) {
            //Class columnClass = getColumnClass(col);
            //return columnClass != ImageIcon.class && columnClass != Date.class;
            return col == 0;
        }
    }

    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            CustomTableModel model = (CustomTableModel) table.getModel();
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(model.getRowColour(row));

            return c;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRetrieve;
    private javax.swing.JButton btnShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblExamID;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JTable tbStudies;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtExamID;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables

}
