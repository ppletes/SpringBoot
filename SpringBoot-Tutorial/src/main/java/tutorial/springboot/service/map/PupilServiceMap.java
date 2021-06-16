package tutorial.springboot.service.map;

import tutorial.springboot.model.Class;
import tutorial.springboot.model.Pupil;
import tutorial.springboot.service.MainService;

import java.util.Set;


public class PupilServiceMap extends AbstractMapService<Pupil, Long> implements MainService<Pupil, Long> {

    @Override
    public Set<Pupil> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pupil object) {
        super.delete(object);
    }

    @Override
    public Pupil findById(Long id) {
        return super.finById(id);
    }

    @Override
    public Pupil save(Pupil object) {
        return super.save(object.getId(), object);
    }
}
