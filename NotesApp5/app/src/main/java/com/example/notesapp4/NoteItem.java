package com.example.notesapp4;

public class NoteItem {
    private String id, title, content;
    private boolean showMenu = false;


    public NoteItem() {
    }

    public NoteItem(String title, String content, boolean showMenu) {
        this.title = title;
        this.content = content;
        this.showMenu = showMenu;
        this.id = id;
    }
    public NoteItem(String id, String title, String content, boolean showMenu) {
        this.title = title;
        this.content = content;
        this.showMenu = showMenu;
        this.id = id;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return  id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
}
