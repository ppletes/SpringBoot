package tutorial.springboot.service.forRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tutorial.springboot.model.Class;
import tutorial.springboot.repository.ClassRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClassServiceForRepository {

    private final ClassRepository classRepository;

    public ClassServiceForRepository(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<Class> listAllClasses() {
        return classRepository.findAll();
    }

    public void addNewClass(Class newClass) {
        boolean matches = newClass.getName().matches("[1-8]+[A-Z]");

        if (matches == true) {
            for (Class c : listAllClasses()) {
                if (c.getName().contains(newClass.getName())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "This class already exists!");
                }
            }

            Class classNew = new Class();
            if(listAllClasses().isEmpty()){
                classNew.setId(Long.parseLong("1"));
            }else{
                Class aClass = listAllClasses().get(listAllClasses().size() - 1);
                classNew.setId(aClass.getId() + 1);
            }
            classNew.setName(newClass.getName());

            classRepository.save(classNew);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong class format! Example: 1A");
        }
    }
}
