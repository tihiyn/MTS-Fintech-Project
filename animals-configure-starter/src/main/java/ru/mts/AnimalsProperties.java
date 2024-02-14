package ru.mts;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "animal")
@Validated
public class AnimalsProperties {
    @NotEmpty
    private String[] catNames;
    @NotEmpty
    private String[] dogNames;
    @NotEmpty
    private String[] sharkNames;
    @NotEmpty
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
