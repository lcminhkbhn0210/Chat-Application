/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author lcmin
 */
public class SocketHandle implements Runnable{
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int ID_Server;
    
    @Override
    public  void run(){
            try {
            socketOfClient = new Socket("192.168.1.251", 11900);
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String message;
            
            while(true){
                message = is.readLine();
                if (message == null) {
                    break;
                }
            }
        } catch (Exception e) {
        }
    
    }
}
