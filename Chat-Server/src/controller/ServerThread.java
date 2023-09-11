/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RoomDao;
import dao.UserDao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FileMessage;
import model.Room;
import model.StringMessage;
import model.User;

/**
 *
 * @author vvt
 */
public class ServerThread implements Runnable{
    private User user = null;
    private Socket socketOfServer;
    private int clientNumber;
    
    private BufferedReader is;
    private BufferedWriter os;
    
    private boolean isClosed;
    //private Room room;
    private UserDao userDao;
    private RoomDao roomDao;
    private String clientIP;
    
    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userDao = new UserDao();
        roomDao = new RoomDao();
        isClosed = false;
        //room = null;
        //Trường hợp test máy ở server sẽ lỗi do hostaddress là localhost
        if(this.socketOfServer.getInetAddress().getHostAddress().equals("127.0.0.1")){
            clientIP = "127.0.0.1";
        }
        else{
            clientIP = this.socketOfServer.getInetAddress().getHostAddress();
        }
    }

    public User getUser() {
        return user;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BufferedWriter getOs() {
        return os;
    }
    
    @Override
    public void run() {
        try {
            // Mở luồng vào command trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            
            while(!isClosed){
                String messageRev = null;
                try {
                    messageRev = is.readLine();
                } catch (Exception e) {
                }
                
                if(messageRev == null){
                    break;
                }
                
                String[] mes = messageRev.split(",");
                
                System.out.println("Receive: " + messageRev);
                
                switch (mes[0]) {
                    case "Login":
                        CheckLogin(mes);
                        break;
                    case "Register":
                        CheckRegister(mes);
                        break;
                    case "Logout":
                        CheckLogout();
                        break;
                    case "GetRoom":
                        GetRooms();
                        break;
                    case "GetFriend":
                        GetFriend();
                        break;
                    case "CreateRoom":
                        CreateRoom(mes);
                        break;
                    case "SearchUser":
                        SearchUser(mes);
                        break;
                    case "AddFriend":
                        AddFriend(mes);
                        break;
                    case "JoinRoom":
                        JoinRoom(mes);
                        break;
                    case "Chat":
                        Chat(mes);
                        break;
                    case "LeaveRoom":
                        LeaveRoom(mes);
                        break;
                    case "AddMember":
                        AddMember(mes);
                        break;
                    case "DeleteFriend":
                        DeleteFriend(mes);
                        break;
                    default:
                        
                        break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            isClosed = true;
            
            if(this.user!=null){
                System.out.println(user.getUsername() + " go Offline");
                userDao.SetOffline(user);
                
                /*
                for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser()!=null) {
                        if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0 || Server.serverThreadBus.getListServerThreads().get(i).getUser().getRoomsIn().size() != 0){
                            try {
                                Server.serverThreadBus.getListServerThreads().get(i).write("Update-OnOff," + user.getUsername() + ",false");
                            } catch (IOException ex) {
                                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SendStr("Update-OnOff," + user.getUsername() +",false");
                        }
                    }
                }
                */
                
                for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser()!=null)
                        if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0)
                            if(Server.serverThreadBus.getListServerThreads().get(i).getUser().isFriend(user.getUsername()))
                                try {
                                    Server.serverThreadBus.getListServerThreads().get(i).write("Update-OnOff," + user.getUsername() + ",false");
                            } catch (IOException ex) {
                                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                }
                
                /*
                for (int i = 0; i < user.getFriends().size(); i++) {
                    if(Server.serverThreadBus.getServerThreadByUserName(user.getFriends().get(i).getUsername()) != null){
                        try {
                            Server.serverThreadBus.getServerThreadByUserName(user.getFriends().get(i).getUsername()).write("Update-OnOff," + user.getUsername() +",false");
                        } catch (IOException ex) {
                            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }*/
            }
            
            //remove thread khỏi bus
            Server.serverThreadBus.remove(clientNumber);
        }
    }
    
    public void CheckLogin(String[] message) throws IOException, ClassNotFoundException{
        try {
            user = new User();
            user.setUsername(message[1]);
            user.setPassword(message[2]);
            
            //call UserDAO check
            String result = userDao.CheckLogin(user);
            
            System.out.println(result + " login for: " + user.getUsername());
            
            write(result);
            
            if(result.equals("Success-Login")){
                for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser()!=null)
                        if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0)
                            if(Server.serverThreadBus.getListServerThreads().get(i).getUser().isFriend(user.getUsername()))
                                Server.serverThreadBus.getListServerThreads().get(i).write("Update-OnOff," + user.getUsername() + ",true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CheckRegister(String[] message){
        try {
            User tmpUser = new User(message[1], message[2]);
            
            //call UserDAO check
            String result = userDao.Register(tmpUser);
            System.out.println(result + " register for: " + tmpUser.getUsername());
            
            write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CheckLogout(){
        try {
            //call UserDAO check
            userDao.SetOffline(user);
            
            /*
            for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
                if(Server.serverThreadBus.getListServerThreads().get(i).getUser()!=null){
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0 || Server.serverThreadBus.getListServerThreads().get(i).getUser().getRoomsIn().size() != 0){
                        Server.serverThreadBus.getListServerThreads().get(i).write("Update-OnOff," + user.getUsername() + ",false");
                        SendStr("Update-OnOff," + user.getUsername() +",false");
                    }
                }
            }*/
            
            for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
                if(Server.serverThreadBus.getListServerThreads().get(i).getUser() != null)
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0)
                        if(Server.serverThreadBus.getListServerThreads().get(i).getUser().isFriend(user.getUsername()))
                            Server.serverThreadBus.getListServerThreads().get(i).write("Update-OnOff," + user.getUsername() + ",false");
            }
            user = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetRooms() throws IOException{
        userDao.GetRoom(user);
        String rMes = "GetRoom," + user.getRoomsIn().size();
        for (Room r : user.getRoomsIn()) {
            rMes += "," + r.getId() + "," + r.getName() + "," + r.getType();
        }
        write(rMes);
    }
    public void GetFriend() throws IOException{
        userDao.GetFiend(user);
        String rMes = "GetFriend," + user.getFriends().size();
        for (User u : user.getFriends()) {
            rMes += "," + u.getUsername()+ "," + u.getIsOnline();
        }
        write(rMes);
    }
    public void CreateRoom(String[] message) throws IOException{
        if(message.length < 2)
            return;
        String roomName = message[1];
        Room r = roomDao.CreateRooms(roomName, user);
        
        String rMes = "CreateRoom," + r.getId() + "," + r.getName() + "," + r.getType();
        write(rMes);
    }
    public void SearchUser(String[] message) throws IOException{
        ArrayList<User> listU = userDao.SearchUser(message[1]);
        String rMes = "SearchUser," + listU.size();
        for(User u : listU){
            rMes += "," + u.getUsername() + "," + u.getIsOnline();
        }
        write(rMes);
    }
    public void AddFriend(String[] message) throws IOException{
        String result = userDao.AddFriend(message[1], Boolean.parseBoolean(message[2]), user);
        String rMes = "AddFriend," + result;
        write(rMes);
        if(result.equals("Success-AddFriend")){
            ServerThread st = Server.serverThreadBus.getServerThreadByUserName(message[1]);
            if(st != null)
                st.write("Update-AFriend," + user.getUsername() + ",true");
        }
    }
    public void JoinRoom(String[] message) throws IOException{
        for (int i = 0; i < user.getRoomsIn().size(); i++) {
            if(user.getRoomsIn().get(i).getId() == Integer.parseInt(message[1])){
                roomDao.GetAllRoomInfo(user.getRoomsIn().get(i));
                String rMes = "JoinRoom," + user.getRoomsIn().get(i).getId() + "," + user.getRoomsIn().get(i).getUsersInRoom().size();
                for(User u : user.getRoomsIn().get(i).getUsersInRoom()){
                    rMes += "," + u.getUsername();
                }
                rMes+="," + user.getRoomsIn().get(i).getStringMessages().size();
                for(StringMessage strMes : user.getRoomsIn().get(i).getStringMessages()){
                    rMes+= "," + strMes.getUserName() + "," + strMes.getSerial() + "," + strMes.getContent();
                }
                /*
                rMes+="," + user.getRoomsIn().get(i).getFileMessages().size();
                for(FileMessage fileMes : user.getRoomsIn().get(i).getFileMessages()){
                    rMes+= "," + fileMes.getUserName() + "," + fileMes.getSerial() + "," + fileMes.getContent().toString();
                }
                */
                write(rMes);
            }
        }
    }
    public void AddMember(String[] message) throws IOException{
        int roomID = Integer.parseInt(message[1]);
        String username = message[2];
        String result = roomDao.AddMember(username, roomID);
        
        System.out.println(result);
        
        for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
            if(Server.serverThreadBus.getListServerThreads().get(i).getUser() != null){
                if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getUsername().equals(username)){
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getRoomsIn().size() != 0){
                        Server.serverThreadBus.getListServerThreads().get(i).write("Update-AddToRoom," + roomID + "," + user.SearchRoomById(roomID).getName() + "," + user.SearchRoomById(roomID).getType());
                        break;
                    }
                }
            }
        }
    }
    public void LeaveRoom(String[] message){
        int roomID = Integer.parseInt(message[1]);
        for (int i = 0; i < user.getRoomsIn().size(); i++) {
            if(user.getRoomsIn().get(i).getId() == roomID)
            {
                user.getRoomsIn().remove(i);
                break;
            }
        }
        roomDao.LeaveRoom(user.getUsername(), roomID);
    }
    public void Chat(String[] message) throws IOException{
        int roomID = Integer.parseInt(message[1]);
        StringMessage sm = new StringMessage();
        sm.setUserName(user.getUsername());
        sm.setSerial(Integer.parseInt(message[2]));
        sm.setContent(message[3]);
        roomDao.UpdateStringChat(sm, roomID);
        
        for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
            if(Server.serverThreadBus.getListServerThreads().get(i).getUser() != null){
                if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getRoomsIn().size() != 0){
                    for(Room r : Server.serverThreadBus.getListServerThreads().get(i).getUser().getRoomsIn()){
                        if(r.getId() == roomID){
                            Server.serverThreadBus.getListServerThreads().get(i).write("Update-RoomChat," + roomID + "," + sm.getSerial() + "," + sm.getUserName() + "," + sm.getContent());
                        }
                    }
                }
            }
        }
    }
    public void DeleteFriend(String[] message) throws IOException{
        String friendName = message[1];
        
        for (int i = 0; i < Server.serverThreadBus.getListServerThreads().size(); i++) {
            if(Server.serverThreadBus.getListServerThreads().get(i).getUser() != null){
                if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getUsername().equals(friendName)){
                    if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getFriends().size() != 0){
                        Server.serverThreadBus.getListServerThreads().get(i).write("Update-DeleteFriend," + user.getUsername());
                    }
                }
            }
        }
        
        for (int i = 0; i < user.getFriends().size(); i++) {
            if(user.getFriends().get(i).getUsername().equals(friendName))
            {
                user.getFriends().remove(i);
                break;
            }
        }
        userDao.DeleteFriend(user.getUsername(), friendName);
    }
    
    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}
