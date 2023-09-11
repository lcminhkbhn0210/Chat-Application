/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import MainRun.MainRun;
import View.ChatRoomFrm;
import View.FriendListFrm;
import View.RoomFrm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import model.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;
import java.util.Collections;
import model.StringMessage;

/**
 *
 * @author lcmin
 */
public class Controller implements Runnable{
    private String severHost="26.174.124.172";
    private int severport = 11900;
    String result="";
      
    
    Socket mySocket;
    BufferedReader bs;
    BufferedWriter bw;
    public Controller(){
     }
    @Override
    public void run(){
     try {
                    
        Socket mySocket = new Socket(severHost, severport);
        bs = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream()));
            
             String message="";
             while (true) { 
                 try {
                     message = bs.readLine();
                 } catch (Exception e) {
                 }
                 System.out.println(message);
             String[] messageSplit = message.split(",");
             if(messageSplit[0].equals("Success-Login")){
                 Client.loginFrm.dispose();
                 UpdateRoom();
             }
             else{
                 if(messageSplit[0].equals("Logined")) Client.loginFrm.notice(messageSplit[0]);
             }
            if(messageSplit[0].equals("Success-Register")){
                 Client.registerFrm.dispose();
                 Client.loginFrm.setLocationRelativeTo(null);
                 Client.loginFrm.setVisible(true);
                 
             }
             if(messageSplit[0].equals("GetRoom")){
                 int sizeroom = Integer.parseInt(messageSplit[1]);
                 MainRun.user.getRoomsIn().clear();
                 int j=2;
                 for(int i=0;i<sizeroom;i++){
                     Room room = new Room();
                     room.setId(Integer.parseInt(messageSplit[j]));
                     room.setName(messageSplit[j+1]);
                     room.setType(messageSplit[j+2]);
                     MainRun.user.getRoomsIn().add(room);
                     j = j + 3;
                 }
                 Client.roomFrm = new RoomFrm();
                 Client.roomFrm.setLocationRelativeTo(null);
                 Client.roomFrm.setVisible(true);
             }
             
             if(messageSplit[0].equals("CreateRoom")){
                Room room = new Room();
                room.setId(Integer.parseInt(messageSplit[1]));
                room.setName(messageSplit[2]);
                room.setType(messageSplit[3]);
                MainRun.user.getRoomsIn().add(room);
                Client.roomFrm.setTable();
             }
             
             if(messageSplit[0].equals("GetFriend")){
                 int sizefriend = Integer.parseInt(messageSplit[1]);
                 int j = 2;
                 MainRun.user.getFriends().clear();
                 for(int i=0;i<sizefriend;i++){
                     User user = new User();
                     user.setUsername(messageSplit[j]);
                     user.setIsOnline(Boolean.parseBoolean(messageSplit[j+1]));
                     j = j+2;
                     MainRun.user.getFriends().add(user);
                }
                 Client.roomFrm.setVisible(false);
                 Client.friendListFrm = new FriendListFrm(Client.roomFrm);
                 Client.friendListFrm.setLocationRelativeTo(null);
                 Client.friendListFrm.setVisible(true);
             }
             
             if(messageSplit[0].equals("Update-OnOff")){
                for(User user:MainRun.user.getFriends()){
                    if(user.getUsername().equals(messageSplit[1])){
                       user.setIsOnline(Boolean.parseBoolean(messageSplit[2]));
                    }
                }
                Client.friendListFrm.updatefriendlist();
             }
             
             if(messageSplit[0].equals("SearchUser")){
                 int size = Integer.parseInt(messageSplit[1]);
                 ArrayList<User> users = new ArrayList<>();
                 int j= 2;
                 for(int i=0;i<size;i++){
                     User user = new User();
                     user.setUsername(messageSplit[j]);
                     user.setIsOnline(Boolean.parseBoolean(messageSplit[j+1]));
                     j = j + 2;
                     users.add(user);
                 }
                  Client.friendListFrm.setTableFriend(users);
             }
            
             if(messageSplit[0].equals("Update-AFriend")){
                User user = new User();
                user.setUsername(messageSplit[1]);
                user.setIsOnline(Boolean.parseBoolean(messageSplit[2]));
                MainRun.user.getFriends().add(user);
                if(Client.friendListFrm.isActive()) Client.friendListFrm.setTable(MainRun.user.getFriends());
             }
             
             if(messageSplit[0].equals("JoinRoom")){
                int id = Integer.parseInt(messageSplit[1]);
                int sizeuser = Integer.parseInt(messageSplit[2]);
                int j = 3;
                ArrayList<User> users = new ArrayList<>();
                for(int i=0;i<sizeuser;i++){
                    User user = new User();
                    user.setUsername(messageSplit[j]);
                    j = j + 1;
                    users.add(user);
                }
                for(int t = 0;t<MainRun.user.getRoomsIn().size();t++){
                    if(MainRun.user.getRoomsIn().get(t).getId()==id) MainRun.user.getRoomsIn().get(t).setUsersInRoom(users);
                }
                
                int sizemess = Integer.parseInt(messageSplit[j]);
                j = j+1;
                 ArrayList<StringMessage> listmess = new ArrayList<>();
                 for(int i=0;i<sizemess;i++){
                    StringMessage s = new StringMessage();
                    s.setUserName(messageSplit[j]);
                    s.setSerial(Integer.parseInt(messageSplit[j+1]));
                    s.setContent(messageSplit[j+2]);
                    listmess.add(s);
                    j = j+3;
                 }
                 Collections.sort(listmess);
                 for(int i=0;i<MainRun.user.getRoomsIn().size();i++){
                     if(MainRun.user.getRoomsIn().get(i).getId()==id){
                         MainRun.user.getRoomsIn().get(i).setStringMessages(listmess);
                         
                     }
                 }
                 Client.roomFrm.dispose();
                 Client.chatFrm = new ChatRoomFrm(id);
                 Client.chatFrm.setLocationRelativeTo(null);
                 Client.chatFrm.setVisible(true);
             }
             
              if(messageSplit[0].equals("Update-RoomChat")){
                int idroom = Integer.parseInt(messageSplit[1]);
                StringMessage stringMessage = new StringMessage();
                stringMessage.setSerial(Integer.parseInt(messageSplit[2]));
                stringMessage.setUserName(messageSplit[3]);
                stringMessage.setContent(messageSplit[4]);
                for(int j=0;j<MainRun.user.getRoomsIn().size();j++){
                if(MainRun.user.getRoomsIn().get(j).getId()==idroom){
                    MainRun.user.getRoomsIn().get(j).getStringMessages().add(stringMessage);
                    Collections.sort(MainRun.user.getRoomsIn().get(j).getStringMessages());
                    break;
                }}
                if(Client.chatFrm!=null){
                    Client.chatFrm.addmess(stringMessage);
                }
                else{
                    if(Client.roomFrm!=null){
                        Client.roomFrm.Notice("Ban co tin nhan tai phong: "+"\n"+MainRun.user.SearchRoomById(idroom).getName());
                    }
                }
             }
             if(messageSplit[0].equals("Update-AddToRoom")){
                 int id = Integer.parseInt(messageSplit[1]);
                 Room room = new Room();
                 room.setId(id);
                 room.setName(messageSplit[2]);
                 room.setType(messageSplit[3]);
                 boolean kt=false;
                 for(int i=0;i<MainRun.user.getRoomsIn().size();i++){
                     if(MainRun.user.getRoomsIn().get(i).getId()==id){
                         kt = true;
                         break;
                     }
                 }
                 if(kt==false) MainRun.user.getRoomsIn().add(room);
             }
             
             if(messageSplit[0].equals("Update-DeleteFriend")){
                 for(int i=0;i<MainRun.user.getFriends().size();i++){
                     if(MainRun.user.getFriends().get(i).getUsername().equals(messageSplit[1])){
                         MainRun.user.getFriends().remove(i);
                         break;
                     }
                 }
                 
                 
             }
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
   
    public void UpdateOnlineFr(String username, User user){
        ArrayList<User> users = user.getFriends();
        for(User sUser:users){
            if(sUser.getUsername().equals(username)) {sUser.setIsOnline(true); break;}
        }
    }
    
    public void UpdateRoom(){
        try {
       
            write("GetRoom");
        } catch (Exception e) {
        }
    }
    public void UpdateFriend() throws IOException{
        write("GetFriend");
    }
    public void createroom(String roomname) throws IOException, ClassNotFoundException{
        write("CreateRoom,"+roomname);
    }
    public String checkLogin(User user) throws IOException, ClassNotFoundException{
        try {
         
        
        write("Login"+","+user.getUsername()+","+user.getPassword()); 
        MainRun.controller.write("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
         return result;
}
    public void Register(User user){
        try {
        
            write("Register"+","+user.getUsername()+","+user.getPassword());
        } catch (Exception e) {
        }
    }
     public void write(String message) throws IOException{
        bw.write(message);
        bw.newLine();
        bw.flush();
    }
 
}
