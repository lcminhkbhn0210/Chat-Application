/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainRun;

import Controller.Client;
import Controller.Controller;
import View.LoginFrm;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import model.User;

/**
 *
 * @author lcmin
 */
import java.sql.*;

public class MainRun {
    public static User user = new User();
    public static Controller controller = new Controller();
  public static void main(String[] args) {
    LoginFrm loginFrm = new LoginFrm();
    loginFrm.setLocationRelativeTo(null);
    loginFrm.setVisible(true);
  }
}







