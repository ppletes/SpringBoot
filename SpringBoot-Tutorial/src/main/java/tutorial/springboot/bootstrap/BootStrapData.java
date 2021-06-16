package tutorial.springboot.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tutorial.springboot.model.Pupil;
import tutorial.springboot.model.Class;
import tutorial.springboot.service.ClassService;
import tutorial.springboot.service.PupilService;

//@Component implements CommandLineRunner
public class BootStrapData {
/*
    private final PupilService pupilService;
    private final ClassService classService;

    public BootStrapData(PupilService pupilService, ClassService classService) {
        this.pupilService = pupilService;
        this.classService = classService;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Pupil pupil1 = new Pupil("Paula", "Pleteš");
        Class class1 = new Class("1A");

        class1.getPupil().add(pupil1);

        pupilService.save(pupil1);
        classService.save(class1);

        System.out.println("Number of pupils: " + pupilService.findAll().size());
        System.out.println("Number of classes: " + classService.count());

    }
 */
}
