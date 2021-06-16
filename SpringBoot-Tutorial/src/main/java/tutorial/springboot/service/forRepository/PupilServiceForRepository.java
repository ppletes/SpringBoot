package tutorial.springboot.service.forRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tutorial.springboot.model.Class;
import tutorial.springboot.model.Pupil;
import tutorial.springboot.repository.ClassRepository;
import tutorial.springboot.repository.PupilRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PupilServiceForRepository {

    private final PupilRepository pupilRepository;

    public PupilServiceForRepository(PupilRepository pupilRepository) {
        this.pupilRepository = pupilRepository;
    }

    public List<Pupil> listAllPupils() {
        return pupilRepository.findAll();
    }

    public void addNewPupil(Pupil newPupil) {

            for (Pupil p : listAllPupils()) {
                if (p.getFirstName().contains(newPupil.getFirstName()) && p.getLastName().contains(newPupil.getLastName())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "This pupil already exists!");
                }
            }

            Pupil pupilNew = new Pupil();
            if(listAllPupils().isEmpty()){
                pupilNew.setId(Long.parseLong("1"));
            }else{
                Pupil aPupil = listAllPupils().get(listAllPupils().size() - 1);
                pupilNew.setId(aPupil.getId() + 1);
            }
            pupilNew.setFirstName(newPupil.getFirstName());
            pupilNew.setLastName(newPupil.getLastName());

            pupilRepository.save(pupilNew);
    }
}
