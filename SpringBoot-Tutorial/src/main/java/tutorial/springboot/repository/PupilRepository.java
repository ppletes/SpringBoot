package tutorial.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tutorial.springboot.model.Pupil;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Integer> {
}
