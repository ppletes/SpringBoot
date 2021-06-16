package tutorial.springboot.service.map;

import tutorial.springboot.model.Class;
import tutorial.springboot.service.MainService;

import java.util.Set;

public class ClassServiceMap extends AbstractMapService<Class, Long> implements MainService<Class, Long> {

    @Override
    public Set<Class> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Class object) {
        super.delete(object);
    }

    @Override
    public Class findById(Long id) {
        return super.finById(id);
    }

    @Override
    public Class save(Class object) {
        return super.save(object.getId(), object);
    }
}
