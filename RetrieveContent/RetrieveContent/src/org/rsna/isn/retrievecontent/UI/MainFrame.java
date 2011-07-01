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

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Properties;
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
        btnRetrieve.setEnabled(false);
        pbar.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblExamID = new javax.swing.JLabel();
        txtExamID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDOB = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnShow = new javax.swing.JButton();
        btnRetrieve = new javax.swing.JButton();
        lblMsg = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        pbar = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbStudies = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RSNA Image Retrieve Tool");

        lblExamID.setText("ExamID: ");

        txtExamID.setText("jjpwx3tj");

        jLabel1.setText("Date of Birth:");

        txtDOB.setText("19741212");

        jLabel2.setText("Password:");

        btnShow.setText("Show Studies");
        btnShow.setToolTipText("Retrieve study from CH by hashed transaction identifier");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        btnRetrieve.setText("Retrieve Studies");
        btnRetrieve.setToolTipText("Retrieve study from CH by typed in user token");
        btnRetrieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrieveActionPerformed(evt);
            }
        });

        lblMsg.setText("  ");
        lblMsg.setAutoscrolls(true);
        lblMsg.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Retrieve Studies from Image Sharing Clearing House");

        jLabel4.setText("(Format: YYYYMMDD)");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblExamID)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(btnShow))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtExamID, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRetrieve)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDOB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4))))
                            .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                            .addComponent(pbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExamID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExamID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShow)
                    .addComponent(btnRetrieve))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
        lblMsg.setText(null);
        try
        {
            String examID=txtExamID.getText().replace("-","").toLowerCase();
            tokenID=TransHash.gen(examID, txtDOB.getText(), txtPassword.getText());
            lblMsg.setText("Querying study for ID:" + tokenID);
            pbar.setValue(0);
            pbar.setVisible(true);

            (new ShowThread()).start();
        } catch (Exception e) {
            e.printStackTrace();
            lblMsg.setText(e.getMessage());
        }
    }//GEN-LAST:event_btnShowActionPerformed

    private void RetrieveDocs(String tokenID)
    {
        String msg="";
        try
        {
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
            tbStudies.setModel(model);
            model.addColumn("PatientName");
            model.addColumn("StudyUID");
            model.addColumn("Study Description");
            model.addColumn("Modality");
            model.addColumn("StudyDate");
            tbStudies.setRowSelectionAllowed(true);
            
            CHGetDocumentSets act = new CHGetDocumentSets();
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
                            docInfo.getPatientName(),
                            docInfo.getStudyInstanceUID(),
                            docInfo.getStudyDescription(),
                            docInfo.getModality(),
                            docInfo.getStudyDate()});
                    }
                }
                else
                {
                    msg += "\n (No KOS file)";
                }
                lblMsg.setText(msg);
            }
            else
            {
                lblMsg.setText("No document was found for " + tokenID);
            }
        } catch (Exception e) {
            lblMsg.setText(e.getMessage());
        }
    }

    private void btnRetrieveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetrieveActionPerformed
        // TODO add your handling code here:
        lblMsg.setText("Retrieving studies...");
        pbar.setValue(0);
        pbar.setVisible(true);

        (new RetrieveThread()).start();
    }//GEN-LAST:event_btnRetrieveActionPerformed

    class ShowThread extends Thread {
        Runnable update, finish;
        int value, min, max, increment;

        public ShowThread() {
            max = pbar.getMaximum();
            min = pbar.getMinimum();

            update = new Runnable() {
                public void run() {
                    value = pbar.getValue() + increment;
                    updateProgressBar(value);
                }
            };
            finish =new Runnable() {
                public void run() {
                    updateProgressBar(min);
                }
            };
        }

        public void run() {
            btnRetrieve.setEnabled(false);
            btnShow.setEnabled(false);
            increment = (max - min) / 20;
            SwingUtilities.invokeLater(update);
            RetrieveDocs(tokenID);

//            while(value + increment <= max) {
//                simulateTimeConsumingActivity();
//                SwingUtilities.invokeLater(update);
//            }
            SwingUtilities.invokeLater(finish);

            btnShow.setEnabled(true);
            pbar.setVisible(false);
        }

        private void updateProgressBar(int value) {
            pbar.setValue(value);
        }
    }

    class RetrieveThread extends Thread {
        Runnable update, finish;
        int value, min, max, increment;

        public RetrieveThread() {
            max = pbar.getMaximum();
            min = pbar.getMinimum();
            increment = 1;

            update = new Runnable() {
                public void run() {
                    value = pbar.getValue() + increment;
                    updateProgressBar(value);
                }
            };
            finish =new Runnable() {
                public void run() {
                    updateProgressBar(min);
                }
            };
        }

        public void run() {
            java.util.Date date= new java.util.Date();
            java.sql.Timestamp startTime=new java.sql.Timestamp(date.getTime());
            btnRetrieve.setEnabled(false);
            btnShow.setEnabled(false);

            Rad69ServiceDataType input;
            String seriesInstanceUID;
            String sopInstanceUID;
            ArrayList<String> seriesInstanceUIDList;
            ArrayList<String> sopInstanceUIDList;
            HashMap<String,ArrayList<String>> images;
            int numOfDocs = 0 ;
            int barSize = 5;

            try {
                increment=(max - min) / barSize;
                SwingUtilities.invokeLater(update);

                for (int i=0; i<docInfoList.size(); i++)
                {
                    DocumentInfo docInfo=docInfoList.get(i);
                    lblMsg.setText("Retrieving study [" + docInfo.getStudyInstanceUID() + "]...");
                    
                    input = new Rad69ServiceDataType();
                    input.setRsnaUID(tokenID);
                    input.setStudyInstanceUID(docInfo.getStudyInstanceUID());

                    seriesInstanceUIDList = docInfo.getSeriesInstanceUIDList();
                    images = docInfo.getImages();

                    if (seriesInstanceUIDList != null)
                    {
                        Iterator seriesItr = seriesInstanceUIDList.iterator();
                        while (seriesItr.hasNext()){
                            seriesInstanceUID = (String) seriesItr.next();
                            input.setSeriesInstanceUID(seriesInstanceUID);

                            sopInstanceUIDList = (ArrayList<String>) images.get(seriesInstanceUID);                            
                            if (sopInstanceUIDList != null)
                            {
                                Iterator imagesItr = sopInstanceUIDList.iterator();
                                barSize += sopInstanceUIDList.size();

                                while (imagesItr.hasNext()){
                                    sopInstanceUID = (String) imagesItr.next();
                                    input.setDocumentUniqueId(sopInstanceUID);
                                    int status = RetrieveDocument.retrieveImage(input);
                                    numOfDocs++;

                                    increment = ((max - min) / docInfoList.size()) / barSize;
                                    SwingUtilities.invokeLater(update);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                lblMsg.setText("Error for  " + tokenID + " is " + e.getMessage());
            }

            SwingUtilities.invokeLater(finish);
            
            btnRetrieve.setEnabled(true);
            btnShow.setEnabled(true);
            pbar.setVisible(false);
            java.sql.Timestamp endTime=new java.sql.Timestamp(date.getTime());
            long diffSeconds =(startTime.getTime()/1000)-(endTime.getTime()/1000);
            lblMsg.setText(numOfDocs + " images were retrieved in " + diffSeconds + " sec.");
        }

        private void updateProgressBar(int value) {
            pbar.setValue(value);
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        File keystore=new File("c:/rsna/config/keystore.jks");
        System.setProperty("javax.net.ssl.keyStore", keystore.getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", "edge1234");

        File truststore=new File("c:/rsna/config/truststore.jks");
        System.setProperty("javax.net.ssl.trustStore", truststore.getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "edge1234");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRetrieve;
    private javax.swing.JButton btnShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblExamID;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JProgressBar pbar;
    private javax.swing.JTable tbStudies;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtExamID;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables

}