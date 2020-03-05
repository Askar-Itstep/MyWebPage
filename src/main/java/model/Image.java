package model;

import java.io.Serializable;

public class Image extends BaseDBEntity implements Serializable {

    private int persId;
    private String imageName;

    public Image(int persId, String imageName) {
        this.persId = persId;
        this.imageName = imageName;
    }

    public Image() {
    }


    public int getPersId() {
        return persId;
    }

    public void setPersId(int persId) {
        this.persId = persId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


}
