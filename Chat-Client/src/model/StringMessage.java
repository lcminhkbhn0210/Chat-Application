/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author vvt
 */
public class StringMessage implements Serializable, Comparable<StringMessage>{
    static final long serialVersionUID = 1L;
    
    private int id;
    private String userName;
    private int serial;
    private String content;

    public StringMessage() {
    }
    
    public StringMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getSerial() {
        return serial;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "StringMessage{" + "id=" + id + ", userName=" + userName + ", serial=" + serial + ", content=" + content + '}';
    }

    @Override
    public int compareTo(StringMessage o) {
        if(o.serial > this.serial)
            return  -1;
        else if(o.serial < this.serial)
            return 1;
        return 0;
    }
}
