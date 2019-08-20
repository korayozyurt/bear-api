package com.mkt.uzaktanelemanapi.entity;

import javax.persistence.*;

@Entity
@Table
public class Project extends BearEntity{

    @Column
    private String projectOwner;

    @Column
    private String projectCategory;

    @Column
    private String projectAvatar;

    @Column
    private String imagePath;

    @Column
    private String smallDescription;

    @Lob
    @Column
    private String description;

    public Project(){}

    public Project(String projectOwner, String projectCategory, String projectAvatar, String imagePath, String smallDescription, String description) {
        this.projectOwner = projectOwner;
        this.projectCategory = projectCategory;
        this.projectAvatar = projectAvatar;
        this.imagePath = imagePath;
        this.smallDescription = smallDescription;
        this.description = description;
    }


    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectAvatar() {
        return projectAvatar;
    }

    public void setProjectAvatar(String projectAvatar) {
        this.projectAvatar = projectAvatar;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSmallDescription() {
        return smallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        this.smallDescription = smallDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
