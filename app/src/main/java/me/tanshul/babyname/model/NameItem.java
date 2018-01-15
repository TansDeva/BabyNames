package me.tanshul.babyname.model;

/**
 * Created by tansdeva on 15/1/18.
 */

public class NameItem {
    private String name;
    private String meaning;
    private GENDER type;

    public NameItem(String name, String meaning, GENDER type) {
        this.name = name;
        this.meaning = meaning;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getMeaning() {
        return meaning;
    }

    public GENDER getType() {
        return type;
    }

    public enum GENDER {
        BOY(1),
        GIRL(2);
        private int gender;

        GENDER(int gender) {
            this.gender = gender;
        }

        public int getGender() {
            return gender;
        }
    }

    public boolean isType(int type) {
        return type == 0 || type == getType().getGender();
    }
}
