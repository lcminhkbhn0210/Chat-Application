/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Room;
import model.StringMessage;
import model.User;

/**
 *
 * @author vvt
 */
public class RoomDao extends DAO{

    public RoomDao() {
    }
    
    public void GetRoomInfo(Room room){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT name, type FROM room WHERE id = ?");
            preparedStatement.setInt(1, room.getId());
                                    
            ResultSet rs = preparedStatement.executeQuery();
                                    
            if(rs.next()){
                room.setName(rs.getString("name"));
                room.setType(rs.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Room CreateRooms(String roomName, User user){
        Room room = new Room();
        try {
            room.setName(roomName);
            
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO room(name, type) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, room.getName());
            preparedStatement.setString(2, room.getType());
                                    
            preparedStatement.executeUpdate();
                                  
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            
            if(generatedKeys.next()){
                room.setId(generatedKeys.getInt(1));
                
                PreparedStatement preparedStatement2 = con.prepareStatement("INSERT INTO user_in_room(roomId, username) VALUES (?,?);");
                preparedStatement2.setInt(1, room.getId());
                preparedStatement2.setString(2, user.getUsername());
                                    
                preparedStatement2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  room;
    }
    
    public void GetAllRoomInfo(Room room){
        room.getUsersInRoom().clear();
        room.getStringMessages().clear();
        room.getFileMessages().clear();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT username FROM user_in_room WHERE roomId = ?");
            preparedStatement.setInt(1, room.getId());
                                    
            ResultSet rs = preparedStatement.executeQuery();
                                    
            while(rs.next()){
                User u = new User();
                u.setUsername(rs.getString("username"));
                room.getUsersInRoom().add(u);
            }
            
            preparedStatement = con.prepareStatement("SELECT username, content, serial FROM text_message WHERE roomId = ?");
            preparedStatement.setInt(1, room.getId());
            
            rs = preparedStatement.executeQuery();
                                    
            while(rs.next()){
                StringMessage mes = new StringMessage(rs.getString("content"));
                mes.setSerial(rs.getInt("serial"));
                mes.setUserName(rs.getString("username"));
                room.getStringMessages().add(mes);
            }
            
            /*
            //Get file message
            preparedStatement = con.prepareStatement("SELECT username, content, serial FROM text_message WHERE roomId = ?");
            preparedStatement.setInt(1, room.getId());
            
            rs = preparedStatement.executeQuery();
                                    
            while(rs.next()){
                StringMessage mes = new StringMessage(rs.getString("content"));
                mes.setSerial(rs.getInt("serial"));
                mes.setUserName(rs.getString("username"));
                room.getStringMessages().add(mes);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String AddMember(String username,int roomId){
        try {
            PreparedStatement preparedStatement_1 = con.prepareStatement("SELECT isOnline FROM user WHERE username = ?");
            preparedStatement_1.setString(1, username);
            
            ResultSet rs_1 = preparedStatement_1.executeQuery();
            
            if(rs_1.next()){
                PreparedStatement preparedStatement = con.prepareStatement("SELECT id FROM user_in_room WHERE roomId = ? AND username = ?");
                preparedStatement.setInt(1, roomId);
                preparedStatement.setString(2, username);

                ResultSet rs = preparedStatement.executeQuery();

                if(rs.next()){
                    return "AlreadyInRoom";
                }
                else{
                    preparedStatement = con.prepareStatement("INSERT INTO user_in_room(roomId, username) VALUES (?,?);");
                    preparedStatement.setInt(1, roomId);
                    preparedStatement.setString(2, username);

                    int counter = preparedStatement.executeUpdate();

                    if(counter > 0){

                        preparedStatement = con.prepareStatement("UPDATE room SET type = '1-n' WHERE id = ?");
                        preparedStatement.setInt(1, roomId);

                        preparedStatement.executeUpdate();

                        return "Success-AddMember";
                    }
                    else
                        return "Fail-AddMember";
                }
            }
            else
                return "UserNotExits";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Fail-AddMember";
    }
    
    public void UpdateStringChat(StringMessage sm, int roomId){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO text_message(roomId, username, serial, content) VALUES (?,?,?,?);");
            preparedStatement.setInt(1, roomId);
            preparedStatement.setString(2, sm.getUserName());
            preparedStatement.setInt(3, sm.getSerial());
            preparedStatement.setString(4, sm.getContent());
                                    
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void LeaveRoom(String username, int roomId){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM user_in_room WHERE username = ? AND roomId = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, roomId);
                                    
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
