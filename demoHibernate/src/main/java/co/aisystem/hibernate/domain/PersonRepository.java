package co.aisystem.hibernate.domain;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    List<Person> findByName(String name);
}
