package model;

import java.io.Serializable;

public class MyStory extends BaseDBEntity implements Serializable {
    private String text;
    private int pers_id;

    public MyStory(){}

    public MyStory(String text, int pers_id) {
        this.text = text;
        this.pers_id = pers_id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPers_id() {
        return pers_id;
    }

    public void setPers_id(int pers_id) {
        this.pers_id = pers_id;
    }
}
