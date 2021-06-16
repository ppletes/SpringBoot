package com.example.demo.heist.objects;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Heist {
    @Id
    @SequenceGenerator(
            name="heist_sequence",
            sequenceName = "heist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "heist_sequence"
    )
    private Long id;

    private String name;
    private String location;
    private String startTime;
    private String endTime;

    @ElementCollection
    @CollectionTable(
            name="SkillHeist",
            joinColumns=@JoinColumn(name="heist_id")
    )
    protected List<SkillHeist> skillHeists;

    private String status;

    public Heist() {
    }

    public Heist(Long id, String name, String location, String startTime, String endTime, List<SkillHeist> skillHeists, String status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skillHeists = skillHeists;
        this.status = status;
    }

    public Heist(String name, String location, String startTime, String endTime, List<SkillHeist> skillHeists, String status) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skillHeists = skillHeists;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<SkillHeist> getSkills() {
        return skillHeists;
    }

    public void setSkills(List<SkillHeist> skillHeists) {
        this.skillHeists = skillHeists;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
