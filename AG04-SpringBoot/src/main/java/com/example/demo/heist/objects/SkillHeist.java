package com.example.demo.heist.objects;

import javax.persistence.Embeddable;

@Embeddable
public class SkillHeist {

    private String name;
    private String level;
    private String members;

    public SkillHeist() {
    }

    public SkillHeist(String name, String level, String members) {
        this.name = name;
        this.level = level;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
