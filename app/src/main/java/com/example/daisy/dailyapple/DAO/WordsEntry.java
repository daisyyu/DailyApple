package com.example.daisy.dailyapple.DAO;

/**
 * Created by Daisy on 10/5/15.
 */
public class WordsEntry {
    public WordsEntry() {
    }

    private String iconHint;
    private String word;
    private String personalHint;
    private boolean isLearned = false;
    private String phoneticMP3Address;
    private String translation;

    public WordsEntry(String iconHint, String personalHint, boolean
            isLearned, String word, String phoneticMP3Address, String translation) {
        this.iconHint = iconHint;
        this.personalHint = personalHint;
        this.isLearned = isLearned;
        this.word = word;
        this.phoneticMP3Address = phoneticMP3Address;
        this.translation = translation;
    }


    public String getPhoneticMP3Address() {
        return phoneticMP3Address;
    }

    public void setPhoneticMP3Address(String phoneticMP3Address) {
        this.phoneticMP3Address = phoneticMP3Address;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public boolean isLearned() {
        return isLearned;
    }

    public void setIsLearned(boolean isLearned) {
        this.isLearned = isLearned;
    }


    public void setIconHint(String iconHint) {
        this.iconHint = iconHint;
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
                "isLearned: " + isLearned + " phonetic: " + phoneticMP3Address + " translation: " + translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
