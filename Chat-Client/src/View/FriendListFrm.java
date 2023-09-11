/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.Client;
import MainRun.MainRun;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.User;

/**
 *
 * @author lcmin
 */
public class FriendListFrm extends javax.swing.JFrame {
    RoomFrm roomFrm ;
    ArrayList<User> searchUsers;
    /**
     * Creates new form FriendListFrm
     */
    public FriendListFrm(RoomFrm roomFrm) {
        initComponents();
        this.roomFrm = roomFrm;
        setTable(MainRun.user.getFriends());
    }

    private FriendListFrm() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        Friend_table = new javax.swing.JTable();
        btn_return = new javax.swing.JButton();
        btn_searchfriend = new javax.swing.JButton();
        txt_searchfriend = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchlistfriend_table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("List Friend");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 24, 154, 45));

        Friend_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Username", "Activity"
            }
        ));
        Friend_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Friend_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Friend_table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 143, 273, 293));

        btn_return.setText("Return");
        btn_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_returnActionPerformed(evt);
            }
        });
        jPanel1.add(btn_return, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 452, 80, 30));

        btn_searchfriend.setText("TIm Kiem Ban Be");
        btn_searchfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchfriendActionPerformed(evt);
            }
        });
        jPanel1.add(btn_searchfriend, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 75, -1, -1));
        jPanel1.add(txt_searchfriend, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 115, 119, -1));

        searchlistfriend_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        searchlistfriend_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchlistfriend_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(searchlistfriend_table);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 143, 242, 287));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fw-bg-gradient.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_returnActionPerformed
        // TODO add your handling code here:
        this.dispose();
        MainRun.user.getFriends().clear();
        roomFrm.setVisible(true);
    }//GEN-LAST:event_btn_returnActionPerformed

    private void btn_searchfriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchfriendActionPerformed
        // TODO add your handling code here:
        boolean kt = true;
        String s = txt_searchfriend.getText();
        if(s.length()>0){
        for(int i=0;i<MainRun.user.getFriends().size();i++){
            if(MainRun.user.getFriends().get(i).getUsername().equals(txt_searchfriend.getText())) 
            {
                JOptionPane.showMessageDialog(this,"Ban be da ton tai");
                kt = false;
                break;
            }
        }
        if(kt==true) try {
            MainRun.controller.write("SearchUser,"+txt_searchfriend.getText());
            txt_searchfriend.setText("");
        } catch (IOException ex) {
            Logger.getLogger(FriendListFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }//GEN-LAST:event_btn_searchfriendActionPerformed

    private void searchlistfriend_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchlistfriend_tableMouseClicked
        // TODO add your handling code here:
        int index = searchlistfriend_table.getSelectedRow();
        boolean kt = false;
        for(int i=0;i<MainRun.user.getFriends().size();i++){
            if(MainRun.user.getFriends().get(i).getUsername().equals(searchUsers.get(index).getUsername())) {
                kt = true;
                break;
            }
        }
        if(kt==false){
        MainRun.user.getFriends().add(searchUsers.get(index));
        this.setTable(MainRun.user.getFriends());
        try {
            MainRun.controller.write("AddFriend,"+searchUsers.get(index).getUsername()+","+searchUsers.get(index).getIsOnline());
        } catch (IOException ex) {
            Logger.getLogger(FriendListFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
            JOptionPane.showMessageDialog(this,"Ban da ton tai trong danh sach");
        }
    }//GEN-LAST:event_searchlistfriend_tableMouseClicked

    private void Friend_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Friend_tableMouseClicked
        // TODO add your handling code here:
        int index = Friend_table.getSelectedRow();
        try {
            MainRun.controller.write("DeleteFriend,"+MainRun.user.getFriends().get(index).getUsername());
            MainRun.user.getFriends().remove(index);
            this.setTable(MainRun.user.getFriends());
        } catch (IOException ex) {
            Logger.getLogger(FriendListFrm.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_Friend_tableMouseClicked

    /**
     * @param args the command line arguments
     */
    public void setTable(ArrayList<User> users){
         String[] columnNames = {"Username","Activity"};
         String[][] value = new String[users.size()][2];
         for(int i=0;i<users.size();i++){
             value[i][0] = users.get(i).getUsername()+"";
             value[i][1] = users.get(i).getIsOnline()+"";
             
         }
         DefaultTableModel tablemodel = new DefaultTableModel(value,columnNames){
         public boolean isCellEdittable(int row,int column){
             return false;
         }
         };
         Friend_table.setModel(tablemodel);
    }
    
    public void updatefriendlist(){
        if(Client.friendListFrm.isActive()==true)
        {   Client.friendListFrm.dispose();
            Client.friendListFrm = new FriendListFrm(Client.roomFrm);
            Client.friendListFrm.setLocationRelativeTo(null);
            Client.friendListFrm.setVisible(true);
        }
    }
    
    public void setTableFriend(ArrayList<User> users){
         String[] columnNames = {"Username","Activity"};
         String[][] value = new String[users.size()][2];
         for(int i=0;i<users.size();i++){
             value[i][0] = users.get(i).getUsername()+"";
             value[i][1] = users.get(i).getIsOnline()+"";
             
         }
         DefaultTableModel tablemodel = new DefaultTableModel(value,columnNames){
         public boolean isCellEdittable(int row,int column){
             return false;
         }
         };
         searchUsers = users;
         searchlistfriend_table.setModel(tablemodel);
    }
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
            java.util.logging.Logger.getLogger(FriendListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FriendListFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Friend_table;
    private javax.swing.JButton btn_return;
    private javax.swing.JButton btn_searchfriend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable searchlistfriend_table;
    private javax.swing.JTextField txt_searchfriend;
    // End of variables declaration//GEN-END:variables
}
