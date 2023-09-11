/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.Client;
import MainRun.MainRun;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lcmin
 */
public class RoomFrm extends javax.swing.JFrame {

    /**
     * Creates new form ChatFrm
     */
    public RoomFrm() {
        initComponents();
        setTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Room_table = new javax.swing.JTable();
        btn_logout = new javax.swing.JButton();
        btn_createroom = new javax.swing.JButton();
        btn_FriendList = new javax.swing.JButton();
        txt_roomname = new javax.swing.JTextField();
        txt_notice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MULTI CHAT");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 25, 185, 39));

        Room_table.setBackground(new java.awt.Color(153, 255, 153));
        Room_table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Room_table.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Room_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Tên phòng", "Type"
            }
        ));
        Room_table.setColumnSelectionAllowed(true);
        Room_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Room_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Room_table);
        Room_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 394, 433));

        btn_logout.setText("Dang Xuat");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        jPanel1.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 186, -1));

        btn_createroom.setText("Tao Phong");
        btn_createroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createroomActionPerformed(evt);
            }
        });
        jPanel1.add(btn_createroom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, -1, -1));

        btn_FriendList.setText("Xem ban be");
        btn_FriendList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FriendListActionPerformed(evt);
            }
        });
        jPanel1.add(btn_FriendList, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 186, -1));
        jPanel1.add(txt_roomname, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 186, -1));
        jPanel1.add(txt_notice, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 0, 232, 44));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fw-bg-gradient.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void setTable(){
         String[] columnNames = {"id","name","type"};
         String[][] value = new String[MainRun.user.getRoomsIn().size()][3];
         for(int i=0;i<MainRun.user.getRoomsIn().size();i++){
             value[i][0] = MainRun.user.getRoomsIn().get(i).getId()+"";
             value[i][1] = MainRun.user.getRoomsIn().get(i).getName()+"";
             value[i][2] = MainRun.user.getRoomsIn().get(i).getType()+"";
         }
         DefaultTableModel tablemodel = new DefaultTableModel(value,columnNames){
         public boolean isCellEdittable(int row,int column){
             return false;
         }
         };
         Room_table.setModel(tablemodel);
         Room_table.setEditingColumn(0);
         Room_table.setEditingRow(0);
    }
    private void btn_FriendListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FriendListActionPerformed
    try {
            MainRun.controller.UpdateFriend();
        } catch (IOException ex) {
            Logger.getLogger(RoomFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_FriendListActionPerformed

    private void Room_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Room_tableMouseClicked
        // TODO add your handling code here:
        int index = Room_table.getSelectedRow();
        try {
            MainRun.controller.write(String.valueOf("JoinRoom,"+MainRun.user.getRoomsIn().get(index).getId()));
        } catch (IOException ex) {
            Logger.getLogger(RoomFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Room_tableMouseClicked

    private void btn_createroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createroomActionPerformed
        try {
            // TODO add your handling code here:
            String s = txt_roomname.getText();
            if(s.length()>0)
            {MainRun.controller.createroom(txt_roomname.getText());
                txt_roomname.setText("");
            }
        } catch (IOException ex) {
            Logger.getLogger(RoomFrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_createroomActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        try {
            // TODO add your handling code here:
            MainRun.controller.write("Logout");
            this.dispose();
            Client.loginFrm = new LoginFrm();
            Client.loginFrm.setLocationRelativeTo(null);
            Client.loginFrm.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(RoomFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_logoutActionPerformed
    public void Notice(String s){
        txt_notice.setText(s);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(RoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Room_table;
    private javax.swing.JButton btn_FriendList;
    private javax.swing.JButton btn_createroom;
    private javax.swing.JButton btn_logout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txt_notice;
    private javax.swing.JTextField txt_roomname;
    // End of variables declaration//GEN-END:variables
}
