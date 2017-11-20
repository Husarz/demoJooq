package co.aisystem.hibernate.domain.specyfication;

import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.Person_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonSpecification {


    static Optional<Specification<Person>> build(PersonCriteria criteria, Specification<Person> specification) {
        return Optional.empty();
    }

    public static Optional<Specification<Person>> build(PersonCriteria criteria) {
        return criteria == null ? Optional.empty() : tryBuild();
    }

    public static Specification<Person> personByName(PersonCriteria criteria) {
        return (Specification<Person>) (root, query, cb) -> cb.like(root.get(Person_.name), criteria.getName());
    }

    public static Optional<Specification<Person>> tryBuild() {
        final List<Specification<Person>> specificationsList = Collections.emptyList();
        return specificationsList.stream()
                .map(Specification::where)
                .reduce(Specification::and);
    }
}
