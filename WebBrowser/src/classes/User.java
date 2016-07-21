/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Airinei
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int nrOfBookmarks;
    public ArrayList<Bookmark> bookmarks;

    public User(int id, String username, String password, int nrOfBookmarks, ArrayList<Bookmark> bookmarks) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nrOfBookmarks = nrOfBookmarks;
        this.bookmarks = bookmarks;
    }
    
    public User(int id, String username, String password, int nrOfBookmarks) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nrOfBookmarks = nrOfBookmarks;
        
    }
    
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nrOfBookmarks = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNrOfBookmarks() {
        return nrOfBookmarks;
    }

    public void setNrOfBookmarks(int nrOfBookmarks) {
        this.nrOfBookmarks = nrOfBookmarks;
    }

    public ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(ArrayList<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @Override
    public String toString() {
        
        String bk="";
        for ( Bookmark it:bookmarks){
            bk=bk+it.toString();
        }
        return "User: " + "id=" + id + ", username=" + username + ", password=" + password + ", nrOfBookmarks=" + nrOfBookmarks +bk;
    }
    
    
    
}
