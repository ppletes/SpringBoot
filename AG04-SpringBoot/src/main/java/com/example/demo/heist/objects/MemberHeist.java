package com.example.demo.heist.objects;

import javax.persistence.*;

@Entity
@Table
public class MemberHeist {

    @Id
    @SequenceGenerator(
            name = "memberheist_sequence",
            sequenceName = "memberheist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "memberheist_sequence"
    )
    private Long id;

    private Long heist_id;
    private Long member_id;

    public MemberHeist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeist_id() {
        return heist_id;
    }

    public void setHeist_id(Long heist_id) {
        this.heist_id = heist_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }
}
