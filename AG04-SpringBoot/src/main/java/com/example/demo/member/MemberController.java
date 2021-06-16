package com.example.demo.member;

import com.example.demo.member.objects.Member;
import com.example.demo.member.viewHelp.GetMemberSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//za pregled json-a preko generated-requests.http umjesto @Controller napisati @RestController
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String getMembers(Model model) {
        model.addAttribute("memberList", memberService.getMembers());
        return "member";
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String registerNewMember(@RequestBody Member member) {
        memberService.addNewMember(member);
        return "member";
    }

    @PutMapping(path = "/{member_id}/skills",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String updateMemberSkill(Model model, @PathVariable Long member_id, @RequestBody Member member) {
        model.addAttribute("member", member);
        memberService.updateMemberSkills(member, member_id);
        return "member/skills";
    }

    @DeleteMapping(path = "/{member_id}/skills/{skill_name}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String deleteMemberSkill(@PathVariable Long member_id, @PathVariable String skill_name) {
        memberService.deleteMemberSkills(member_id, skill_name);
        return "member/skills";
    }

    @GetMapping(path = "/{member_id}")
    public Member getMemberById(@PathVariable Long member_id) {
        return memberService.getMemberById(member_id);
    }

    @GetMapping(path = "/{member_id}/skills")
    public String getMemberSkillsById(Model model, @PathVariable Long member_id) {
        GetMemberSkills memberSkillsById = memberService.getMemberSkillsById(member_id);
        model.addAttribute("memberGetSkills", memberSkillsById.getSkills());
        model.addAttribute("memberGetMainSkill", memberSkillsById.getMainSkill());
        return "member/skills";
    }
}
