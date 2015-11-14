package com.example.daisy.dailyapple.translation.loaders;

/**
 * Created by Daisy on 10/5/15.
 */
public class CatEntry {
    public CatEntry() {
    }

    private String icon;
    private String title;

    public CatEntry(String icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
