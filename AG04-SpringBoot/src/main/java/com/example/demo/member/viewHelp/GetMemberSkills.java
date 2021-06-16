package com.example.demo.member.viewHelp;

import com.example.demo.member.objects.SkillMember;

import java.util.List;

public interface GetMemberSkills {

    List<SkillMember> getSkills();

    String getMainSkill();
}
