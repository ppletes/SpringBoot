package com.example.demo.member.objects;

import javax.persistence.Embeddable;

@Embeddable
public class SkillMember {

    private String name;
    private String level;

    public SkillMember() {
    }

    public SkillMember(String name, String level) {
        this.name = name;
        this.level = level;
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
}
