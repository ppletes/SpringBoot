package com.example.demo.member.objects;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Member {
    @Id
    @SequenceGenerator(
            name="member_sequence",
            sequenceName = "member_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_sequence"
    )
    private Long id;
    private String name;
    private String sex;

    @ElementCollection
    @CollectionTable(
            name="SkillMember",
            joinColumns=@JoinColumn(name="member_id")
    )
    private List<SkillMember> skills;

    private String email;
    private String mainSkill;
    private String status;

    public Member() {
    }

    public Member(Long id, String name, String sex, String email, List<SkillMember> skills, String mainSkill, String status) {
        this.id = id;
        this.name = name;
        this.sex=sex;
        this.email = email;
        this.skills = skills;
        this.mainSkill = mainSkill;
        this.status = status;
    }

    public Member(String name, String sex, String email, List<SkillMember> skills, String mainSkill, String status) {
        this.name = name;
        this.sex=sex;
        this.email = email;
        this.skills = skills;
        this.mainSkill = mainSkill;
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<SkillMember> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillMember> skillMembers) {
        this.skills = skillMembers;
    }

    public void setSkillsObject(SkillMember skillMember) {
        this.skills.add(skillMember);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", skills=" + skills +
                ", email='" + email + '\'' +
                ", mainSkill='" + mainSkill + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
