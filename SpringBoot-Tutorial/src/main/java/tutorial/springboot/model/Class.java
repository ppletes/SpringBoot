package tutorial.springboot.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Class {

    private Long id;
    @Column(name = "name")
    private String name;

    private Set<Pupil> pupil = new HashSet<>();

    public Class() {
    }

    public Class(String name) {
        this.name = name;
    }

    public Class(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    public Set<Pupil> getPupil() {
        return pupil;
    }

    public void setPupil(Set<Pupil> pupil) {
        this.pupil = pupil;
    }
}
