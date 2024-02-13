package ru.mts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.animals")
public class AnimalsProperties {
    private String[] catNames;
    private String[] dogNames;
    private String[] sharkNames;
    private String[] wolfNames;

    public String[] getCatNames() {
        return catNames;
    }

    public void setCatNames(String[] catNames) {
        this.catNames = catNames;
    }

    public String[] getDogNames() {
        return dogNames;
    }

    public void setDogNames(String[] dogNames) {
        this.dogNames = dogNames;
    }

    public String[] getSharkNames() {
        return sharkNames;
    }

    public void setSharkNames(String[] sharkNames) {
        this.sharkNames = sharkNames;
    }

    public String[] getWolfNames() {
        return wolfNames;
    }

    public void setWolfNames(String[] wolfNames) {
        this.wolfNames = wolfNames;
    }
}
