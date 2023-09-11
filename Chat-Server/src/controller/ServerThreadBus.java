/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vvt
 */
public class ServerThreadBus {
    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }
    
    
    
    public int getLength(){
        return listServerThreads.size();
    }
    
    public ServerThread getServerThreadByUserName(String username){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getUser().getUsername().equals(username)){
                return Server.serverThreadBus.listServerThreads.get(i);
            }
        }
        return null;
    }
    
    public void remove(int id){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getClientNumber()==id){
                Server.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }
    /*
    public void boardCast(int id, String message){
        for(ServerThread serverThread : Server.serverThreadBus.getListServerThreads()){
            if (serverThread.getClientNumber() == id) {
                continue;
            } else {
                if(serverThread.getUser()!=null){
                    try {
                        serverThread.write(message);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }*/
}
