package com.example.demo.member;

import com.example.demo.member.objects.Member;
import com.example.demo.member.objects.SkillMember;
import com.example.demo.member.viewHelp.GetMemberSkills;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public void addNewMember(Member member) {
        List<SkillMember> skillsPost = member.getSkills();

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(member.getEmail());
        if (memberOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email se koristi");
        }

        Set<String> store = new HashSet<>();
        for (SkillMember s : skillsPost) {
            if (!store.add(s.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesen skill istog imena");
            }
        }

        memberRepository.save(member);
    }

    @Transactional
    public void updateMemberSkills(Member member1, Long member_id) {
        Set<String> store = new HashSet<>();
        Member member2 = memberRepository.findById(member_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member navedenog id-a ne postoji"));
        List<SkillMember> skillsUpdate = member1.getSkills();
        List<SkillMember> skillsGet = member2.getSkills();
        List<String> skillsString = new ArrayList<>();

        for (SkillMember s : skillsUpdate) {
            if (!store.add(s.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesen skill istog imena");
            }
        }

        for (SkillMember update : skillsUpdate) {
            skillsGet.removeIf(skillMember -> update.getName().equals(skillMember.getName()));
            skillsGet.add(new SkillMember(update.getName(), update.getLevel()));
        }

        for (SkillMember get : skillsGet) {
            skillsString.add(get.getName());
        }
        if (!skillsString.contains(member1.getMainSkill())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Main skill nije sadržan u listi skilleva");
        }

        member2.setMainSkill(member1.getMainSkill());
        member2.setSkills(skillsGet);
    }

    @Transactional
    public void deleteMemberSkills(Long member_id, String name) {
        Member member = memberRepository.findById(member_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member navedenog id-a ne postoji"));
        List<SkillMember> skillMembers = member.getSkills();
        boolean present = skillMembers.stream().anyMatch(o -> o.getName().equals(name));
        if (!present) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill membera ne postoji");
        }
        for (SkillMember s : skillMembers) {
            if (s.getName().equals(name)) {
                skillMembers.remove(s);
            }
        }
        member.setSkills(skillMembers);
    }

    public Member getMemberById(Long member_id) {
        return memberRepository.findById(member_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member navedenog id-a ne postoji"));
    }

    public GetMemberSkills getMemberSkillsById(Long member_id) {
        GetMemberSkills memberById = memberRepository.findMemberById(member_id);
        if (memberById == null || memberById.getSkills().isEmpty() || memberById.getMainSkill().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member navedenog id-a ne postoji");
        }
        return memberById;
    }
}
