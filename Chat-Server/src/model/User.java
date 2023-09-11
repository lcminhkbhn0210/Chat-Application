/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author vvt
 */
public class User implements Serializable{
    static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private boolean isOnline;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Room> roomsIn = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, boolean isOnline) {
        this.username = username;
        this.isOnline = isOnline;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<Room> getRoomsIn() {
        return roomsIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setRoomsIn(ArrayList<Room> roomsIn) {
        this.roomsIn = roomsIn;
    }
    
    public boolean isFriend(String name){
        for (int i = 0; i < friends.size(); i++) {
            if(friends.get(i).getUsername().equals((name)))
                return  true;
        }
        return false;
    }
    
    public Room SearchRoomById(int roomId){
        for (int i = 0; i < roomsIn.size(); i++) {
            if(roomsIn.get(i).getId() == roomId)
                return roomsIn.get(i);
        }
        return null;
    }

    @Override
    public String toString() {
        return "name: "+ username +" pass: "+ password +" Online: "+ isOnline;
    }
}
