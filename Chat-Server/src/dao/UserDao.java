/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Room;
import model.User;

/**
 *
 * @author vvt
 */
public class UserDao extends DAO{
    public UserDao() {
    }
    
    public String CheckLogin(User user){
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT isOnline FROM user WHERE username = ? AND password = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                user.setIsOnline(rs.getBoolean("isOnline"));
                
                if(user.getIsOnline()){
                    return "Logined";
                }
                else{
                    // Cập nhật trạng thái online của user
                    SetOnline(user);
                    
                    return "Success-Login";
                }
            }else{
                return "Fail-Login";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "Fail-Login";
    }
    
    public void SetOnline(User user){
        try {
            user.setIsOnline(true);
            
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET isOnline = true WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            preparedStatement.executeUpdate();
            
            preparedStatement = con.prepareStatement("UPDATE friend SET isOnline = true WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void SetOffline(User user){
        try {
            user.setIsOnline(true);
            
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET isOnline = false WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            preparedStatement.executeUpdate();
            
            preparedStatement = con.prepareStatement("UPDATE friend SET isOnline = false WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String Register(User user){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT isOnline FROM user WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return "Exits";
            }
            else{
                try {
                    PreparedStatement preparedStatement1 = con.prepareStatement("INSERT INTO user(username, password, isOnline) VALUES (?,?,?);");
                    preparedStatement1.setString(1, user.getUsername());
                    preparedStatement1.setString(2, user.getPassword());
                    preparedStatement1.setBoolean(3, false);
                        
                    int counter = preparedStatement1.executeUpdate();
                    
                    if(counter > 0)
                        return "Success-Register";
                    else
                        return "Fail-Register";
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Fail-Register";
    }
    
    //Lấy ds Friend
    public void GetFiend(User user){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT username, isOnline FROM friend WHERE ownerUsername = ?");
            preparedStatement.setString(1, user.getUsername());
                            
            ResultSet rs = preparedStatement.executeQuery();
                            
            ArrayList<User> friends = new ArrayList<>();
            while(rs.next()){
                friends.add(new User(rs.getString("username"), rs.getBoolean("isOnline")));
            }
            
            user.setFriends(friends);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Lấy thông tin từng room
    public void GetRoom(User user){
        RoomDao rd = new RoomDao();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT roomId FROM user_in_room WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            ArrayList<Room> roomsIn  = new ArrayList<>();
            while(rs.next()){
                Room room = new Room(rs.getInt("roomId"));
                rd.GetRoomInfo(room);
                roomsIn.add(room);
            }
                            
            user.setRoomsIn(roomsIn);          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<User> SearchUser(String userName){
        ArrayList<User> listUsers = new ArrayList<>();
        
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT username, isOnline FROM user WHERE username LIKE '%" + userName + "%'");
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                User u = new User(rs.getString("username"), rs.getBoolean("isOnline"));
                listUsers.add(u);
            }       
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listUsers;
    }
    
    
    public String AddFriend(String friendName, boolean isOnline, User user){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO friend(username, isOnline, ownerUsername) VALUES (?,?,?), (?,?,?);");
            preparedStatement.setString(1, friendName);
            preparedStatement.setBoolean(2, isOnline);
            preparedStatement.setString(3, user.getUsername());
            
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setBoolean(5, user.getIsOnline());
            preparedStatement.setString(6, friendName);
                    
            int counter = preparedStatement.executeUpdate();
                    
            if(counter > 0)
                return "Success-AddFriend";
            else
                return "Fail-AddFriend";
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Fail-AddFriend";
    }
    
    public void DeleteFriend(String username, String friendName){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM friend WHERE ownerUsername = ? AND username = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, friendName);
                                    
            preparedStatement.executeUpdate();
            
            preparedStatement = con.prepareStatement("DELETE FROM friend WHERE ownerUsername = ? AND username = ?;");
            preparedStatement.setString(1, friendName);
            preparedStatement.setString(2, username);
                                    
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
