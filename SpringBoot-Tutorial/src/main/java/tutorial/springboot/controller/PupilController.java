package tutorial.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tutorial.springboot.service.PupilService;

@Controller
public class PupilController {

    private final PupilService pupilService;

    public PupilController(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    @RequestMapping("/pupils")
    public String getClasses(Model model){
        model.addAttribute("classes", pupilService.findAll());
        return "pupils/pupil";
    }
}
