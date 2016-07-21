/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;

/**
 *
 * @author Airinei
 */
public class Bookmark implements Serializable {
    private int id;
    private String bookmarkTitle;
    private String bookmargLink;
    private int userId;

    public Bookmark(int id, String bookmarkTitle, String bookmargLink, int userId) {
        this.id = id;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmargLink = bookmargLink;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookmarkTitle() {
        return bookmarkTitle;
    }

    public void setBookmarkTitle(String bookmarkTitle) {
        this.bookmarkTitle = bookmarkTitle;
    }

    public String getBookmargLink() {
        return bookmargLink;
    }

    public void setBookmargLink(String bookmargLink) {
        this.bookmargLink = bookmargLink;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "\nBookmark{" + "id=" + id + ", bookmarkTitle=" + bookmarkTitle + ", bookmargLink=" + bookmargLink + ", userId=" + userId + '}';
    }
    
}
