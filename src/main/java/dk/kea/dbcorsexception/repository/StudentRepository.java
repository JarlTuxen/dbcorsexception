package dk.kea.dbcorsexception.repository;

import dk.kea.dbcorsexception.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
