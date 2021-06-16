package com.example.demo.heist;

import com.example.demo.heist.objects.Heist;
import com.example.demo.heist.objects.Members;
import com.example.demo.heist.objects.SkillHeist;
import com.example.demo.heist.viewHelp.GetHeistEligibleMembers;
import com.example.demo.heist.viewHelp.GetHeistStatus;
import com.example.demo.member.viewHelp.GetHeistMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//za pregled json-a preko generated-requests.http umjesto @Controller napisati @RestController
@Controller
@RequestMapping(path = "/heist")
public class HeistController {

    private final HeistService heistService;

    @Autowired
    public HeistController(HeistService heistService) {
        this.heistService = heistService;
    }

    @GetMapping
    public String getHeists(Model model) {
        model.addAttribute("heist", heistService.getHeists());
        return "heist";
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String registerNewHeist(@RequestBody Heist heist) {
        heistService.addNewHeist(heist);
        return "heist";
    }

    @PatchMapping(path = "/{heist_id}/skills",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String updateHeistSkill(@PathVariable Long heist_id, @RequestBody Heist heist) {
        heistService.updateHeistSkills(heist, heist_id);
        return "heist/skills";
    }

    @PutMapping(path = "/{heist_id}/members",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String confirmHeistMember(Model model, @PathVariable Long heist_id, @RequestBody Members members) {
        model.addAttribute("heist_id", heist_id);
        heistService.confirmHeistMembers(members, heist_id);
        return "heist/members";
    }

    @PutMapping(path = "/{heist_id}/start",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String startHeist(@PathVariable Long heist_id) {
        heistService.startHeist(heist_id);
        return "heist";
    }

    @GetMapping(path = "/{heist_id}")
    public Heist getHeistById(@PathVariable Long heist_id) {
        return heistService.getHeistById(heist_id);
    }

    @GetMapping(path = "/{heist_id}/skills")
    public String getHeistSkillsById(Model model, @PathVariable Long heist_id) {
        List<SkillHeist> heistSkillsById = heistService.getHeistSkillsById(heist_id);
        model.addAttribute("heistSkillsById", heistSkillsById);
        return "heist/skills";
    }

    @GetMapping(path = "/{heist_id}/status")
    public GetHeistStatus getHeistStatusById(@PathVariable Long heist_id) {
        return heistService.getHeistStatusById(heist_id);
    }

    @GetMapping(path = "/{heist_id}/members")
    public String getHeistMembers(Model model, @PathVariable Long heist_id) {
        List<GetHeistMembers> heistMembers = heistService.getHeistMembers(heist_id);
        model.addAttribute("heistMember", heistMembers);
        return "heist/members";
    }

    @GetMapping(path = "/{heist_id}/eligible_members")
    public String getHeistEligibleMembers(Model model, @PathVariable Long heist_id) {
        GetHeistEligibleMembers heistEligibleMembers = heistService.getHeistEligibleMembers(heist_id);
        model.addAttribute("heistEligibleMembers", heistEligibleMembers);
        return "heist/eligible";
    }

}
