package com.example.demo.heist.viewHelp;

import com.example.demo.heist.objects.SkillHeist;
import com.example.demo.member.viewHelp.GetHeistMembers;

import java.util.List;

public interface GetHeistEligibleMembers {

    List<SkillHeist> getSkills();

    List<GetHeistMembers> getMembers();
}
