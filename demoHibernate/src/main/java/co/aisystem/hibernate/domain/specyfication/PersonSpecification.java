package co.aisystem.hibernate.domain.specyfication;

import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.Person_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class PersonSpecification {

    static Optional<Specification<Person>> build(PersonCriteria criteria, Specification<Person> specification) {
        return Optional.empty();
    }

    public static Specification<Person> personByName(String name) {
        return (Specification<Person>) (root, query, cb) -> cb.like(root.get(Person_.name), name);
    }
}
