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
public class Room implements Serializable{
    static final long serialVersionUID = 1L;
    
    private int id;
    private String type = "1-1";
    private String name;
    private ArrayList<User> usersInRoom = new ArrayList<>();
    private ArrayList<StringMessage> StringMessages = new ArrayList<>();
    private ArrayList<FileMessage> fileMessages = new ArrayList<>();

    public Room() {
    }

    public Room(int id) {
        this.id = id;
    }

    public Room(String type, String name, ArrayList<User> usersInRoom) {
        this.type = type;
        this.name = name;
        this.usersInRoom = usersInRoom;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsersInRoom() {
        return usersInRoom;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<StringMessage> getStringMessages() {
        return StringMessages;
    }

    public ArrayList<FileMessage> getFileMessages() {
        return fileMessages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsersInRoom(ArrayList<User> usersInRoom) {
        this.usersInRoom = usersInRoom;
    }

    public void setStringMessages(ArrayList<StringMessage> StringMessages) {
        this.StringMessages = StringMessages;
    }

    public void setFileMessages(ArrayList<FileMessage> fileMessages) {
        this.fileMessages = fileMessages;
    }
    
    @Override
    public String toString() {
        return "Name: "+ name +" type: "+ type;
    }
}
