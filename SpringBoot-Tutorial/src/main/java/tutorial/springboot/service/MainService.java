package tutorial.springboot.service;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

@NoRepositoryBean
public interface MainService <T, ID> extends Repository<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
