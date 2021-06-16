package tutorial.springboot.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tutorial.springboot.model.Class;
import tutorial.springboot.service.forRepository.ClassServiceForRepository;

@Controller
@RequestMapping("/classes")
public class ClassController {

    private final ClassServiceForRepository classService;

    public ClassController(ClassServiceForRepository classService) {
        this.classService = classService;
    }

    @GetMapping("")
    public String getClasses(Model model) {
        model.addAttribute("classes", classService.listAllClasses());
        return "classes/class";
    }


    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String setClasses(Class newClass) {
        classService.addNewClass(newClass);
        return "classes/class";
    }
}
