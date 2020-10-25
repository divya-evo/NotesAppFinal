package com.example.notesapp4;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class RecyclerEntity implements Serializable {
    private String title;
    private String content;
    private boolean showMenu = false;
    //private int image;

    public RecyclerEntity() {
    }

    public RecyclerEntity(String title, String content, boolean showMenu) {
        this.title = title;
        this.content = content;
        this.showMenu = showMenu;
        //this.image = image;
    }

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String title) {
        this.content = content;
    }


}
