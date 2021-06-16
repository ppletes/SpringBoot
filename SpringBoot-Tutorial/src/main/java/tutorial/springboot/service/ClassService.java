package tutorial.springboot.service;

import org.springframework.data.repository.CrudRepository;
import tutorial.springboot.model.Class;

public interface ClassService extends CrudRepository<Class, Long> {
}
