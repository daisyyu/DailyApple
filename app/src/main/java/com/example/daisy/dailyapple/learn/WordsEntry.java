package com.example.daisy.dailyapple.learn;

/**
 * Created by Daisy on 10/5/15.
 */
public class WordsEntry {
    public WordsEntry() {
    }

    private String iconHint;
    private String personalHint;
    private boolean isLearned = false;

    public boolean isLearned() {
        return isLearned;
    }

    public void setIsLearned(boolean isLearned) {
        this.isLearned = isLearned;
    }

    public void setIconHint(String iconHint) {
        this.iconHint = iconHint;
    }


    public WordsEntry(String iconHint, String personalHint, boolean isLearned) {
        this.iconHint = iconHint;
        this.personalHint = personalHint;
        this.isLearned = isLearned;
    }

    public String getPersonalHint() {
        return personalHint;
    }

    public void setPersonalHint(String personalHint) {
        this.personalHint = personalHint;
    }

    public String getIconHint() {

        return iconHint;
    }

    @Override
    public String toString() {
        return "iconHint: " + iconHint + " personalhint: " + personalHint + " " +
                "isLearned: " + isLearned;
    }

    public void seticonHint(String iconHint) {
        this.iconHint = iconHint;
    }
}
